package de.kreuzbe.movingMouse.io;

import de.kreuzbe.movingMouse.net.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
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
        if (event.getID() == MouseEvent.MOUSE_MOVED) {
            MouseEvent me = (MouseEvent) event;
            if (hasFocus && me.getXOnScreen() <= 1) {
                f.setExtendedState(JFrame.MAXIMIZED_BOTH);
                try {
                    server.getPrintWriter().println((AWTEvent.RESERVED_ID_MAX + 1) + " " + tk.getSystemClipboard().getData(DataFlavor.stringFlavor));
                } catch (UnsupportedFlavorException | IOException e) {
                    e.printStackTrace();
                }
                hasFocus = false;
                robot.mouseMove((int) (tk.getScreenSize().getWidth() - 1), me.getYOnScreen());
            } else if (!hasFocus && me.getXOnScreen() >= tk.getScreenSize().width - 1) {
                f.setBounds(0, 0, 1, (int) tk.getScreenSize().getHeight());
                robot.mouseMove(1, me.getYOnScreen());
                hasFocus = true;
            }
        }
        System.out.println(event.getClass());
        if (event.getClass() == MouseEvent.class) {
            MouseEvent me = (MouseEvent) event;
            server.getPrintWriter().println(event.getID() + " " + me.getXOnScreen() + " " + me.getYOnScreen() + " " + me.getButton());
            me.consume();
        } else if (event.getClass() == MouseWheelEvent.class) {
            MouseWheelEvent mwe = (MouseWheelEvent) event;
            server.getPrintWriter().println(event.getID() + " " + mwe.getWheelRotation());
            mwe.consume();
        } else if (event.getClass() == KeyEvent.class) {
            KeyEvent ke = (KeyEvent) event;
            server.getPrintWriter().println(event.getID() + " " + ke.getKeyCode());
            ke.consume();
        }
        System.out.println(event.paramString());
    }
}
