package Watches;

import Alarm.IAlarm;

import java.util.Vector;

public interface IWatches {
    void setHours(int hours) throws Exception;
    void setMinutes(int minutes) throws Exception;
    void setSeconds(int seconds) throws Exception;
    void addHours(int hours);
    void addMinutes(int minutes);
    void addSeconds(int seconds) throws Exception;
    void addAlarm(IAlarm alarm);
    Vector<IAlarm> isAlarmed();
}
