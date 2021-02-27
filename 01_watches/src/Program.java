import Watches.HMWatch;
import Watches.HMSWatch;

public class Program {
    public static void main(String[] args) {
        System.out.println("----- 01_watches -----");
        HMWatch watches1 = new HMWatch(499.90, "Chasi1");
        HMSWatch watches2 = new HMSWatch(249.90, "Chasi2");
        System.out.println("Watches 1 price: " + watches1.getPrice());
        System.out.println("Watches 2 price: " + watches2.getPrice());
    }
}
