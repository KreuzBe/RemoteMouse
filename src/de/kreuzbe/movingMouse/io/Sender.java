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
        try {
            robot = new Robot();
        } catch (Exception e) {
            e.printStackTrace();
        }
        server.setInputConsumer((input) -> {
            if (input instanceof KeyEvent) {
                KeyEvent ke = (KeyEvent) input;
                if (ke.getID() == KeyEvent.KEY_PRESSED)
                    robot.keyPress(ke.getKeyCode());
                else if (ke.getID() == KeyEvent.KEY_PRESSED)
                    robot.keyRelease(ke.getKeyCode());
            } else if (input instanceof MouseEvent) {
                MouseEvent me = (MouseEvent) input;
                if (me.getID() == MouseEvent.MOUSE_MOVED || me.getID() == MouseEvent.MOUSE_DRAGGED)
                    robot.mouseMove(me.getXOnScreen(), me.getYOnScreen());
                else if (me.getID() == MouseEvent.MOUSE_PRESSED)
                    robot.mousePress(me.getButton());
                else if (me.getID() == MouseEvent.MOUSE_RELEASED)
                    robot.mouseRelease(me.getButton());
                else if (me.getID() == MouseEvent.MOUSE_WHEEL)
                    robot.mouseWheel(((MouseWheelEvent) me).getWheelRotation());
            }
        });

        tk.addAWTEventListener(this, AWTEvent.MOUSE_EVENT_MASK | AWTEvent.MOUSE_MOTION_EVENT_MASK | AWTEvent.MOUSE_WHEEL_EVENT_MASK | AWTEvent.KEY_EVENT_MASK);
        f = new JFrame();

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
        if (event instanceof MouseEvent) {
            System.out.println(event);
        }
        try {
            server.send(event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
