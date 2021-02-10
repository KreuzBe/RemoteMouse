package de.kreuzbe.movingMouse.io;

import de.kreuzbe.movingMouse.net.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.IOException;

public class Sender extends IoManager {
    private static Toolkit tk = Toolkit.getDefaultToolkit();

    private Server server;
    private boolean hasFocus = true;

    public Sender(Server server) {
        super();
        this.server = server;
        server.setInputConsumer(this::processEvent);
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
