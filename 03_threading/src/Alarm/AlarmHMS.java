package Alarm;

import Arch.AbstractEvent;
import Clock.TimeUpdateEvent;

import java.io.InvalidClassException;

public class AlarmHMS extends AlarmHM {
    protected int seconds = 0;

    public AlarmHMS() {
        super();
    }

    public AlarmHMS(int hours, int minutes, int seconds) {
        super(hours, minutes);
        setSeconds(seconds);
    }

    @Override
    public void signal(AbstractEvent event) throws IllegalArgumentException {
        if(!event.getClass().isAssignableFrom(new TimeUpdateEvent().getClass()))
            throw new IllegalArgumentException("Unsupported event class");
        TimeUpdateEvent time = (TimeUpdateEvent)event;
        if (time.hours == this.hours && time.minutes == this.minutes && time.seconds == this.seconds) {
            slot.call();
        }
    }

    @Override
    public void setSeconds(int seconds) throws IllegalArgumentException {
        if (seconds < 0  || seconds > 59)
            throw new IllegalArgumentException("Invalid seconds");
        this.seconds = seconds;
    }

    @Override
    public int getSeconds() {
        return this.seconds;
    }
}
