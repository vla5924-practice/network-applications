package Server;

import Alarm.Alarm;
import Alarm.AlarmHMS;
import Clock.BClock;
import Clock.Clock;
import Clock.ClockController;
import Database.DatabaseService;
import Database.DatabaseSessionFactory;
import Events.Event;
import Events.EventManager;
import Events.EventType;
import Events.EventListener;
import Timeholders.TimeholderType;
import org.hibernate.Session;

import java.util.LinkedList;
import java.util.List;

public class ServerModel implements EventListener {
    private EventManager eventManager = new EventManager();

    private Clock clock = BClock.build(TimeholderType.HMS);
    private ClockController clockController = new ClockController(clock);
    private LinkedList<Alarm> alarms = new LinkedList<>();
    private DatabaseService db = new DatabaseService();

    public ServerModel() {
    }

    public void addSubscriber(EventListener subscriber) {
        eventManager.addSubscriber(subscriber);
    }

    public void addAlarm(Alarm alarm) {
        alarm.addSubscriber(this);
        addClockSubscriber(alarm);
        alarms.add(alarm);
        db.insertAlarm(alarm);
        eventManager.broadcast(new Event(EventType.ALARM_ADDED, alarm));
    }

    public void deleteAlarm(Alarm alarm) {
        eventManager.broadcast(new Event(EventType.ALARM_DELETED, alarm));
        alarm.removeSubscriber(this);
        db.deleteAlarm(alarm);
    }

    public void fetchAlarms() {
        Session session = DatabaseSessionFactory.getInstance().openSession();
        List<AlarmHMS> raw_alarms = session.createQuery("from AlarmHMS", AlarmHMS.class).getResultList();
        if (raw_alarms != null) {
            for (AlarmHMS alarm : raw_alarms) {
                alarm.addSubscriber(this);
                addClockSubscriber(alarm);
                alarms.add(alarm);
                eventManager.broadcast(new Event(EventType.ALARM_ADD_REQUEST, alarm));
            }
        }
        session.close();
    }

    public LinkedList<Alarm> getAlarms() {
        return alarms;
    }

    public Clock getClock() {
        return clock;
    }

    @Override
    public void signal(Event event) {
        if (event.type == EventType.ALARM_WENT_OFF) {
            deleteAlarm(event.alarm);
            eventManager.broadcast(event);
            return;
        }
        if (event.type == EventType.ALARM_DELETE_REQUEST) {
            deleteAlarm(event.alarm);
            return;
        }
        if (event.type == EventType.CLOCK_TOGGLE_REQUEST) {
            clockController.toggle();
            eventManager.broadcast(new Event(EventType.CLOCK_SYNC, clock, isClockRunning()));
            return;
        }
        if (event.type == EventType.CLOCK_UPDATE_REQUEST) {
            Clock clock_ = event.clock;
            clock.setHours(clock_.getHours());
            clock.setMinutes(clock_.getMinutes());
            try {
                clock.setSeconds(clock_.getSeconds());
            } catch (NoSuchMethodException e) {
            }
            eventManager.broadcast(new Event(EventType.CLOCK_SYNC, clock, isClockRunning()));
        }
        System.out.println("[Server model signal] Unsupported event: " + event.type);
    }

    public void addClockSubscriber(EventListener subscriber) {
        clock.addSubscriber(subscriber);
    }

    public boolean isClockRunning() {
        return clockController.isRunning();
    }
}
