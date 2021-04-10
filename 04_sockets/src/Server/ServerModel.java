package Server;

import Alarm.Alarm;
import Arch.EventManager;
import Arch.EventType;
import Clock.BClock;
import Clock.ClockController;
import Clock.Clock;
import Arch.Event;
import Arch.ISubscriber;
import Timeholders.TimeholderType;

import java.util.LinkedList;

public class ServerModel implements ISubscriber {
    private EventManager eventManager = new EventManager();

    private Clock clock = BClock.build(TimeholderType.HMS);
    private ClockController clockController = new ClockController(clock);
    private LinkedList<Alarm> alarms = new LinkedList<>();

    public ServerModel() {
    }

    public void addSubscriber(ISubscriber subscriber) {
        eventManager.addSubscriber(subscriber);
    }

    public void addAlarm(Alarm alarm) {
        alarm.addSubscriber(this);
        addClockSubscriber(alarm);
        alarms.add(alarm);
        eventManager.broadcast(new Event(EventType.ALARM_ADDED, alarm));
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
            eventManager.broadcast(event);
            return;
        }
        if (event.type == EventType.CLOCK_TOGGLE) {
            clockController.toggle();
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
        }
        System.out.println("[Server model signal] Unsupported event: " + event.type);
    }

    public void addClockSubscriber(ISubscriber subscriber) {
        clock.addSubscriber(subscriber);
    }
}
