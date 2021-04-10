package Clock;

import Timeholders.TimeholderType;

public class BClock {
    public static Clock build(TimeholderType type) {
        return switch (type) {
            case HM -> new ClockHM();
            case HMS -> new ClockHMS();
        };
    }
}
