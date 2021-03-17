package Watches;

import java.util.Vector;
import Alarm.IAlarm;

public class HMWatches implements IWatches {
    String name;
    int hours = 0;
    int minutes = 0;
    Vector<IAlarm> alarms = new Vector<IAlarm>();

    public HMWatches(String name) {
        this.name = name;
    }

    @Override
    public void setHours(int hours) throws Exception {
        if (hours < 0 || hours > 11)
            throw new Exception("Invalid hours");
        this.hours = hours;
    }

    @Override
    public void setMinutes(int minutes) throws Exception {
        if (minutes < 0 || minutes > 59)
            throw new Exception("Invalid minutes");
        this.minutes = minutes;
    }

    @Override
    public void setSeconds(int seconds) throws Exception {
        throw new Exception("Seconds are unsupported");
    }

    @Override
    public void addHours(int hours) {
        this.hours = (this.hours + hours) % 12;
    }

    @Override
    public void addMinutes(int minutes) {
        this.minutes = (this.minutes + minutes) % 60;
    }

    @Override
    public void addSeconds(int seconds) throws Exception {
        throw new Exception("Seconds are unsupported");
    }

    public void addAlarm(IAlarm alarm) {
        alarms.add(alarm);
    }

    public Vector<IAlarm> isAlarmed() {
        Vector<IAlarm> alarmed = new Vector<IAlarm>();
        for (int i = 0; i < alarms.size(); i++) {
            IAlarm alarm = alarms.elementAt(i);
            if (alarm.getHours() == this.hours
                    && alarm.getMinutes() == this.minutes)
                alarmed.add(alarm);
        }
        return alarmed;
    }

    public String toString() {
        return hours + ":" + minutes;
    }
}
