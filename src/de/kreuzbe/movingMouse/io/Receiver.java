package de.kreuzbe.movingMouse.io;

import de.kreuzbe.movingMouse.net.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.IOException;

public class Receiver extends IoManager {
    private static Toolkit tk = Toolkit.getDefaultToolkit();
    private final Client client;
    private boolean hasFocus = false;

    public Receiver(Client client) {
        super();
        this.client = client;
        client.setInputConsumer(this::processEvent);
        getFrame().setBounds(0, 0, 10, 10);
    }


    @Override
    public void eventDispatched(AWTEvent event) {
        if (event instanceof MouseEvent) {
            MouseEvent me = (MouseEvent) event;
            if (me.getXOnScreen() < 10 && !hasFocus) {
                hasFocus = false;
                getFrame().setExtendedState(Frame.MAXIMIZED_BOTH);
                getRobot().mouseMove((int) tk.getScreenSize().getWidth(), me.getYOnScreen());
            } else if (me.getXOnScreen() > tk.getScreenSize().getWidth() - 10 && hasFocus) {
                hasFocus = true;
                getFrame().setBounds(0, 0, 10, (int) tk.getScreenSize().getHeight());
                getRobot().mouseMove(10, me.getYOnScreen());
            }
        }
        super.eventDispatched(event);
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
