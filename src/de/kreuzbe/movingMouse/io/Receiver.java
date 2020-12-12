package de.kreuzbe.movingMouse.io;

import de.kreuzbe.movingMouse.net.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.function.Consumer;

public class Receiver {
    private JFrame f;
    private Client client;
    private Robot robot;

    public Receiver(Client client) {
        f = new JFrame();
        initFrame();


        this.client = client;
        client.setInputConsumer((input) -> {
            if (input.isBlank())
                return;

            if (input.startsWith((AWTEvent.RESERVED_ID_MAX + 1) + " ")) {
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(input.split(" ")[1]), null);
                return;
            }

            String[] args = input.split(" ");
            if (Integer.parseInt(args[0]) == MouseEvent.MOUSE_MOVED || Integer.parseInt(args[0]) == MouseEvent.MOUSE_DRAGGED) {
                robot.mouseMove(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
            } else if (Integer.parseInt(args[0]) == MouseEvent.MOUSE_PRESSED) {
                robot.mousePress(MouseEvent.getMaskForButton(Integer.parseInt(args[3])));
            } else if (Integer.parseInt(args[0]) == MouseEvent.MOUSE_RELEASED) {
                robot.mouseRelease(MouseEvent.getMaskForButton(Integer.parseInt(args[3])));
            } else if (Integer.parseInt(args[0]) == MouseEvent.MOUSE_WHEEL) {
                robot.mouseWheel(Integer.parseInt(args[1]));
            } else if (Integer.parseInt(args[0]) == KeyEvent.KEY_PRESSED) {
                robot.keyPress(Integer.parseInt(args[1]));
            } else if (Integer.parseInt(args[0]) == KeyEvent.KEY_RELEASED) {
                robot.keyRelease(Integer.parseInt(args[1]));
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
