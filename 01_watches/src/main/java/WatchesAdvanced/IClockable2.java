package WatchesAdvanced;

public interface IClockable2 {
    public abstract void setTime(int h, int m) throws Exception;
    public abstract void addTime(int h, int m);
    public abstract int[] getTime();
}
