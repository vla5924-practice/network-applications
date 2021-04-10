package Client;

import Forms.ClientWindow;
import Forms.ServerWindow;
import Server.Server;

import javax.swing.*;

public class Client {
    ClientController controller = new ClientController();

    public Client() {
    }

    public void addWindow(ClientWindow window) {
        window.addSubscriber(controller);
        controller.addSubscriber(window);
    }

    public static void main(String[] args) {
        Client client = new Client();
        ClientWindow window = new ClientWindow();
        client.addWindow(window);
        JFrame frame = new JFrame("Client");
        frame.setContentPane(window.getPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
