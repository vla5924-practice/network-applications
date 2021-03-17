import Alarm.HMSAlarm;
import Alarm.IAlarm;
import Watches.BWatches;
import Watches.IWatches;
import Watches.WatchesType;
import Watches.*;
import Alarm.*;

import java.util.Scanner;
import java.util.Vector;

public class ConsoleApplication {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        IWatches watches = BWatches.build(WatchesType.HMSWatches, "Chasi");
        while (true) {
            System.out.println("1 - Get time, 2 - Set time, 3 - Add alarm");
            int mode = in.nextInt();
            if (mode == 1) {
                System.out.println(watches);
                Vector<IAlarm> alarms = watches.isAlarmed();
                System.out.println("Alarmed " + alarms.size() + " alarms now");
            } else if (mode == 2) {
                try {
                    watches.setHours(in.nextInt());
                    watches.setMinutes(in.nextInt());
                    watches.setSeconds(in.nextInt());
                    System.out.println("Time set.");
                    System.out.println(watches);
                    Vector<IAlarm> alarms = watches.isAlarmed();
                    System.out.println("Alarmed " + alarms.size() + " alarms now");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } else if (mode == 3) {
                HMSAlarm alarm = new HMSAlarm();
                try {
                    alarm.setHours(in.nextInt());
                    alarm.setMinutes(in.nextInt());
                    alarm.setSeconds(in.nextInt());
                    watches.addAlarm(alarm);
                    System.out.println("Alarm added");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } else {
                System.out.println("Invalid mode.");
            }
        }
    }
}
