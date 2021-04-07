package Clock;

import Arch.Event;
import Arch.EventManager;
import Arch.EventType;
import Arch.ISubscriber;
import Timeholders.IClock;

public abstract class Clock implements IClock {
    protected EventManager eventManager = new EventManager();

    protected void broadcast() {
        eventManager.broadcast(new Event(EventType.CLOCK_UPDATED, this));
    }

    public void addSubscriber(ISubscriber subscriber) {
        eventManager.addSubscriber(subscriber);
    }
}
