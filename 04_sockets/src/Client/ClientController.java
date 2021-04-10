package Client;

import Alarm.Alarm;
import Arch.EventManager;
import Arch.EventType;
import Arch.ISubscriber;
import Clock.ClockController;
import Clock.Clock;
import Server.ServerModel;
import Arch.Event;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientController implements ISubscriber {
    protected EventManager eventManager = new EventManager();

    int port = 5924;
    InetAddress host = null;

    Socket csocket;
    ServerModel model;

    InputStream istream;
    OutputStream ostream;
    DataInputStream distream;
    DataOutputStream dostream;

    Thread thread;

    Clock clock;
    ClockController clockController;

    Gson json = new GsonBuilder().setPrettyPrinting().create();

    public ClientController() {
    }

    public synchronized void onAlarmAdded(Event event) {
        Alarm alarm = (Alarm)event.object;
        System.out.println("Alarm added: " + alarm.getHours() + " " + alarm.getMinutes());
    }

    public void addAlarm(Alarm alarm) {
        send(new Event(EventType.ALARM_ADD_REQUEST, alarm));
    }

    public void send(Event event) {
        if (thread == null)
            return;
        String data = json.toJson(event);
        try {
            dostream.writeUTF(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void connect() {
        try {
            host = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        try {
            csocket = new Socket(host, port);
            System.out.println("Client started");

            ostream = csocket.getOutputStream();
            dostream = new DataOutputStream(ostream);

            thread = new Thread(()->{
                try {
                    istream = csocket.getInputStream();
                    distream = new DataInputStream(istream);
                    while (true) {
                        String data = distream.readUTF();
                        Event event = json.fromJson(data, Event.class);
                        if (event.type == EventType.ALARM_ADDED) {
                            onAlarmAdded(event);
                        } else {
                            System.out.println("Unknown event: " + data);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            });
            thread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void signal(Event event) {
        if (event.type == EventType.CLIENT_CONNECT_REQUEST) {
            connect();
            return;
        }
    }

    public void addSubscriber(ISubscriber subscriber) {
        eventManager.addSubscriber(subscriber);
    }
}
