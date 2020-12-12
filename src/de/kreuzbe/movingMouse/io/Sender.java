package de.kreuzbe.movingMouse.io;

import de.kreuzbe.movingMouse.net.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseEvent;

public class Sender implements AWTEventListener {
    private static Toolkit tk = Toolkit.getDefaultToolkit();

    private Server server;

    private Robot robot;

    private JFrame f;
    private boolean hasFocus = true;

    public Sender(Server server) {
        this.server = server;

        tk.addAWTEventListener(this, AWTEvent.MOUSE_EVENT_MASK | AWTEvent.MOUSE_MOTION_EVENT_MASK);
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
                hasFocus = false;
                robot.mouseMove((int) (tk.getScreenSize().getWidth() - 1), me.getYOnScreen());
            } else if (!hasFocus && me.getXOnScreen() >= tk.getScreenSize().width - 1) { // TODO WHEN MOUSE IS AT CLIENTS RIGHT SCREEN SIDE
                f.setBounds(0, 0, 1, (int) tk.getScreenSize().getHeight());
                robot.mouseMove(1, me.getYOnScreen());
                hasFocus = true;
            }
        }
        if (event.getID() == MouseEvent.MOUSE_MOVED) {
            MouseEvent me = (MouseEvent) event;
            server.getPrintWriter().println(event.getID() + " " + me.getXOnScreen() + " " + me.getYOnScreen());
        }
        System.out.println(event.paramString());
    }
}
