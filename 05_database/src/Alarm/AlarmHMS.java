package Alarm;

import Clock.Clock;

import javax.persistence.*;

@Entity
@Table(name = "alarms_hms")
public class AlarmHMS extends AlarmHM {
    @Column
    protected int seconds = 0;

    public int getId() {
        return id;
    }

    public AlarmHMS() {
        super();
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

    @Override
    protected boolean isSameTime(Clock clock) {
        int clockSeconds;
        try {
            clockSeconds = clock.getSeconds();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return false;
        }
        return hours == clock.getHours() && minutes == clock.getMinutes() && seconds == clockSeconds;
    }

    @Override
    public String toString() {
        return "%02d:%02d:%02d".formatted(hours, minutes, seconds);
    }
}
