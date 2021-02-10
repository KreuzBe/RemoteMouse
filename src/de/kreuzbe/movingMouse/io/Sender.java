package de.kreuzbe.movingMouse.io;

import de.kreuzbe.movingMouse.net.Server;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class Sender extends IoManager {
    private final Server server;

    public Sender(Server server) {
        super();
        this.server = server;
        server.setInputConsumer(this::processEvent);
    }


    @Override
    public void send(Object o) {
        System.out.println("Send");
        try {
            server.send(o);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
