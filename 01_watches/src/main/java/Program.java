import WatchesAdvanced.*;

public class Program {
    public static String glueTime(int[] time) {
        return time[0] + ":" + time[1] + ":" + time[2];
    }

    public static void main(String[] args) {
        System.out.println("----------- 01_watches -----------");
        HMWatch watches1 = new HMWatch("Chasi1", 449.90);
        HMSWatch watches2 = new HMSWatch("Chasi2", 249.90);
        System.out.println("Watches 1 name: " + watches1.getName());
        System.out.println("Watches 2 name: " + watches2.getName());
        System.out.println("Watches 1 price: " + watches1.getPrice());
        System.out.println("Watches 2 price: " + watches2.getPrice());
        try {
            watches1.setTime(10, 20);
            System.out.println("Watches 1 time: " + glueTime(watches1.getTime()));
            watches1.setTime(10, 99);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            watches2.setTime(10, 20, 30);
            System.out.println("Watches 2 time: " + glueTime(watches2.getTime()));
            watches2.setTime(10, 20, 99);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        watches1.addTime(2, 2);
        System.out.println("Watches 1 time: " + glueTime(watches1.getTime()));
        watches1.addTime(22, 22);
        System.out.println("Watches 1 time: " + glueTime(watches1.getTime()));
        watches2.addTime(2, 2, 2);
        System.out.println("Watches 2 time: " + glueTime(watches2.getTime()));
        watches2.addTime(22, 22, 22);
        System.out.println("Watches 2 time: " + glueTime(watches2.getTime()));
    }
}
