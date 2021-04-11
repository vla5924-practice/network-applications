package Clock;

public class ClockController {
    Clock clock;
    Thread thread = null;

    public ClockController(Clock clock_) {
        clock = clock_;
    }

    public void start() {
        if (thread != null)
            stop();
        thread = new Thread(() -> {
            try {
                int tickDelay = clock.getTickDelay();
                while (true) {
                    Thread.sleep(tickDelay);
                    clock.tick();
                }
            } catch (InterruptedException e) {
            }
        });
        thread.start();
    }

    public void stop() {
        thread.interrupt();
        thread = null;
    }

    public void toggle() {
        if (thread == null)
            start();
        else
            stop();
    }

    public boolean isRunning() {
        return thread != null;
    }
}
