package Client;

import Alarm.Alarm;
import Arch.*;
import Clock.ClockController;
import Clock.Clock;
import Server.ServerModel;

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

    public ClientController() {
    }

    public synchronized void onAlarmAdded(Event event) {
        eventManager.broadcast(event);
    }

    public void onAlarmWentOff(Event event) {
        eventManager.broadcast(event);
    }

    public void onClockUpdated(Event event) {
        if (clockController != null)
            clockController.stop();
        clock = event.clock;
        clockController = new ClockController(clock);
        eventManager.broadcast(event);
    }

    public void send(Event event) {
        if (thread == null)
            return;
        String data = JSON.get().toJson(event);
        System.out.println(data);
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
                        Event event = JSON.get().fromJson(data, Event.class);
                        if (event.type == EventType.ALARM_ADDED) {
                            onAlarmAdded(event);
                        } else if (event.type == EventType.CLOCK_UPDATED || event.type == EventType.CLOCK_SYNC) {
                            onClockUpdated(event);
                        } else if (event.type == EventType.ALARM_WENT_OFF) {
                            onAlarmWentOff(event);
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
        if (event.type == EventType.ALARM_ADD_REQUEST) {
            send(event);
            return;
        }
    }

    public void addSubscriber(ISubscriber subscriber) {
        eventManager.addSubscriber(subscriber);
    }
}
