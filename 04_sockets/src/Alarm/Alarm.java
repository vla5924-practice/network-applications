package Alarm;

import Arch.Event;
import Arch.EventManager;
import Arch.EventType;
import Arch.EventListener;
import Clock.Clock;
import Timeholders.IAlarm;

public abstract class Alarm implements IAlarm, EventListener {
    protected EventManager eventManager = new EventManager();

    public void addSubscriber(EventListener subscriber) {
        eventManager.addSubscriber(subscriber);
    }

    protected abstract boolean isSameTime(Clock clock);

    @Override
    public void signal(Event event) {
        if (event.type == EventType.CLOCK_UPDATED) {
            Clock clock = event.clock;
            if (isSameTime(clock)) {
                eventManager.broadcast(new Event(EventType.ALARM_WENT_OFF, this));
            }
            return;
        }
    }
}
