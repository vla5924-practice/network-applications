package Forms;

import javax.swing.*;
import java.awt.*;

public class MainWindow {
    private JLabel clock_h;
    private JLabel clock_m;
    private JLabel clock_s;
    private JSpinner spinner_clock_h;
    private JSpinner spinner_clock_m;
    private JSpinner spinner_clock_s;
    private JPanel panel;

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Clock");
        frame.setContentPane(new MainWindow().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
