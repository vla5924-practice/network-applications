package Clock;

public class ClockHMS extends ClockHM {
    protected int seconds = 0;

    public ClockHMS() {
        super();
    }

    public ClockHMS(int hours, int minutes, int seconds) {
        super(hours, minutes);
        this.setSeconds(seconds);
    }

    @Override
    public void setSeconds(int seconds) throws IllegalArgumentException {
        if (seconds < 0  || seconds > 59)
            throw new IllegalArgumentException("Invalid minutes");
        this.seconds = seconds;
        this.broadcast();
    }

    @Override
    public void addSeconds(int seconds) throws IllegalArgumentException {
        if (seconds < 0)
            throw new IllegalArgumentException("Invalid seconds");
        int newSeconds = this.seconds + seconds;
        if (newSeconds >= 60) {
            this.seconds = newSeconds % 60;
            this.addMinutes(newSeconds / 60);
        } else {
            this.seconds += seconds;
            this.broadcast();
        }
    }

    @Override
    public int getSeconds() {
        return this.seconds;
    }

    @Override
    public void tick() {
        this.addSeconds(1);
    }

    @Override
    public int getTickDelay() {
        return 999;
    }

    @Override
    public String toString() {
        return "%02d:%02d:%02d".formatted(hours, minutes, seconds);
    }
}
