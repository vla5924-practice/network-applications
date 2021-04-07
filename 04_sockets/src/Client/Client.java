package Client;

public class Client {
    ClientController controller = new ClientController();

    public Client() {
        controller.connect();
    }

    public static void main(String[] args) {
        new Client();
    }
}
