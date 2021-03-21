package Clock;

import Timeholder.ETimeholderType;

public class BClock {
    public static IClock build(ETimeholderType type) {
        return switch (type) {
            case HM -> new ClockHM();
            case HMS -> new ClockHMS();
            default -> null;
        };
    }
}
