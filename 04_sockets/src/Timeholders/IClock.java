package Timeholders;

import Timeholders.ITimeholder;

public interface IClock extends ITimeholder {
    void addHours(int hours) throws IllegalArgumentException;
    void addMinutes(int minutes) throws IllegalArgumentException;
    void addSeconds(int seconds) throws NoSuchMethodException, IllegalArgumentException;
    void tick();
    int getTickDelay();
}
