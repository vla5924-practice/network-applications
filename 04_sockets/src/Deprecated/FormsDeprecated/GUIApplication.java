package Deprecated.FormsDeprecated;

import javax.swing.*;

public class GUIApplication {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Clock manager");
        frame.setContentPane(new MainWindow().getPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
