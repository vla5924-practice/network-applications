package Server;

import Alarm.Alarm;
import Arch.EventManager;
import Arch.EventType;
import Clock.Clock;
import Timeholders.IAlarm;
import Arch.Event;
import Arch.ISubscriber;

import java.util.LinkedList;

public class ServerModel implements ISubscriber {
    protected EventManager eventManager = new EventManager();

    Clock clock;
    LinkedList<Alarm> alarms = new LinkedList<>();

    public ServerModel() {
    }

    public void addSubscriber(ISubscriber subscriber) {
        eventManager.addSubscriber(subscriber);
    }

    public void addAlarm(Alarm alarm) {
        alarm.addSubscriber(this);
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
        if (event.type != EventType.ALARM_WENT_OFF)
            return;
        eventManager.broadcast(event);
    }
}
