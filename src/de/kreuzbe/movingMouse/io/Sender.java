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

public class Sender implements AWTEventListener {
    private static Toolkit tk = Toolkit.getDefaultToolkit();

    private Server server;

    private Robot robot;

    private JFrame f;
    private boolean hasFocus = true;

    public Sender(Server server) {
        this.server = server;

        server.setInputConsumer((input) -> {

        });

        tk.addAWTEventListener(this, AWTEvent.MOUSE_EVENT_MASK | AWTEvent.MOUSE_MOTION_EVENT_MASK | AWTEvent.MOUSE_WHEEL_EVENT_MASK | AWTEvent.KEY_EVENT_MASK);
        f = new JFrame();
        try {
            robot = new Robot();
        } catch (Exception e) {
            e.printStackTrace();
        }
        initFrame();
    }

    private void initFrame() {
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setBounds(0, 0, 10, (int) tk.getScreenSize().getHeight());
        f.setResizable(true);
        f.setAlwaysOnTop(true);
        f.setUndecorated(true);
        f.setOpacity(0.2f);
        f.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        f.setVisible(true);
    }

    @Override
    public void eventDispatched(AWTEvent event) {
        if (event.getID() == AWTEvent.KEY_EVENT_MASK) {
            System.out.println(event);
        }
    }
}
