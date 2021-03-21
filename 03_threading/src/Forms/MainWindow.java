package Forms;

import Clock.BClock;
import Clock.EClockType;
import Clock.IClock;

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
    private JButton button_clock_start;
    private JButton button_clock_set_time;

    private IClock clock;

    public MainWindow() {
        clock = BClock.build(EClockType.HMS);
        button_clock_set_time.addActionListener(e -> setClockTime());
        button_clock_start.addActionListener(e -> startClockTick());
    }

    public void updateClock() {
        clock_h.setText(String.valueOf(clock.getHours()));
        clock_m.setText(String.valueOf(clock.getMinutes()));
        try {
            clock_s.setText(String.valueOf(clock.getSeconds()));
        } catch (Exception e) {

        }
    }

    public void setClockTime() {
        int hours = (int)spinner_clock_h.getValue();
        int minutes = (int)spinner_clock_m.getValue();
        int seconds = (int)spinner_clock_s.getValue();
        try {
            clock.setHours(hours);
            clock.setMinutes(minutes);
            clock.setSeconds(seconds);
        } catch (Exception e) {

        }
        updateClock();
    }

    public void startClockTick() {
        button_clock_start.setEnabled(false);
        new Thread(() -> {
            try {
                while (true) {
                    Thread.sleep(500); // TODO: Set to 999
                    clock.addSeconds(1);
                    updateClock();
                }
            } catch (Exception e) {
            }
        }).start();
    }

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
