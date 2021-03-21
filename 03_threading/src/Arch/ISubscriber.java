package Arch;

public interface ISubscriber {
    void signal(AbstractEvent event) throws IllegalArgumentException;
}
