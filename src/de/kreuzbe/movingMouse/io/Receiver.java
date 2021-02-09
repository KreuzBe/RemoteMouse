package de.kreuzbe.movingMouse.io;

import de.kreuzbe.movingMouse.net.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.IOException;

public class Receiver extends  IoManager{
    private JFrame f;
    private Client client;
    private Robot robot;

    public Receiver(Client client) {
        this.client = client;
        client.setInputConsumer(this::processEvent);
    }


    @Override
    public void send(Object o) {
        try {
            client.send(o);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
