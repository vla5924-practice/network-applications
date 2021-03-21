package Clock;

import Arch.IPublisher;
import Arch.ISubscriber;

public class ClockHM implements IClock, IPublisher {
    protected Arch.EventManager eventManager = new Arch.EventManager();
    protected int hours = 0;
    protected int minutes = 0;

    public ClockHM() {
    }

    public ClockHM(int hours, int minutes) {
        this.setHours(hours);
        this.setMinutes(minutes);
    }

    protected void broadcast() {
        eventManager.broadcast(new TimeUpdateEvent(this.hours, this.minutes));
    }

    @Override
    public void addSubscriber(ISubscriber subscriber) {
        eventManager.subscribe(subscriber);
    }

    @Override
    public void removeSubscriber(ISubscriber subscriber) {
        eventManager.unsubscribe(subscriber);
    }

    @Override
    public void setHours(int hours) {
        if (hours < 0 || hours > 11)
            throw new IllegalArgumentException("Invalid hours");
        this.hours = hours;
        this.broadcast();
    }

    @Override
    public void setMinutes(int minutes) throws IllegalArgumentException {
        if (minutes < 0  || minutes > 59)
            throw new IllegalArgumentException("Invalid minutes");
        this.minutes = minutes;
        this.broadcast();
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

    @Override
    public void addMinutes(int minutes) throws IllegalArgumentException {
        if (minutes < 0)
            throw new IllegalArgumentException("Invalid minutes");
        int newMinutes = this.minutes + minutes;
        if (newMinutes >= 60) {
            this.hours += newMinutes / 60;
            this.minutes = minutes % 60 - 1;
        } else {
            this.minutes += minutes;
        }
        this.broadcast();
    }

    @Override
    public void addSeconds(int seconds) throws NoSuchMethodException {
        throw new NoSuchMethodException("Not implemented");
    }
}
