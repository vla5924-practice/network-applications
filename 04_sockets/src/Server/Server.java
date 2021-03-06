package Server;

import Forms.ServerWindow;

import javax.swing.*;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Server {
    int port = 5924;
    InetAddress host = null;
    ServerSocket socket = null;

    ServerModel model = new ServerModel();
    int nofClients = 0;
    ServerWindow window;

    public Server() {
        try {
            host = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        try {
            socket = new ServerSocket(port, 0, host);
            System.out.println("Server started");
            while (true) {
                Socket csocket = socket.accept();
                nofClients++;
                new ServerController(nofClients, csocket, model, window);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addWindow(ServerWindow window_) {
        window = window_;
        model.addClockSubscriber(window);
        model.addSubscriber(window);
        window.addSubscriber(model);
    }

    public static void main(String[] args) {
        Server server = new Server();
        ServerWindow window = new ServerWindow();
        server.addWindow(window);
        JFrame frame = new JFrame("Server");
        frame.setContentPane(window.getPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        server.start();
    }
}
