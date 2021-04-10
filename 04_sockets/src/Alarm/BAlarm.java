package Alarm;

import Timeholders.TimeholderType;

public class BAlarm {
    public static Alarm build(TimeholderType type) {
        return switch (type) {
            case HM -> new AlarmHM();
            case HMS -> new AlarmHMS();
        };
    }
}
