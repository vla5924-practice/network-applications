package Forms;

import Alarm.BAlarm;
import Alarm.IAlarm;
import Arch.ICallable;
import Arch.IPublisher;
import Arch.ISubscriber;
import Clock.BClock;
import Timeholder.ETimeholderType;
import Clock.IClock;

import javax.swing.*;
import java.util.LinkedList;
import java.util.Vector;

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
    private JList list_alarms;
    private JSpinner spinner_alarm_h;
    private JSpinner spinner_alarm_m;
    private JSpinner spinner_alarm_s;
    private JButton button_alarm_add;
    private JTextPane pane_alarm;
    private JButton button_clear;

    private IClock clock;
    private LinkedList<IAlarm> alarms;
    private DefaultListModel model_alarms;
    private ICallable alarm_slot;

    public MainWindow() {
        clock = BClock.build(ETimeholderType.HMS);
        alarms = new LinkedList<IAlarm>();
        model_alarms = new DefaultListModel();
        list_alarms.setModel(model_alarms);
        alarm_slot = new ICallable() {
            @Override
            public void call() {
                try {
                    pane_alarm.setText("Alarm " + clock.getHours() + ":" + clock.getMinutes() + ":" + clock.getSeconds());
                } catch (Exception e) {
                }
            }
        };

        button_clock_set_time.addActionListener(e -> setClockTime());
        button_clock_start.addActionListener(e -> startClockTick());
        button_alarm_add.addActionListener(e -> addAlarm());
        button_clear.addActionListener(e -> clearLog());
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

    public void addAlarm() {
        int hours = (int)spinner_alarm_h.getValue();
        int minutes = (int)spinner_alarm_m.getValue();
        int seconds = (int)spinner_alarm_s.getValue();
        IAlarm alarm = BAlarm.build(ETimeholderType.HMS);
        try {
            alarm.setHours(hours);
            alarm.setMinutes(minutes);
            alarm.setSeconds(seconds);
        } catch (Exception e) {

        }
        alarms.add(alarm);
        IPublisher pub = (IPublisher)clock;
        ISubscriber sub = (ISubscriber)alarm;
        sub.setSlot(alarm_slot);
        pub.addSubscriber(sub);
        model_alarms.addElement(hours + ":" + minutes + ":" + seconds);
    }

    public void startClockTick() {
        button_clock_start.setEnabled(false);
        new Thread(() -> {
            try {
                while (true) {
                    Thread.sleep(999);
                    clock.addSeconds(1);
                    updateClock();
                }
            } catch (Exception e) {
            }
        }).start();
    }

    public void clearLog() {
        pane_alarm.setText("");
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public JPanel getPanel() {
        return panel;
    }
}
