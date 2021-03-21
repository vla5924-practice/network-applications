package Arch;

import java.util.concurrent.Callable;

public interface ISubscriber {
    void signal(AbstractEvent event) throws IllegalArgumentException;
    void setSlot(ICallable slot);
}
