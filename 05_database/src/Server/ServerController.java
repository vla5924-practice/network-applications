package Server;

import Alarm.Alarm;
import Events.Event;
import Events.EventManager;
import Events.EventType;
import Events.EventListener;
import Events.JSON;
import Forms.ServerWindow;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;

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
        LinkedList<Alarm> alarms = model.getAlarms();
        for (Alarm alarm: alarms) {
            send(new Event(EventType.ALARM_ADDED, alarm));
        }
    }

    public void send(Event event) {
        String data = JSON.get().toJson(event);
        try {
            dostream.writeUTF(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onAlarmAddRequest(Event event) {
        model.addAlarm(event.alarm);
        eventManager.broadcast(event);
    }

    public void onAlarmDeleteRequest(Event event) {
        model.deleteAlarm(event.alarm);
        eventManager.broadcast(event);
    }

    @Override
    public void run() {
        try {
            istream = csocket.getInputStream();
            distream = new DataInputStream(istream);
            do {
                String data = distream.readUTF();
                System.out.println(data);
                Event event = JSON.get().fromJson(data, Event.class);
                if (event.type == EventType.ALARM_ADD_REQUEST) {
                    onAlarmAddRequest(event);
                } else if (event.type == EventType.ALARM_DELETE_REQUEST) {
                    onAlarmDeleteRequest(event);
                } else if (event.type == EventType.CLIENT_DISCONNECT_REQUEST) {
                    csocket.close();
                    eventManager.broadcast(new Event("Client disconnected"));
                    return;
                } else {
                    System.out.println("[Server controller run] Unsupported event: " + event.type);
                }
            } while (true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void signal(Event event) {
        send(event);
    }
}
