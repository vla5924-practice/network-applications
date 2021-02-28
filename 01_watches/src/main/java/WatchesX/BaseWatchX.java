package WatchesX;

public abstract class BaseWatchX {
    private String name;
    private double price;

    public BaseWatchX(String name, double price) {
        this.price = price;
        this.name = name;
    }

    public abstract void setTime(int h, int m, int s) throws Exception;
    public abstract void addTime(int h, int m, int s);
    public abstract int[] getTime();

    public double getPrice() {
        return this.price;
    }

    public String getName() {
        return this.name;
    }
}
