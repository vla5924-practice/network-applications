package Alarm;

import Clock.Clock;
import Timeholders.IClock;

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
        int clockSeconds = 0;
        try {
            clockSeconds = clock.getSeconds();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return false;
        }
        return hours == clock.getHours() && minutes == clock.getMinutes() && seconds == clockSeconds;
    }
}
