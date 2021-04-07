package Arch;

import java.util.LinkedList;

public class EventManager {
    LinkedList<ISubscriber> subscribers = new LinkedList<>();

    public EventManager() {
    }

    public void addSubscriber(ISubscriber subscriber) {
        subscribers.add(subscriber);
    }

    public void removeSubscriber(ISubscriber subscriber) {
        subscribers.remove(subscriber);
    }

    public void broadcast(Event event) {
        for (ISubscriber subscriber: subscribers) {
            subscriber.signal(event);
        }
    }
}
