package de.kreuzbe.movingMouse.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.function.Consumer;

public class Client {

    private Thread listeningThread;
    private String host;
    private int port;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    private Consumer<Object> inputConsumer;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
        try {
            socket = new Socket(host, port);

            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            listeningThread = new Thread(this::listen, "Listening Thread");
            listeningThread.setDaemon(true);
            listeningThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setInputConsumer(Consumer<Object> inputConsumer) {
        this.inputConsumer = inputConsumer;
    }

    private void listen() {
        boolean isRunning = true;
        while (true) {
            if (!socket.isConnected())
                break;
            try {
                if (inputConsumer != null)
                    inputConsumer.accept(in.readObject());
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                System.exit(0);
                break;
            }
        }
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(Object o) throws IOException {
        out.writeObject(o);
    }
}
