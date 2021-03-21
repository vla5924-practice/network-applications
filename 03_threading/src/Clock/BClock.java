package Clock;

public class BClock {
    public static IClock build(EClockType type) {
        return switch (type) {
            case HM -> new ClockHM();
            case HMS -> new ClockHMS();
            default -> null;
        };
    }
}
