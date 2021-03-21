package Arch;

public interface IPublisher {
    void addSubscriber(ISubscriber subscriber);
    void removeSubscriber(ISubscriber subscriber);
}
