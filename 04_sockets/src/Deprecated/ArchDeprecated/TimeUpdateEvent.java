package Deprecated.ArchDeprecated;

public class TimeUpdateEvent extends AbstractEvent {
    public int hours = 0;
    public int minutes = 0;
    public int seconds = 0;

    public TimeUpdateEvent() {
    }

    public TimeUpdateEvent(int hours, int minutes, int seconds) {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    public TimeUpdateEvent(int hours, int minutes) {
        this.hours = hours;
        this.minutes = minutes;
    }
}
