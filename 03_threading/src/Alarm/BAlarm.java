package Alarm;

import Timeholder.ETimeholderType;

public class BAlarm {
    public static IAlarm build(ETimeholderType type) {
        return switch (type) {
            case HM -> new AlarmHM();
            case HMS -> new AlarmHMS();
            default -> null;
        };
    }
}
