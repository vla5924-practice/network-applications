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
    protected EventManager eventManager = new EventManager();

    Clock clock = BClock.build(TimeholderType.HMS);
    ClockController clockController = new ClockController(clock);
    LinkedList<Alarm> alarms = new LinkedList<>();

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

    public Alarm getLastAlarm() {
        return alarms.getLast();
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
            int[] arr = (int[])event.object;
            clock.setHours(arr[0]);
            clock.setMinutes(arr[1]);
            try {
                clock.setSeconds(arr[2]);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    public void addClockSubscriber(ISubscriber subscriber) {
        clock.addSubscriber(subscriber);
    }
}
