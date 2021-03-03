package WatchesAdvanced;

public abstract class BaseWatch {
    private String name;
    private double price;

    public BaseWatch(String name, double price) {
        this.price = price;
        this.name = name;
    }

    public double getPrice() {
        return this.price;
    }

    public String getName() {
        return this.name;
    }
}
