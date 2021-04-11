package Server;

import Alarm.Alarm;
import Arch.Event;
import Arch.EventManager;
import Arch.EventType;
import Arch.EventListener;
import Clock.BClock;
import Clock.Clock;
import Clock.ClockController;
import Timeholders.TimeholderType;

import java.util.LinkedList;

public class ServerModel implements EventListener {
    private EventManager eventManager = new EventManager();

    private Clock clock = BClock.build(TimeholderType.HMS);
    private ClockController clockController = new ClockController(clock);
    private LinkedList<Alarm> alarms = new LinkedList<>();

    public ServerModel() {
    }

    public void addSubscriber(EventListener subscriber) {
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
