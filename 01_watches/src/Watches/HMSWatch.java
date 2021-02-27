package Watches;

public class HMSWatch extends HMWatch {
    protected int s;

    public HMSWatch(double price_, String name_) {
        super(price_, name_);
        this.s = 0;
    }

    public void setTime(int h, int m, int s) throws Exception {
        super.setTime(h, m, s);
        if (s < 0 || s > 59)
            throw new Exception("Seconds must be between 0 and 59");
        this.s = s;
    }

    public void addTime(int h, int m, int s) throws Exception {
        this.setTime(this.h + h, this.m  + m, this.s + s);
    }
}
