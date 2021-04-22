package Clock;

public class ClockHM extends Clock {
    protected int hours = 0;
    protected int minutes = 0;

    public ClockHM() {
    }

    public ClockHM(int hours, int minutes) {
        this.setHours(hours);
        this.setMinutes(minutes);
    }

    @Override
    public void setHours(int hours) {
        if (hours < 0 || hours > 11)
            throw new IllegalArgumentException("Invalid hours");
        this.hours = hours;
        this.broadcast();
    }

    @Override
    public void setMinutes(int minutes) throws IllegalArgumentException {
        if (minutes < 0  || minutes > 59)
            throw new IllegalArgumentException("Invalid minutes");
        this.minutes = minutes;
        this.broadcast();
    }

    @Override
    public void setSeconds(int seconds) throws NoSuchMethodException {
        throw new NoSuchMethodException("Not implemented");
    }

    @Override
    public int getHours() {
        return this.hours;
    }

    @Override
    public int getMinutes() {
        return this.minutes;
    }

    @Override
    public int getSeconds() throws NoSuchMethodException {
        throw new NoSuchMethodException("Not implemented");
    }

    @Override
    public void addHours(int hours) throws IllegalArgumentException {
        if (hours < 0)
            throw new IllegalArgumentException("Invalid hours");
        int newHours = this.hours + hours;
        if (newHours >= 12) {
            this.hours = newHours % 12;
        } else {
            this.hours += hours;
        }
        this.broadcast();
    }

    @Override
    public void addMinutes(int minutes) throws IllegalArgumentException {
        if (minutes < 0)
            throw new IllegalArgumentException("Invalid minutes");
        int newMinutes = this.minutes + minutes;
        if (newMinutes >= 60) {
            this.minutes = newMinutes % 60;
            this.addHours(newMinutes / 60);
        } else {
            this.minutes += minutes;
            this.broadcast();
        }
    }

    @Override
    public void addSeconds(int seconds) throws NoSuchMethodException {
        throw new NoSuchMethodException("Not implemented");
    }

    @Override
    public void tick() {
        this.addMinutes(1);
    }

    @Override
    public int getTickDelay() {
        return 59999;
    }

    @Override
    public String toString() {
        return "%02d:%02d".formatted(hours, minutes);
    }
}
