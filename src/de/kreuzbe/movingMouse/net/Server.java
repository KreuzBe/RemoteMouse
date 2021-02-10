package de.kreuzbe.movingMouse.net;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class Server {
    private Thread listeningThread;

    private int port;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private ObjectOutput out;
    private ObjectInputStream in;

    private Consumer<Object> inputConsumer;

    public void setInputConsumer(Consumer<Object> inputConsumer) {
        this.inputConsumer = inputConsumer;
    }

    public Server(int port) {
        this.port = port;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Waiting for connection on " + InetAddress.getLocalHost().getHostAddress() + ":" + port);
            clientSocket = serverSocket.accept();
            System.out.println("Connected!");

            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());

            listeningThread = new Thread(this::listen, "Listening Thread");
            listeningThread.setDaemon(true);
            listeningThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void listen() { // TODO LISTEN AS LONG AS YOU CAN
        boolean isRunning = true;
        while (isRunning) {
            if (!clientSocket.isConnected())
                break;

            try {
                if (inputConsumer != null) {
                    BufferedImage img = ImageIO.read(ImageIO.createImageInputStream(clientSocket.getInputStream()));
                    if (img == null)
                        inputConsumer.accept(in.readObject());
                    else
                        inputConsumer.accept(img);
                } else
                    Thread.sleep(10);
            } catch (IOException | ClassNotFoundException | InterruptedException e) {
                e.printStackTrace();
               // break;
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
