package Clock;

import Arch.ISubscriber;

public class ClockHMS extends ClockHM {
    protected int seconds = 0;

    public ClockHMS() {
        super();
    }

    public ClockHMS(int hours, int minutes, int seconds) {
        super(hours, minutes);
        this.setSeconds(seconds);
    }

    @Override
    protected void broadcast() {
        eventManager.broadcast(new TimeUpdateEvent(this.hours, this.minutes, this.seconds));
    }

    @Override
    public void setSeconds(int seconds) throws IllegalArgumentException {
        if (seconds < 0  || seconds > 59)
            throw new IllegalArgumentException("Invalid minutes");
        this.seconds = seconds;
        this.broadcast();
    }

    @Override
    public void addSeconds(int seconds) throws IllegalArgumentException {
        if (seconds < 0)
            throw new IllegalArgumentException("Invalid seconds");
        if (seconds >= 60) {
            this.addMinutes(seconds / 60);
            this.seconds += seconds % 60;
        } else {
            this.seconds += seconds;
        }
        this.broadcast();
    }
}
