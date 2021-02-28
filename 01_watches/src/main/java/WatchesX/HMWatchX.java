package WatchesX;

public class HMWatchX extends BaseWatchX {
    private int h;
    private int m;

    public HMWatchX(String name, double price) {
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

    public void addTime(int h, int m, int s) {
        this.h = (this.h + h) % 12;
        this.m = (this.m + m) % 60;
    }

    public int[] getTime() {
        return new int[]{ this.h, this.m, 0 };
    }
}
