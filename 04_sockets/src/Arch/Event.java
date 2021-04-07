package Arch;

public class Event {
    public EventType type;
    public Object object;

    public Event(EventType type_, Object object_) {
        type = type_;
        object = object_;
    }

    public Event(String message) {
        type = EventType.SERVICE_MESSAGE;
        object = new ServiceMessage(message);
    }
}
