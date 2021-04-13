package Events;

import java.util.LinkedList;

public class EventManager {
    LinkedList<EventListener> subscribers = new LinkedList<>();

    public EventManager() {
    }

    public void addSubscriber(EventListener subscriber) {
        subscribers.add(subscriber);
    }

    public void removeSubscriber(EventListener subscriber) {
        subscribers.remove(subscriber);
    }

    public void broadcast(Event event) {
        for (EventListener subscriber: subscribers) {
            subscriber.signal(event);
        }
    }
}
