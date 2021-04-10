package Forms;

import Alarm.Alarm;
import Alarm.AlarmHMS;
import Arch.*;
import Clock.Clock;

import javax.swing.*;

public class ClientWindow implements ISubscriber {
    protected EventManager eventManager = new EventManager();

    private JPanel panel;
    private JSpinner hr_s;
    private JSpinner min_s;
    private JSpinner sec_s;
    private JButton connect;
    private JButton add;
    private JList alarms;
    private JTextPane log;
    private JLabel hr;
    private JLabel min;
    private JLabel sec;

    private DefaultListModel model_alarms;

    public ClientWindow() {
        model_alarms = new DefaultListModel();
        alarms.setModel(model_alarms);
        log.setEnabled(false);
        connect.addActionListener(e -> onConnectClick());
        add.addActionListener(e -> onAddAlarmClick());
    }

    public JPanel getPanel() {
        return panel;
    }

    public void addSubscriber(ISubscriber subscriber) {
        eventManager.addSubscriber(subscriber);
    }

    protected void addLog(String message) {
        String full = log.getText();
        full += message + "\n";
        log.setText(full);
    }

    protected void onConnectClick() {
        eventManager.broadcast(new Event(EventType.CLIENT_CONNECT_REQUEST));
    }

    protected void onAddAlarmClick() {
        int hours = (int)hr_s.getValue();
        int minutes = (int)min_s.getValue();
        int seconds = (int)sec_s.getValue();
        AlarmHMS alarm = new AlarmHMS();
        alarm.setHours(hours);
        alarm.setMinutes(minutes);
        alarm.setSeconds(seconds);
        eventManager.broadcast(new Event(EventType.ALARM_ADD_REQUEST, alarm));
    }

    @Override
    public void signal(Event event) {
        if (event.type == EventType.CLOCK_SYNC || event.type == EventType.CLOCK_UPDATED) {
            Clock clock = event.clock;
            int seconds = 0;
            try {
                seconds = clock.getSeconds();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
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
                e.printStackTrace();
            }
            model_alarms.addElement(alarm.getHours() + ":" + alarm.getMinutes() + ":" + seconds);
            return;
        }
        if (event.type == EventType.SERVICE_MESSAGE) {
            addLog(event.message);
            return;
        }
        System.out.println("Unsupported event");
    }
}
