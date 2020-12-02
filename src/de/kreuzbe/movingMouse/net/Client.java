package de.kreuzbe.movingMouse.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private Thread listeningThread;
    private String host;
    private int port;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
        try {
            socket = new Socket(host, port);

            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));


            listeningThread = new Thread(this::listen, "Listening Thread");
            listeningThread.setDaemon(true);
            listeningThread.start();

            Scanner sc = new Scanner(System.in);
            while (sc.hasNext()) {
                out.println(sc.nextLine());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void listen() {
        boolean isRunning = true;
        while (isRunning) {

            if (!socket.isConnected())
                break;
            try {
                System.out.println(in.readLine());
            } catch (IOException e) {
                e.printStackTrace();
                break;

            }
        }
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
