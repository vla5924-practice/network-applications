package Client;

import Clock.ClockController;
import Clock.Clock;
import Events.Event;
import Events.EventManager;
import Events.EventType;
import Events.EventListener;
import Events.JSON;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientController implements EventListener {
    protected EventManager eventManager = new EventManager();

    int port = 5924;
    InetAddress host = null;

    Socket socket;

    InputStream istream;
    OutputStream ostream;
    DataInputStream distream;
    DataOutputStream dostream;

    Thread thread;

    Clock clock;
    ClockController clockController;

    public ClientController() {
    }

    public void closeClockController() {
        if (clockController != null && clockController.isRunning())
            clockController.stop();
    }

    public synchronized void onAlarmAdded(Event event) {
        eventManager.broadcast(event);
    }

    public void onAlarmWentOff(Event event) {
        eventManager.broadcast(event);
    }

    public void onServiceMessage(Event event) {
        eventManager.broadcast(event);
    }

    public void onClockSync(Event event) {
        closeClockController();
        clock = event.clock;
        clock.addSubscriber(this);
        if (event.running) {
            clockController = new ClockController(clock);
            clockController.start();
        }
        eventManager.broadcast(event);
    }

    public void send(Event event) {
        if (thread == null)
            return;
        String data = JSON.get().toJson(event);
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
            socket = new Socket(host, port);
            System.out.println("Client started");

            ostream = socket.getOutputStream();
            dostream = new DataOutputStream(ostream);

            thread = new Thread(this::run);
            thread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        closeClockController();
        try {
            socket.close();
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
        if (event.type == EventType.CLIENT_DISCONNECT_REQUEST) {
            send(event);
            disconnect();
            return;
        }
        if (event.type == EventType.ALARM_ADD_REQUEST) {
            send(event);
            return;
        }
        if (event.type == EventType.CLOCK_UPDATED) {
            eventManager.broadcast(event);
            return;
        }
        System.out.println("[Client controller signal] Unsupported event: " + event.type);
    }

    public void addSubscriber(EventListener subscriber) {
        eventManager.addSubscriber(subscriber);
    }

    private void run() {
        try {
            istream = socket.getInputStream();
            distream = new DataInputStream(istream);
            while (true) {
                String data = distream.readUTF();
                Event event = JSON.get().fromJson(data, Event.class);
                if (event.type == EventType.ALARM_ADDED) {
                    onAlarmAdded(event);
                } else if (event.type == EventType.CLOCK_SYNC) {
                    onClockSync(event);
                } else if (event.type == EventType.ALARM_WENT_OFF) {
                    onAlarmWentOff(event);
                } else if (event.type == EventType.SERVICE_MESSAGE) {
                    onServiceMessage(event);
                } else {
                    System.out.println("[Client controller run] Unsupported event: " + event.type);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
