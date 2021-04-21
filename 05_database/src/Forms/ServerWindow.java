package Forms;

import Alarm.Alarm;
import Clock.Clock;
import Clock.ClockHMS;
import Events.Event;
import Events.EventManager;
import Events.EventType;
import Events.EventListener;

import javax.swing.*;

public class ServerWindow implements EventListener {
    protected EventManager eventManager = new EventManager();

    private JPanel panel;
    private JSpinner sec_s;
    private JSpinner min_s;
    private JSpinner hr_s;
    private JButton toggle;
    private JButton set;
    private JList<Alarm> alarms;
    private JLabel hr;
    private JLabel min;
    private JLabel sec;
    private JList<String> log;
    private JButton deleteButton;

    private DefaultListModel<Alarm> model_alarms;
    private DefaultListModel<String> model_log;

    private SpinnerNumberModel model_hr_s;
    private SpinnerNumberModel model_min_s;
    private SpinnerNumberModel model_sec_s;

    public ServerWindow() {
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

        toggle.addActionListener(e -> onToggleClick());
        set.addActionListener(e -> onSetTimeClick());
        deleteButton.addActionListener(e -> onDeleteClick());
    }

    private void onDeleteClick() {
        if (model_alarms.size() == 0)
            return;
        Alarm alarm = alarms.getSelectedValue();
        if (alarm == null)
            return;
        eventManager.broadcast(new Event(EventType.ALARM_DELETE_REQUEST, alarm));
    }

    public JPanel getPanel() {
        return panel;
    }

    protected void addLog(String message) {
        model_log.insertElementAt(message, 0);
    }

    protected void onToggleClick() {
        eventManager.broadcast(new Event(EventType.CLOCK_TOGGLE_REQUEST));
        toggle.setText(toggle.getText().equals("Start") ? "Stop" : "Start");
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

    public void addSubscriber(EventListener subscriber) {
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
        if (event.type == EventType.ALARM_ADD_REQUEST) {
            model_alarms.addElement(event.alarm);
            return;
        }
        if (event.type == EventType.ALARM_DELETED) {
            addLog("Alarm deleted: " + event.alarm);
            model_alarms.removeElement(event.alarm);
            return;
        }
        if (event.type == EventType.ALARM_WENT_OFF) {
            Alarm alarm = event.alarm;
            addLog("Alarm went off: "  + alarm);
            model_alarms.removeElement(alarm);
            return;
        }
        if (event.type == EventType.SERVICE_MESSAGE) {
            addLog(event.message);
            return;
        }
        System.out.println("[Server window signal] Unsupported event: " + event.type);
    }
}
