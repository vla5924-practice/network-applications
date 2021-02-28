import Watches.HMWatch;
import Watches.HMSWatch;
import WatchesX.*;

public class Program {
    public static void main(String[] args) {
        System.out.println("----- 01_watches -----");
        HMWatch watches1 = new HMWatch("Chasi1", 449.90);
        HMSWatch watches2 = new HMSWatch("Chasi2", 249.90);
        System.out.println("Watches 1 price: " + watches1.getPrice());
        System.out.println("Watches 2 price: " + watches2.getPrice());
    }
}
