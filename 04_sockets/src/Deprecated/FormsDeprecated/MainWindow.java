package Deprecated.FormsDeprecated;

import javax.swing.*;
import java.util.LinkedList;

public class MainWindow {
    private JButton button_clock_create;
    private JTextField text_clock_name;
    private JPanel panel;

    private LinkedList<JFrame> clock_frames;

    public MainWindow() {
        clock_frames = new LinkedList<JFrame>();
        button_clock_create.addActionListener(e -> createClockWindow());
    }

    void createClockWindow() {
        String name = text_clock_name.getText();
        if (name.isEmpty())
            return;
        JFrame frame = new JFrame(name);
        frame.setContentPane(new ClockWindow().getPanel());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        clock_frames.add(frame);
    }

    public JPanel getPanel() {
        return panel;
    }
}
