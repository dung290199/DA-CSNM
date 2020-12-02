package HDH;// Packages to import
import org.jfree.data.time.Millisecond;
import sun.plugin.javascript.navig.LinkArray;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class JTableExamples extends JPanel implements ActionListener {

    private JTable processTable;
    private String[] colName;
    private List<String> books;
    private Timer timer = new Timer(1000, this);

    // Constructor 
    public JTableExamples() throws IOException, InterruptedException {

        this.processTable = new JTable();


        this.colName = new String[]{"PID", "Process", "Session Name", "Mem Usage(K)"};
        this.processTable.getTableHeader().setBackground(Color.WHITE);
        this.processTable.getTableHeader().setForeground(Color.BLUE);
        Font Tablefont = new Font("Process Header", Font.BOLD, 15);
        this.processTable.getTableHeader().setFont(Tablefont);
        this.processTable.setFont(new Font("Process details", Font.HANGING_BASELINE, 13));
        this.processTable.setGridColor(Color.LIGHT_GRAY);
        this.processTable.setRowHeight(20);

        DefaultTableModel contactTableModel = (DefaultTableModel) this.processTable.getModel();
        contactTableModel.setColumnIdentifiers(this.colName);
        this.processTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // adding it to JScrollPane 
        JScrollPane sp = new JScrollPane(this.processTable);
        timer.setInitialDelay(1000);
        setAutoscrolls(true);
        add(sp);
        setVisible(true);
        timer.start();
//        this.updateData();
    }

    public void actionPerformed(final ActionEvent e) {
        try {
            this.updateData();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    public void updateData() throws IOException, InterruptedException {
        DefaultTableModel tableModel = (DefaultTableModel) processTable.getModel();
        if (books != null) {
            List<String> temp = books;
            books = getData();
            if (temp.equals(books)){
                return;
            }
        } else {
            books = getData();
        }
        int i = 0;
        if (books.size() != 0) {
            for (String book : books) {
                String[] object = new String[4];
                object[0] = book.substring(25, 34);
                object[1] = book.substring(0, 24);
                object[2] = book.substring(35, 52);
                object[3] = book.substring(66, 76).replace('K', ' ');

                tableModel.addRow(object);
            }
            processTable.setModel(tableModel);
            tableModel.fireTableDataChanged();
        }
    }

    public List<String> getData() throws IOException {
        Process process = Runtime.getRuntime().exec("tasklist");
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        List<String> lines = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            if (!(line.equals("") || line.startsWith("Image") || line.startsWith("=="))) {
                lines.add(line);
//                System.out.println(line);
            }

        }
        return lines;
    }

//    // Driver  method
//    public static void main(String[] args) throws IOException, InterruptedException {
//        JTableExamples j = new JTableExamples();
//
//    }
} 