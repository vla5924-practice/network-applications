package Server;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Server {
    int port = 5924;
    InetAddress host = null;

    ServerModel model = new ServerModel();

    public Server() {
        try {
            host = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        try {
            ServerSocket ssocket = null;
            ssocket = new ServerSocket(port, 0, host);
            System.out.println("Server started");
            while (true) {
                Socket csocket = ssocket.accept();
                System.out.println("Client connected");

                ServerController runner = new ServerController(csocket, model);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Server();
    }
}
