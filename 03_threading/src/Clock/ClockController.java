package Clock;

public class ClockController {
    IClock clock;
    boolean started = false;

    public ClockController(IClock clock) {
        this.clock = clock;
    }

    public void start() {
        this.started = true;
        new Thread(() -> {
            try {
                while (true) {
                    Thread.sleep(999);
                    if (started)
                        clock.addSeconds(1);
                    else
                        return;
                }
            } catch (Exception e) {
                try {
                    while (true) {
                        Thread.sleep(59999);
                        if (started)
                            clock.addMinutes(1);
                        else
                            return;
                    }
                } catch (Exception ee) {
                }
            }
        }).start();
    }

    public void stop() {
        this.started = false;
    }

    public void toggle() {
        if (this.started)
            this.stop();
        else
            this.start();
    }
}
