package Alarm;

import Arch.Event;
import Arch.EventManager;
import Arch.EventType;
import Arch.ISubscriber;
import Timeholders.IAlarm;
import Timeholders.IClock;

public abstract class Alarm implements IAlarm, ISubscriber {
    protected EventManager eventManager = new EventManager();

    public void addSubscriber(ISubscriber subscriber) {
        eventManager.addSubscriber(subscriber);
    }

    protected abstract boolean isSameTime(IClock clock);
    @Override
    public void signal(Event event) {
        if (event.type != EventType.CLOCK_UPDATED)
            return;
        IClock clock = (IClock)event.object;
        if (isSameTime(clock)) {
            eventManager.broadcast(new Event(EventType.ALARM_WENT_OFF, this));
        }
    }
}