package Clock;

import Timeholder.ITimeholder;

public interface IClock extends ITimeholder {
    void addMinutes(int minutes) throws IllegalArgumentException;
    void addSeconds(int seconds) throws NoSuchMethodException, IllegalArgumentException;
}
