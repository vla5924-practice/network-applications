package Alarm;

public interface IAlarm {
    void setHours(int hours) throws Exception;
    void setMinutes(int minutes) throws Exception;
    void setSeconds(int seconds) throws Exception;

    int getHours();
    int getMinutes();
    int getSeconds() throws Exception;
}
