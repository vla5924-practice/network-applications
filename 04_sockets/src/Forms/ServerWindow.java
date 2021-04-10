package Forms;

import Alarm.Alarm;
import Arch.*;
import Clock.Clock;
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
    private JList alarms;
    private JLabel hr;
    private JLabel min;
    private JLabel sec;
    private JTextPane log;

    private DefaultListModel model_alarms;

    Server server;

    public ServerWindow(Server server_) {
        server = server_;
        model_alarms = new DefaultListModel();
        alarms.setModel(model_alarms);

        toggle.addActionListener(e -> onToggleClick());
        set.addActionListener(e -> onSetTimeClick());
    }

    public JPanel getPanel() {
        return panel;
    }

    protected void addLog(String message) {
        String full = log.getText();
        full += message + "\n";
        log.setText(full);
    }

    protected void onToggleClick() {
        eventManager.broadcast(new Event(EventType.CLOCK_TOGGLE));
    }

    protected void onSetTimeClick() {
        int hours = (int)hr_s.getValue();
        int minutes = (int)min_s.getValue();
        int seconds = (int)sec_s.getValue();
        eventManager.broadcast(new Event(EventType.CLOCK_UPDATE_REQUEST, new int[]{hours, minutes, seconds}));
    }

    public void addSubscriber(ISubscriber subscriber) {
        eventManager.addSubscriber(subscriber);
    }

    @Override
    public void signal(Event event) {
        if (event.type == EventType.CLOCK_UPDATED) {
            Clock clock = (Clock)event.object;
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
            Alarm alarm = (Alarm)event.object;
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
            ServiceMessage message = (ServiceMessage)event.object;
            addLog(message.get());
            return;
        }
        System.out.println("Unsupported event");
    }
}
