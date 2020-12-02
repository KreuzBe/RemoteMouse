package de.kreuzbe.movingMouse.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
// I am server
// Change
// HAllul
public class Server {
    private Thread listeningThread;

    private int port;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public Server(int port) {
        this.port = port;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Waiting for connection on " + InetAddress.getLocalHost().getHostAddress() + ":" + port);
            clientSocket = serverSocket.accept();
            System.out.println("Connected!");

            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));


            listeningThread = new Thread(this::listen, "Listening Thread");
            listeningThread.setDaemon(true);
            listeningThread.start();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PrintWriter getPrintWriter() {
        return out;
    }


    private void listen() { // TODO LISTEN AS LONG AS YOU CAN
        boolean isRunning = true;
        while (isRunning) {
            if (!clientSocket.isConnected())
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
