package Clock;

import Timeholders.IClock;

public class ClockController {
    IClock clock;
    Thread thread = null;

    public ClockController(IClock clock) {
        this.clock = clock;
    }

    public void start() {
        this.thread = new Thread(() -> {
            try {
                int tickDelay = clock.getTickDelay();
                while (true) {
                    Thread.sleep(tickDelay);
                    clock.tick();
                }
            } catch (InterruptedException e) {
                return;
            }
        });
        this.thread.start();
    }

    public void stop() {
        this.thread.interrupt();
        this.thread = null;
    }

    public void toggle() {
        if (this.thread == null)
            this.start();
        else
            this.stop();
    }
}
