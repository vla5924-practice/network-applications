package Watches;

public abstract class BaseWatch {
    private double price;
    private String name;

    public BaseWatch(double price_, String name_) {
        this.price = price_;
        this.name = name_;
    }

    public abstract void setTime(int h, int m, int s) throws Exception;
    public abstract void addTime(int h, int m, int s) throws Exception;

    public double getPrice() {
        return this.price;
    }

    public String getName() {
        return this.name;
    }
}
