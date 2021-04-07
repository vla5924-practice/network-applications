package Deprecated.FormsDeprecated;

import Alarm.BAlarm;
import Timeholders.IAlarm;
import Deprecated.ArchDeprecated.AbstractEvent;
import Deprecated.ArchDeprecated.ICallable;
import Deprecated.ArchDeprecated.IPublisher;
import Deprecated.ArchDeprecated.ISubscriber__;
import Clock.BClock;
import Clock.ClockController;
import Deprecated.ArchDeprecated.TimeUpdateEvent;
import Timeholders.TimeholderType;
import Timeholders.IClock;

import javax.swing.*;
import java.util.LinkedList;

public class ClockWindow implements ISubscriber__ {
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
    private ClockController controller;
    private LinkedList<IAlarm> alarms;
    private DefaultListModel model_alarms;
    private ICallable alarm_slot;

    public ClockWindow() {
        clock = BClock.build(TimeholderType.HMS);
        alarms = new LinkedList<IAlarm>();
        model_alarms = new DefaultListModel();
        list_alarms.setModel(model_alarms);
        alarm_slot = () -> {
            try {
                pane_alarm.setText("Alarm " + clock.getHours() + ":" + clock.getMinutes() + ":" + clock.getSeconds());
            } catch (Exception e) {
            }
        };

        IPublisher pub = (IPublisher)clock;
        pub.addSubscriber(this);

        controller = new ClockController(clock);

        button_clock_set_time.addActionListener(e -> setClockTime());
        button_clock_start.addActionListener(e -> controller.toggle());
        button_alarm_add.addActionListener(e -> addAlarm());
        button_clear.addActionListener(e -> clearLog());
    }

    @Override
    public void signal(AbstractEvent event) throws IllegalArgumentException {
        if(!event.getClass().isAssignableFrom(new TimeUpdateEvent().getClass()))
            throw new IllegalArgumentException("Unsupported event class");
        TimeUpdateEvent time = (TimeUpdateEvent)event;
        clock_h.setText(String.valueOf(time.hours));
        clock_m.setText(String.valueOf(time.minutes));
        clock_s.setText(String.valueOf(time.seconds));
    }

    @Override
    public void setSlot(ICallable slot) {
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
    }

    public void addAlarm() {
        int hours = (int)spinner_alarm_h.getValue();
        int minutes = (int)spinner_alarm_m.getValue();
        int seconds = (int)spinner_alarm_s.getValue();
        IAlarm alarm = BAlarm.build(TimeholderType.HMS);
        try {
            alarm.setHours(hours);
            alarm.setMinutes(minutes);
            alarm.setSeconds(seconds);
        } catch (Exception e) {
        }
        alarms.add(alarm);
        IPublisher pub = (IPublisher)clock;
        ISubscriber__ sub = (ISubscriber__)alarm;
        sub.setSlot(alarm_slot);
        pub.addSubscriber(sub);
        model_alarms.addElement(hours + ":" + minutes + ":" + seconds);
    }

    public void clearLog() {
        pane_alarm.setText("");
    }

    public JPanel getPanel() {
        return panel;
    }
}
