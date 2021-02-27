package Watches;

public class HMWatch extends BaseWatch {
    protected int h;
    protected int m;

    public HMWatch(double price_, String name_) {
        super(price_, name_);
        this.h = 0;
        this.m = 0;
    }

    public void setTime(int h, int m, int s) throws Exception {
        if (h < 0 || h > 23)
            throw new Exception("Hours must be between 0 and 23");
        if (m < 0 || m > 59)
            throw new Exception("Minutes must be between 0 and 59");
        this.h = h;
        this.m = m;
    }

    public void addTime(int h, int m, int s) throws Exception {
        this.setTime(this.h + h, this.m  + m, 0);
    }
}
