package WatchesAdvanced;

public interface IClockable3 {
    public abstract void setTime(int h, int m, int s) throws Exception;
    public abstract void addTime(int h, int m, int s);
    public abstract int[] getTime();
}
