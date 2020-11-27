package LTM;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;

import static java.awt.GridBagConstraints.*;

public class Client1 extends JFrame implements ActionListener, KeyListener, Runnable{

    DatagramSocket socket;
    String dic;
    ArrayList<String> arr;

    //GB arguments:
    private int gridx, gridy, gridwidth, gridheight, fill, anchor, ipadx, ipady;
    private double weightx, weighty;
    private Insets insets;

    // GB Insets:
    private int top, left, bottom, right;
    private final Insets insetsTop = new Insets(top = 5, left = 0, bottom = 15, right = 0);
    private final Insets insetsLabel = new Insets(top = 0, left = 10, bottom = 6, right = 5);
    private final Insets insetsText = new Insets(top = 0, left = 10, bottom = 6, right = 50);
    private final Insets insetsButton = new Insets(top = 10, left = 0, bottom = 10, right = 10);

    //input fields:
    JLabel lb1, lb2;
    JTextField editor;
    JComboBox jComboBox;
    JButton search, eng_vi, vi_eng;
    JEditorPane jEditorPane;
    JScrollPane jScrollPane;
    JPanel pn;

    public static void main(String[] args) {
        new Client1();
    }

    public Client1() {
        arr = new ArrayList<String>();
        try {
            socket = new DatagramSocket();
        } catch(Exception e) {
            e.printStackTrace();
        }
        new Thread(this).start();
    }

    public void paint() {
        pn = new JPanel(new GridBagLayout());

        //header row:
        lb1 = new JLabel("Dictionary");
        setDefaultValuesGB();// default values for all GridBagConstraints
        addGB(lb1,1, 1, 2, gridheight, fill, 1.0, weighty, anchor, insetsTop);

        //txt row (textfield):
        jComboBox = new JComboBox();
        jComboBox.setPreferredSize(new Dimension(200, 30));
        jComboBox.setEnabled(true);
        jComboBox.setEditable(true);
        jComboBox.remove(jComboBox.getComponent(0));

        editor = (JTextField) jComboBox.getEditor().getEditorComponent();
        editor.addKeyListener(this);
        setDefaultValuesGB();// default values for all GridBagConstraints
        addGB(jComboBox, 1, 2, gridwidth, gridheight, HORIZONTAL, 1.0, weighty, anchor, insetsText);

        //Result row (label):
        lb2 = new JLabel("Result : ");
        setDefaultValuesGB();// default values for all GridBagConstraints
        addGB(lb2, 1, 3, gridwidth, gridheight, fill, weightx, weighty, FIRST_LINE_START, insetsLabel);

        //Result row (textfield):
        jEditorPane = new JEditorPane();
        jEditorPane.setContentType("text/html");
        jEditorPane.setEnabled(false);
        jScrollPane = new JScrollPane(jEditorPane);
        setDefaultValuesGB();// default values for all GridBagConstraints
        addGB(new JScrollPane(jScrollPane), 1, 4, gridwidth, gridheight, BOTH, 1.0, 1.0, anchor, insetsText);

        //trailer row:
        search = new JButton("search");
        search.addActionListener(this);
        setDefaultValuesGB();// default values for all GridBagConstraints
        addGB(search, 2, 2, gridwidth, gridheight, HORIZONTAL, weightx, weighty, WEST, insetsButton);

        eng_vi = new JButton("English - Vietnamese");
        eng_vi.addActionListener(this);
        setDefaultValuesGB();// default values for all GridBagConstraints
        addGB(eng_vi, 2, 3, gridwidth, gridheight, HORIZONTAL, weightx, weighty, WEST, insetsButton);

        vi_eng = new JButton("Vietnamese - English");
        vi_eng.addActionListener(this);
        setDefaultValuesGB();// default values for all GridBagConstraints
        addGB(vi_eng, 2, 4, gridwidth, gridheight, HORIZONTAL, weightx, weighty, FIRST_LINE_START, insetsButton);

        setTitle("Dictionary");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 400);
        setLocationRelativeTo(null);
        setContentPane(pn);
        setVisible(true);
    }

    private void addGB(final Component component, final int gridx, final int gridy,
                       final int gridwidth, final int gridheight,
                       final int fill, final double weightx, final double weighty,
                       final int anchor, final Insets insets ) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = gridx;
        constraints.gridy = gridy;
        constraints.gridwidth = gridwidth;
        constraints.gridheight = gridheight;
        constraints.fill = fill;
        constraints.weightx = weightx;
        constraints.weighty = weighty;
        constraints.anchor = anchor;
        constraints.insets = insets;
        constraints.ipadx = ipadx;
        constraints.ipady = ipady;
        pn.add(component, constraints);
    }

    private void setDefaultValuesGB() {
        gridx = RELATIVE;
        gridy = RELATIVE;
        gridwidth = 1;
        gridheight = 1;
        fill = NONE;
        weightx = 0.0;
        weighty = 0.0;
        anchor = CENTER;
        insets = new Insets(0, 0, 0, 0);
        ipadx = 0;
        ipady = 0;
    }

    @Override
    public void run() {

        //set input, output stream
//            out = new DatagramPacket()
//            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        //Layout
        this.paint();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == search) {
            String mess = "search" + editor.getText();
            String string = "";

//            sendData(mess);//Send mess to server
            sendData(mess);

            //recieve
            string = receiveData();

            //receive data from server
//            String string = receiveData();
            //display to client
            jEditorPane.setText(string);
        }

        if (e.getSource() == eng_vi){
            lb1.setText("English - Vietnamese Dictionary");
            dic = "anh_viet";
            sendData(dic);
        }

        if (e.getSource() == vi_eng){
            lb1.setText("Vietnamese -English Dictionary");
            dic = "viet_anh";
            sendData(dic);
        }
    }

    public boolean sendData(String string){
        try {
            DatagramPacket send = new DatagramPacket(string.getBytes(), string.length(), InetAddress.getByName("localhost"), 5000);
            socket.send(send);
//            if (dic == "anh_viet"){
//                out.writeUTF(string);
//            } else {
//                out.writeUTF(string);
//            }
            System.out.println("out: " + string);
        } catch (IOException ioException){
            ioException.printStackTrace();
            return false;
        }
        return true;
    }

    public String receiveData() {
        String string = "";
        try {
            DatagramPacket recieve = new DatagramPacket(new byte[1000], 1000);
            socket.receive(recieve);
            string = new String(recieve.getData()).substring(0, recieve.getLength());
            System.out.println("receive: " + string);
            System.out.println("Data: " + recieve.getData());
        } catch(IOException ioException){
            ioException.printStackTrace();
        }
        return string;
    }

    public ArrayList<String> recieveWord() throws IOException {
        arr.clear();
        String string = "";
        System.out.println("in: ");
        DatagramPacket recieve = new DatagramPacket(new byte[1000], 1000);
        socket.receive(recieve);
        string = new String(recieve.getData()).substring(0, recieve.getLength());
        arr.addAll(Arrays.asList(string.split("\n", 10)));
//        for (int i = 0; i < 10; i++) {
//            if (string.equals("Not found") ||string.equals("Không tìm thấy")) {
//                arr.add(string);
//                break;
//            }
//            System.out.println(string);
//            arr.add(string);
//        }
        return arr;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        String string = editor.getText();
        jComboBox.setPopupVisible(true);

        if ((jComboBox.getItemCount() != 1) && findSimilar(arr, string)){
            System.out.println("done If");
            return;
        } else {
            sendData(string);
            try {
                recieveWord();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

            for (String item : arr) {
                jComboBox.addItem(item);
            }
            findSimilar(arr, string);
            System.out.println("done Else");
        }
    }

    private boolean findSimilar(ArrayList<String> arrayList, String string) {
        if (arr.size() == 1)
            return false;

        for (String item: arrayList) {
            if (item.toLowerCase().startsWith(string.toLowerCase())){
                jComboBox.setSelectedItem(item);
                editor.setText(string);
                return true;
            }
        }
        return false;
    }


}
