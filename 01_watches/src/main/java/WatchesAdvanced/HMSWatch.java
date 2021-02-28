package WatchesAdvanced;

public class HMSWatch extends HMWatch {
    protected int s;

    public HMSWatch(String name, double price) {
        super(name, price);
        this.s = 0;
    }

    public void setTime(int h, int m, int s) throws Exception {
        super.setTime(h, m, s);
        if (s < 0 || s > 59)
            throw new Exception("Seconds must be between 0 and 59");
        this.s = s;
    }

    public void addTime(int h, int m, int s) {
        super.addTime(h, m, 0);
        this.s = (this.s + s) % 60;
    }

    public int[] getTime() {
        return new int[]{ this.h, this.m, this.s };
    }
}
