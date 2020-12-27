package HDH;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.Border;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

public class DynamicLineAndTimeSeriesChart extends JPanel implements ActionListener {

    private TimeSeries cpuSeries;
    private TimeSeries ramSeries;

    private JLabel process, infoProcess;
    private JLabel handle, infoHandle;
    private JLabel thread, infoThread;
    private JLabel cpuUsage, infoCPUUsage;
    private JLabel ramUsage, infoRAMUsage;
    private JLabel ramAvail, inforRAMAvail;
    private JLabel ramUsed, infoRAMUsed;

    private Timer timer = new Timer(250, this);

    public DynamicLineAndTimeSeriesChart(final String title) {

        this.cpuSeries = new TimeSeries("CPU usage(%)");
        this.ramSeries = new TimeSeries("RAM usage(%)");

        final TimeSeriesCollection cpuDataset = new TimeSeriesCollection(this.cpuSeries);
        final TimeSeriesCollection ramDataset = new TimeSeriesCollection(this.ramSeries);
        final JFreeChart cpuChart = createChart(cpuDataset);
        final JFreeChart ramChart = createChart(ramDataset);

        timer.setInitialDelay(1000);

        //Sets background color of cpuChart
        cpuChart.setBackgroundPaint(Color.LIGHT_GRAY);
        ramChart.setBackgroundPaint(Color.LIGHT_GRAY);

        //Created JPanel to show graph on screen
        final JPanel panel = new JPanel(new GridBagLayout());
        final JPanel percentPanel = new JPanel(new GridLayout(2, 1));
        percentPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        final JPanel systemPanel = new JPanel(new GridLayout(3, 2));
        systemPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        final JPanel currentUsagePanel = new JPanel(new GridLayout(2, 2));
        currentUsagePanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        final JPanel memoryPanel = new JPanel(new FlowLayout());
        memoryPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        //Create some component for systemPanel
        this.process = new JLabel("Processes: ");
        this.infoProcess = new JLabel();
        this.handle = new JLabel("Handles: ");
        this.infoHandle = new JLabel();
        this.thread = new JLabel("Thread: ");
        this.infoThread = new JLabel();
        this.cpuUsage = new JLabel("CPU usage (%): ");
        this.infoCPUUsage = new JLabel();
        this.ramUsage = new JLabel("RAM usage (%): ");
        this.infoRAMUsage = new JLabel();
        this.ramAvail = new JLabel("RAM avail (GB): ");
        this.inforRAMAvail = new JLabel();
        this.ramUsed = new JLabel("RAM used (GB): ");
        this.infoRAMUsed = new JLabel();

        //Add above components to systemPanel
        systemPanel.add(this.process);
        systemPanel.add(this.infoProcess);
        systemPanel.add(this.handle);
        systemPanel.add(this.infoHandle);
        systemPanel.add(this.thread);
        systemPanel.add(this.infoThread);

        currentUsagePanel.add(this.cpuUsage);
        currentUsagePanel.add(this.infoCPUUsage);
        currentUsagePanel.add(this.ramUsage);
        currentUsagePanel.add(this.infoRAMUsage);

        memoryPanel.add(this.ramAvail);
        memoryPanel.add(this.inforRAMAvail);
        memoryPanel.add(this.ramUsed);
        memoryPanel.add(this.infoRAMUsed);

        //Created Chartpanel for cpuChart area
        final ChartPanel cpuChartPanel = new ChartPanel(cpuChart);
        final ChartPanel ramChartPanel = new ChartPanel(ramChart);

        //Added chartpanel to main panel
        percentPanel.add(cpuChartPanel);
        percentPanel.add(ramChartPanel);

        //Sets the size of whole window (JPanel)
        cpuChartPanel.setPreferredSize(new java.awt.Dimension(350, 200));
        ramChartPanel.setPreferredSize(new java.awt.Dimension(350, 200));

        //Puts the whole percentPanel on a Frame
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(currentUsagePanel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(systemPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(memoryPanel, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        panel.add(percentPanel, gbc);

        add(panel);
        setVisible(true);
        timer.start();

    }

    private JFreeChart createChart(final XYDataset dataset) {
        final JFreeChart result = ChartFactory.createTimeSeriesChart(
                null,
                "Time",
                "Value",
                dataset,
                true,
                true,
                false
        );

        final XYPlot plot = result.getXYPlot();

        plot.setBackgroundPaint(new Color(0xffffe0));
        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.lightGray);
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.lightGray);

        ValueAxis xaxis = plot.getDomainAxis();
        xaxis.setAutoRange(true);

        //Domain axis would show data of 60 seconds for a time
        xaxis.setFixedAutoRange(60000.0);  // 1 seconds
        xaxis.setVerticalTickLabels(true);
        xaxis.setVisible(false);

        ValueAxis yaxis = plot.getRangeAxis();
        yaxis.setRange(0.0, 100.0);
        yaxis.setVisible(false);

        return result;
    }

    public void actionPerformed(final ActionEvent e) {

        try {
            List<String> strings = getCPUUsage("powershell Get-Counter '\\Processor(_Total)\\% Processor Time'");
            double cpuUasge = Double.parseDouble(strings.get(4).trim());
            double ramUsage = getRAMUsage();

            this.ramSeries.add(new Millisecond(), ramUsage);
            this.cpuSeries.add(new Millisecond(), cpuUasge);
            this.infoProcess.setText(String.valueOf(getNumOfProcess()));
            this.infoHandle.setText(String.valueOf(getNumOfHandle()));
            this.infoThread.setText(String.valueOf(getNumOfThread()));
            this.infoCPUUsage.setText(String.valueOf(Math.round(cpuUasge)) + "%");
            this.infoRAMUsage.setText(String.valueOf(Math.round(ramUsage)) + "%");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    public List<String> getCPUUsage(String string) throws IOException, InterruptedException {
        Process process = Runtime.getRuntime().exec(string);
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        List<String> lines = new ArrayList<>();
        String line;
        while((line = reader.readLine()) != null){
            lines.add(line);
        }
        return lines;
    }

    public double getRAMUsage() throws IOException {
        Process process = Runtime.getRuntime().exec("powershell Get-WmiObject win32_OperatingSystem |%{$_.totalvisiblememorysize, $_.freephysicalmemory}");
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        int totalRam = Integer.parseInt(reader.readLine().trim());
//        System.out.println("Ram total: " + totalRam);
        int availRam = Integer.parseInt(reader.readLine().trim());
//        System.out.println("Ram available: " + availRam);

        this.inforRAMAvail.setText(String.valueOf(roundAvoid( availRam / Math.pow(1024, 2), 1 )));
        this.infoRAMUsed.setText(String.valueOf( roundAvoid( (totalRam - availRam) / Math.pow(1024, 2), 1 ) ));

//        double temp = Double.valueOf((totalRam - availRam)) / totalRam * 100;
////        System.out.println(temp);;
        return Double.valueOf((totalRam - availRam)) / totalRam * 100;
    }

    public double roundAvoid(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }

    public int getNumOfThread() throws IOException {
        Process process = Runtime.getRuntime().exec("powershell (Get-Process|Select-Object -ExpandProperty Threads).Count");
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//        int thread = Integer.parseInt(reader.readLine().trim());
//        System.out.println("threads: " + thread);
        return Integer.parseInt(reader.readLine().trim());
    }

    public int getNumOfProcess() throws IOException {
        Process process = Runtime.getRuntime().exec("powershell (Get-Process).Count");
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//        int process = Integer.parseInt(reader.readLine().trim());
//        System.out.println("processes: " + thread);
        return Integer.parseInt(reader.readLine().trim());
    }

    public int getNumOfHandle() throws IOException, InterruptedException {
        Process process = Runtime.getRuntime().exec("powershell (Get-Process).handles");
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        int handles = 0;
        while((line = reader.readLine()) != null){
            handles += Integer.parseInt(line);
        }
        return handles;
    }
    /**
     * Starting point for the dynamic graph application.
     *
     * @param args  ignored.
     */
//    public static void main(final String[] args) {
//
//        final DynamicLineAndTimeSeriesChart demo = new DynamicLineAndTimeSeriesChart("Dynamic Line And TimeSeries Chart");
//        demo.pack();
//        RefineryUtilities.centerFrameOnScreen(demo);
//        demo.setVisible(true);
//
//    }

}  