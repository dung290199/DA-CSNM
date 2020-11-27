package LTM;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

public class JTextFieldExam implements ActionListener, KeyListener {

    private JFrame mainFrame;
    private JLabel headerLabel;
    private JLabel statusLabel;
    private JPanel controlPanel;

    private Color HILIT_COLOR = Color.orange;
    private Color  ERROR_COLOR = Color.RED;

    private Color entryBg;
    private Highlighter hilit;
    private Highlighter.HighlightPainter painter;

    List<String> keywords = new ArrayList<String>();

    public JTextFieldExam(){
        keywords.add("example");
        keywords.add("autocomplete");
        keywords.add("stackabuse");
        keywords.add("java");
        prepareGUI();
    }

    public static void main(String[] args) {
        JTextFieldExam swingDemo = new JTextFieldExam();
        swingDemo.showTextFieldDemo();
    }

    private void prepareGUI() {
        mainFrame = new JFrame("Vi du JTextField - Java Swing");
        mainFrame.setSize(400, 300);
        mainFrame.setLayout(new GridLayout(3, 1));
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });

        headerLabel = new JLabel("", JLabel.CENTER);
        statusLabel = new JLabel("", JLabel.CENTER);
        statusLabel.setSize(350, 100);

        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());
        mainFrame.add(headerLabel);
        mainFrame.add(controlPanel);
        mainFrame.add(statusLabel);
        mainFrame.setVisible(true);
    }

    JTextField userText;
    JButton loginButton;
    private void showTextFieldDemo() {
        headerLabel.setText("Control in action: JTextField");
        JLabel namelabel = new JLabel("User ID: ", JLabel.RIGHT);

        userText = new JTextField(6);
        userText.addKeyListener(this);
        hilit = new DefaultHighlighter();
        painter = new DefaultHighlighter.DefaultHighlightPainter(HILIT_COLOR);
        userText.setHighlighter(hilit);

        loginButton = new JButton("Login");
        loginButton.addActionListener(this);

        controlPanel.add(namelabel);
        controlPanel.add(userText);
        controlPanel.add(loginButton);

        mainFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton){
            String data = "Username " + userText.getText();
            statusLabel.setText(data);
        }

        if (e.getSource() == userText){
            statusLabel.setText("this is text!!!!!!!!");
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        String string = "";
        try {
            do{
                string = userText.getText();
                if (keywords.contains(string)){

                }
                hilit.addHighlight(0, userText.getText().length(), painter);
            } while(true);
        } catch (BadLocationException exception) {
            exception.printStackTrace();
        }
        statusLabel.setText(string);
    }
}