package Alarm;

import Clock.Clock;

import javax.persistence.*;

@Entity
@Table(name = "alarm_hm")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class AlarmHM extends Alarm {
    @Column
    protected int hours = 0;

    @Column
    protected int minutes = 0;

    public AlarmHM() {
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

    @Override
    protected boolean isSameTime(Clock clock) {
        return hours == clock.getHours() && minutes == clock.getMinutes();
    }

    @Override
    public String toString() {
        return "%02d:%02d:%02d".formatted(hours, minutes);
    }
}
