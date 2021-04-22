package Forms;

import Alarm.Alarm;
import Alarm.AlarmHMS;
import Clock.Clock;
import Events.Event;
import Events.EventManager;
import Events.EventType;
import Events.EventListener;

import javax.swing.*;

public class ClientWindow implements EventListener {
    protected EventManager eventManager = new EventManager();

    private JPanel panel;
    private JSpinner hr_s;
    private JSpinner min_s;
    private JSpinner sec_s;
    private JButton connect;
    private JButton add;
    private JList<Alarm> alarms;
    private JList<String> log;
    private JLabel clockLabel;
    private JLabel min;
    private JLabel sec;
    private JButton deleteButton;

    private DefaultListModel<Alarm> model_alarms;
    private DefaultListModel<String> model_log;

    private SpinnerNumberModel model_hr_s;
    private SpinnerNumberModel model_min_s;
    private SpinnerNumberModel model_sec_s;

    public ClientWindow() {
        model_alarms = new DefaultListModel<>();
        alarms.setModel(model_alarms);
        model_log = new DefaultListModel<>();
        log.setModel(model_log);

        model_hr_s = new SpinnerNumberModel(0, 0, 11, 1);
        hr_s.setModel(model_hr_s);
        model_min_s = new SpinnerNumberModel(0, 0, 59, 1);
        min_s.setModel(model_min_s);
        model_sec_s = new SpinnerNumberModel(0, 0, 59, 1);
        sec_s.setModel(model_sec_s);

        connect.addActionListener(e -> onConnectClick());
        add.addActionListener(e -> onAddAlarmClick());
        deleteButton.addActionListener(e -> onDeleteClick());
    }

    private void onDeleteClick() {
        if (model_alarms.size() == 0)
            return;
        Alarm alarm = alarms.getSelectedValue();
        if (alarm == null)
            return;
        int index = alarms.getSelectedIndex();
        eventManager.broadcast(new Event(EventType.ALARM_DELETE_REQUEST, alarm));
        model_alarms.remove(index);
    }

    public JPanel getPanel() {
        return panel;
    }

    public void addSubscriber(EventListener subscriber) {
        eventManager.addSubscriber(subscriber);
    }

    protected void addLog(String message) {
        model_log.insertElementAt(message, 0);
    }

    protected void onConnectClick() {
        if (connect.getText().equals("Connect")) {
            eventManager.broadcast(new Event(EventType.CLIENT_CONNECT_REQUEST));
            connect.setText("Disconnect");
            add.setEnabled(true);
        } else {
            eventManager.broadcast(new Event(EventType.CLIENT_DISCONNECT_REQUEST));
            connect.setText("Connect");
            model_alarms.removeAllElements();
            model_log.removeAllElements();
            add.setEnabled(false);
        }
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
            clockLabel.setText(clock.toString());
            return;
        }
        if (event.type == EventType.ALARM_ADDED) {
            model_alarms.addElement(event.alarm);
            return;
        }
        if (event.type == EventType.ALARM_DELETED) {
            Alarm alarm = event.alarm;
            addLog("Alarm deleted: "  + alarm);
            for (int i = 0; i < model_alarms.size(); i++)
                if (model_alarms.get(i).equals(alarm))
                    model_alarms.remove(i);
            return;
        }
        if (event.type == EventType.ALARM_WENT_OFF) {
            Alarm alarm = event.alarm;
            addLog("===== ALARM WENT OFF: "  + alarm + " =====");
            for (int i = 0; i < model_alarms.size(); i++)
                if (model_alarms.get(i).equals(alarm))
                    model_alarms.remove(i);
            return;
        }
        if (event.type == EventType.SERVICE_MESSAGE) {
            addLog(event.message);
            return;
        }
        System.out.println("[Client window signal] Unsupported event: " + event.type);
    }
}
