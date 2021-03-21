package Alarm;

import Arch.AbstractEvent;
import Arch.ICallable;
import Arch.ISubscriber;
import Clock.TimeUpdateEvent;

import java.io.InvalidClassException;

public class AlarmHM implements IAlarm, ISubscriber {
    protected ICallable slot;
    protected int hours = 0;
    protected int minutes = 0;

    public AlarmHM() {
    }

    public AlarmHM(int hours, int minutes) {
        this.setHours(hours);
        this.setMinutes(minutes);
    }

    @Override
    public void signal(AbstractEvent event) throws IllegalArgumentException {
        if(!event.getClass().isAssignableFrom(new TimeUpdateEvent().getClass()))
            throw new IllegalArgumentException("Unsupported event class");
        TimeUpdateEvent time = (TimeUpdateEvent)event;
        if (time.hours == this.hours && time.minutes == this.minutes) {
            slot.call();
        }
    }

    @Override
    public void setSlot(ICallable slot) {
        this.slot = slot;
    }

    @Override
    public void setHours(int hours) {
        if (hours < 0 || hours > 11)
            throw new IllegalArgumentException("Invalid hours");
        this.hours = hours;
    }

    @Override
    public void setMinutes(int minutes) throws IllegalArgumentException {
        if (minutes < 0  || minutes > 59)
            throw new IllegalArgumentException("Invalid minutes");
        this.minutes = minutes;
    }

    @Override
    public void setSeconds(int seconds) throws NoSuchMethodException {
        throw new NoSuchMethodException("Not implemented");
    }

    @Override
    public int getHours() {
        return this.hours;
    }

    @Override
    public int getMinutes() {
        return this.minutes;
    }

    @Override
    public int getSeconds() throws NoSuchMethodException {
        throw new NoSuchMethodException("Not implemented");
    }
}
