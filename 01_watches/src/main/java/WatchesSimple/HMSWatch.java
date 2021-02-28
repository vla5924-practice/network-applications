package WatchesSimple;

public class HMSWatch {
    private String name;
    private double price;
    private int h;
    private int m;
    private int s;

    public HMSWatch(String name, double price) {
        this.name = name;
        this.price = price;
        this.h = 0;
        this.m = 0;
        this.s = 0;
    }

    public void setTime(int h, int m, int s) throws Exception {
        if (h < 0 || h > 11)
            throw new Exception("Hours must be between 0 and 11");
        if (m < 0 || m > 59)
            throw new Exception("Minutes must be between 0 and 59");
        if (s < 0 || s > 59)
            throw new Exception("Seconds must be between 0 and 59");
        this.h = h;
        this.m = m;
        this.s = s;
    }

    public void addTime(int h, int m, int s) {
        this.h = (this.h + h) % 12;
        this.m = (this.m + m) % 60;
        this.s = (this.s + s) % 60;
    }

    public int[] getTime() {
        return new int[]{ this.h, this.m, this.s };
    }

    public double getPrice() {
        return this.price;
    }

    public String getName() {
        return this.name;
    }
}
