package de.kreuzbe.movingMouse;

import de.kreuzbe.movingMouse.io.IoManager;
import de.kreuzbe.movingMouse.io.Receiver;
import de.kreuzbe.movingMouse.net.Client;

public class Main {
    // Hello
    public static void main(String[] args) {
//        Server s = new Server(4444);
//        Sender l = new Sender(s);
        Client cl = new Client("192.168.2.121", 4444);
        Receiver r = new Receiver(cl);
    }
}