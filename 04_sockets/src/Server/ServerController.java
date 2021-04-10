package Server;

import Alarm.Alarm;
import Arch.EventManager;
import Arch.EventType;
import Arch.Event;
import Arch.ISubscriber;
import Forms.ServerWindow;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.net.Socket;

public class ServerController extends Thread implements ISubscriber {
    protected EventManager eventManager = new EventManager();

    Socket csocket;
    ServerModel model;

    InputStream istream;
    OutputStream ostream;
    DataInputStream distream;
    DataOutputStream dostream;

    Gson json = new GsonBuilder().setPrettyPrinting().create();

    public ServerController(Socket csocket_, ServerModel model_, ServerWindow window) {
        csocket = csocket_;
        model = model_;
        try {
            ostream = csocket.getOutputStream();
            dostream = new DataOutputStream(ostream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        model.addSubscriber(this);
        eventManager.addSubscriber(window);
        start();
        send(new Event("Server controller started"));
    }

    public void send(Event event) {
        String data = json.toJson(event);
        try {
            dostream.writeUTF(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            istream = csocket.getInputStream();
            distream = new DataInputStream(istream);
            do {
                String data = distream.readUTF();
                Event event = json.fromJson(data, Event.class);
                if (event.type == EventType.ALARM_ADD_REQUEST) {
                    model.addAlarm((Alarm)event.object);
                }
            } while (true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void signal(Event event) {
        send(event);
        eventManager.broadcast(event);
    }
}
