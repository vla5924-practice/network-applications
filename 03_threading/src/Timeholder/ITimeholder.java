package Timeholder;

public interface ITimeholder {
    void setHours(int hours) throws IllegalArgumentException;
    void setMinutes(int minutes) throws IllegalArgumentException;
    void setSeconds(int seconds) throws NoSuchMethodException, IllegalArgumentException;
    int getHours();
    int getMinutes();
    int getSeconds() throws NoSuchMethodException;
}
