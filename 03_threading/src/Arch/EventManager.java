package Arch;

import java.util.LinkedList;

public class EventManager {
    LinkedList<ISubscriber> subscribers = new LinkedList<ISubscriber>();

    public EventManager() {
    }

    public void subscribe(ISubscriber subscriber) {
        subscribers.add(subscriber);
    }

    public void unsubscribe(ISubscriber subscriber) {
        subscribers.remove(subscriber);
    }

    public void broadcast(AbstractEvent event) {
        for (ISubscriber subscriber: subscribers) {
            subscriber.signal(event);
        }
    }
}
