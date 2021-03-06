package Watches;

public class HMWatch extends BaseWatch {
    protected int h;
    protected int m;

    public HMWatch(String name, double price) {
        super(name, price);
        this.h = 0;
        this.m = 0;
    }

    public void setTime(int h, int m, int s) throws Exception {
        if (h < 0 || h > 11)
            throw new Exception("Hours must be between 0 and 11");
        if (m < 0 || m > 59)
            throw new Exception("Minutes must be between 0 and 59");
        this.h = h;
        this.m = m;
    }

    public void addTime(int h, int m, int s) throws Exception {
        this.setTime(this.h + h, this.m  + m, 0);
    }
}
