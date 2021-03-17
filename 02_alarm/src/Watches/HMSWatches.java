package Watches;

import Alarm.IAlarm;

import java.util.Vector;

public class HMSWatches extends HMWatches {
    int seconds = 0;

    public HMSWatches(String name) {
        super(name);
    }

    @Override
    public void setSeconds(int seconds) throws Exception {
        if (seconds < 0 || seconds > 59)
            throw new Exception("Invalid seconds");
        this.seconds = seconds;
    }

    @Override
    public void addSeconds(int seconds) {
        this.seconds = (this.seconds + seconds) % 60;
    }

    public Vector<IAlarm> isAlarmed() {
        Vector<IAlarm> alarmed = new Vector<IAlarm>();
        for (int i = 0; i < alarms.size(); i++) {
            IAlarm alarm = alarms.elementAt(i);
            int alarmSeconds = 0;
            try { alarmSeconds = alarm.getSeconds(); } catch (Exception e) {}
            if (alarm.getHours() == this.hours
                    && alarm.getMinutes() == this.minutes
                    && alarmSeconds == this.seconds)
                alarmed.add(alarm);
        }
        return alarmed;
    }

    public String toString() {
        return super.toString() + ":" + seconds;
    }
}
