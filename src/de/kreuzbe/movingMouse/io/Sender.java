package de.kreuzbe.movingMouse.io;

import de.kreuzbe.movingMouse.net.Server;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class Sender extends IoManager {
    private final Toolkit tk = Toolkit.getDefaultToolkit();
    private final Server server;
    private boolean hasFocus = true;


    public Sender(Server server) {
        super();
        this.server = server;
        server.setInputConsumer(this::processEvent);
    }


    @Override
    public void eventDispatched(AWTEvent event) {
        if (event instanceof MouseEvent) {
            MouseEvent me = (MouseEvent) event;
            if (me.getXOnScreen() < 10 && hasFocus) {
                hasFocus = false;
                getFrame().setExtendedState(Frame.MAXIMIZED_BOTH);
                getRobot().mouseMove((int) tk.getScreenSize().getWidth(), me.getYOnScreen());
            } else if (me.getXOnScreen() > tk.getScreenSize().getWidth() - 10 && !hasFocus) {
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
            server.send(o);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
