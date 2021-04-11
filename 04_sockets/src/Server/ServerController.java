package Server;

import Arch.Event;
import Arch.EventManager;
import Arch.EventType;
import Arch.EventListener;
import Arch.JSON;
import Forms.ServerWindow;

import java.io.*;
import java.net.Socket;

public class ServerController extends Thread implements EventListener {
    protected EventManager eventManager = new EventManager();

    int id;
    Socket csocket;
    ServerModel model;

    InputStream istream;
    OutputStream ostream;
    DataInputStream distream;
    DataOutputStream dostream;

    public ServerController(int id_, Socket csocket_, ServerModel model_, ServerWindow window) {
        id = id_;
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
        eventManager.broadcast(new Event("Client #" + id + " connected"));
        send(new Event(EventType.CLOCK_SYNC, model.getClock(), model.isClockRunning()));
    }

    public void send(Event event) {
        String data = JSON.get().toJson(event);
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
                Event event = JSON.get().fromJson(data, Event.class);
                if (event.type == EventType.ALARM_ADD_REQUEST) {
                    model.addAlarm(event.alarm);
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
