package de.kreuzbe.movingMouse.io;

import de.kreuzbe.movingMouse.net.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.IOException;

public class Receiver {
    private JFrame f;
    private Client client;
    private Robot robot;

    public Receiver(Client client) {
        f = new JFrame();
        initFrame();

        this.client = client;
        client.setInputConsumer((input) -> {
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
                    robot.mousePress(InputEvent.getMaskForButton(me.getButton()));
                else if (me.getID() == MouseEvent.MOUSE_RELEASED)
                    robot.mouseRelease(InputEvent.getMaskForButton(me.getButton()));
                else if (me.getID() == MouseEvent.MOUSE_WHEEL)
                    robot.mouseWheel(((MouseWheelEvent) me).getWheelRotation());
            }
        });

        try {
            robot = new Robot();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initFrame() {
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setBounds(0, 0, 10, 10);
        f.setResizable(true);
        f.setAlwaysOnTop(true);
        f.setUndecorated(true);
        f.setOpacity(0.2f);
        f.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        f.setVisible(true);
    }
}
