package de.kreuzbe.movingMouse;

import de.kreuzbe.movingMouse.net.Client;

public class Main {

    public static void main(String[] args) {
//        Server s = new Server(4444);
//        Listener l = new Listener(s);
        Client cl = new Client("192.168.2.121", 4444);
    }

}
