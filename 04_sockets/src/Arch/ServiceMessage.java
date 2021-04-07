package Arch;

public class ServiceMessage {
    private String message;

    public ServiceMessage(String message_) {
        message = message_;
    }

    String get() {
        return message;
    }
}
