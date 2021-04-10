package Arch;

public class ServiceMessage {
    private String message;

    public ServiceMessage(String message_) {
        message = message_;
    }

    public String get() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }
}
