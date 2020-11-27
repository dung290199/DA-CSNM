package LTM;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxUI;

public class ComboBoxDemo extends JFrame implements KeyListener {
    String[] languages = {
            "Java", "C++", "C", "Python",
            "JavaScript", "Perl", "Ruby", "C#"
    };

    JTextField textfield1 = new JTextField
            ("Top Programming Languages : ");

    JTextField textfield2 = new JTextField(15);
    
    JComboBox comboBox = new JComboBox();

    int count = 0;
    JTextField editor = (JTextField) comboBox.getEditor().getEditorComponent();

    public ComboBoxDemo() {
        for(int i = 0; i < languages.length; i++)
            comboBox.addItem(languages[count++]);
        textfield1.setEditable(false);
//        textfield2.addKeyListener(this);
//        textfield2.add(comboBox);
        comboBox.setEnabled(true);
        comboBox.setEditable(true);

        comboBox.remove(comboBox.getComponent(0));
        editor.addKeyListener(this);

        setLayout(new FlowLayout());
        add(textfield1);
        add(textfield2);
        add(comboBox);
    }

    public static void main(String[] args) {
        setFrame(new ComboBoxDemo(), 250, 150);
    }

    public static void
    setFrame(final JFrame frame, final int width, final int height) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                frame.setTitle(frame.getClass().getSimpleName());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(width, height);
                frame.setVisible(true);
            }
        });
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
        comboBox.setPopupVisible(true);

        if (string.length() != 0){
            textfield2.setText("text");
            for (int i = 0; i < comboBox.getItemCount(); i++) {
                String item = (String) comboBox.getItemAt(i);
                if (item.startsWith(string)){
                    comboBox.setSelectedIndex(i);
                    editor.setText(string);
                    return;
                }
            }
        } else {
            textfield2.setText("");
        }
    }
}