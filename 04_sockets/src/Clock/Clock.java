package Clock;

import Arch.Event;
import Arch.EventManager;
import Arch.EventType;
import Arch.EventListener;
import Timeholders.IClock;

public abstract class Clock implements IClock {
    protected EventManager eventManager = new EventManager();

    protected void broadcast() {
        eventManager.broadcast(new Event(EventType.CLOCK_UPDATED, this));
    }

    public void addSubscriber(EventListener subscriber) {
        eventManager.addSubscriber(subscriber);
    }
}
