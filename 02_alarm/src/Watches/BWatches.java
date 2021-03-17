package Watches;

public class BWatches {
    public static IWatches build(WatchesType type, String name) {
        if (type == WatchesType.HMWatches)
            return new HMWatches(name);
        else if (type == WatchesType.HMSWatches)
            return new HMSWatches(name);
        else
            return null;
    }
}
