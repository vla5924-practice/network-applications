package Forms;

import Alarm.Alarm;
import Arch.*;
import Clock.*;
import Server.Server;

import javax.swing.*;

public class ServerWindow implements ISubscriber {
    protected EventManager eventManager = new EventManager();

    private JPanel panel;
    private JSpinner sec_s;
    private JSpinner min_s;
    private JSpinner hr_s;
    private JButton toggle;
    private JButton set;
    private JList<String> alarms;
    private JLabel hr;
    private JLabel min;
    private JLabel sec;
    private JList<String> log;

    private DefaultListModel<String> model_alarms;
    private DefaultListModel<String> model_log;

    Server server;

    public ServerWindow(Server server_) {
        server = server_;
        model_alarms = new DefaultListModel<>();
        alarms.setModel(model_alarms);
        model_log = new DefaultListModel<>();
        log.setModel(model_log);
        toggle.addActionListener(e -> onToggleClick());
        set.addActionListener(e -> onSetTimeClick());
    }

    public JPanel getPanel() {
        return panel;
    }

    protected void addLog(String message) {
        model_log.addElement(message);
    }

    protected void onToggleClick() {
        eventManager.broadcast(new Event(EventType.CLOCK_TOGGLE));
    }

    protected void onSetTimeClick() {
        int hours = (int)hr_s.getValue();
        int minutes = (int)min_s.getValue();
        int seconds = (int)sec_s.getValue();
        ClockHMS clock = new ClockHMS();
        clock.setHours(hours);
        clock.setMinutes(minutes);
        clock.setSeconds(seconds);
        eventManager.broadcast(new Event(EventType.CLOCK_UPDATE_REQUEST, clock));
    }

    public void addSubscriber(ISubscriber subscriber) {
        eventManager.addSubscriber(subscriber);
    }

    @Override
    public void signal(Event event) {
        if (event.type == EventType.CLOCK_UPDATED) {
            Clock clock = event.clock;
            int seconds = 0;
            try {
                seconds = clock.getSeconds();
            } catch (NoSuchMethodException e) {
            }
            hr.setText(String.valueOf(clock.getHours()));
            min.setText(String.valueOf(clock.getMinutes()));
            sec.setText(String.valueOf(seconds));
            return;
        }
        if (event.type == EventType.ALARM_ADDED) {
            Alarm alarm = event.alarm;
            int seconds = 0;
            try {
                seconds = alarm.getSeconds();
            } catch (NoSuchMethodException e) {
            }
            model_alarms.addElement(alarm.getHours() + ":" + alarm.getMinutes() + ":" + seconds);
            return;
        }
        if (event.type == EventType.ALARM_WENT_OFF) {
            Alarm alarm = event.alarm;
            int seconds = 0;
            try {
                seconds = alarm.getSeconds();
            } catch (NoSuchMethodException e) {
            }
            addLog("Alarm went off: "  + alarm.getHours() + ":" + alarm.getMinutes() + ":" + seconds);
            return;
        }
        if (event.type == EventType.SERVICE_MESSAGE) {
            addLog(event.message);
            return;
        }
        System.out.println("[Server window signal] Unsupported event: " + event.type);
    }
}
