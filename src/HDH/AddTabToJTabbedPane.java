package HDH;

import java.awt.*;
import java.io.IOException;

import javax.swing.*;

public class AddTabToJTabbedPane {

    public AddTabToJTabbedPane() throws IOException, InterruptedException {

        // Create and set up the window.
        final JFrame frame = new JFrame("Memory Managerment");

        // Display the window.
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // set grid layout for the frame
        frame.getContentPane().setLayout(new GridLayout(1, 1));

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);

        // add tab with title
        JPanel performance = new DynamicLineAndTimeSeriesChart("s");
        tabbedPane.addTab("Performance", performance);

        JTableExamples processes = new JTableExamples();
        tabbedPane.addTab("Processes", processes);

        frame.setSize(new Dimension(800, 500));
        frame.getContentPane().add(tabbedPane);

    }

    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            public void run() {

                try {
                    new AddTabToJTabbedPane();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        });
    }

}