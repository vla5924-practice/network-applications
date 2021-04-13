package Events;

import Alarm.Alarm;
import Clock.Clock;

public class Event {
    public EventType type;

    public String message = null;
    public Clock clock = null;
    public Alarm alarm = null;
    public boolean running = false;

    public Event(EventType type_, Clock clock_) {
        type = type_;
        clock = clock_;
    }

    public Event(EventType type_, Clock clock_, boolean running_) {
        type = type_;
        clock = clock_;
        running = running_;
    }

    public Event(EventType type_, Alarm alarm_) {
        type = type_;
        alarm = alarm_;
    }

    public Event(EventType type_) {
        type = type_;
    }

    public Event(String message_) {
        type = EventType.SERVICE_MESSAGE;
        message = message_;
    }
}
