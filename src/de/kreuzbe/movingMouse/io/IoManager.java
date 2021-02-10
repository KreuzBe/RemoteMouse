package de.kreuzbe.movingMouse.io;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.*;
import java.io.IOException;
import java.io.Serializable;

public abstract class IoManager implements AWTEventListener {
    private static final Toolkit tk = Toolkit.getDefaultToolkit();
    private JFrame frame;
    private Robot robot;

    public IoManager() {
        frame = new JFrame();
        try {
            robot = new Robot();
        } catch (Exception e) {
            e.printStackTrace();
        }

        tk.addAWTEventListener(this, AWTEvent.MOUSE_EVENT_MASK | AWTEvent.MOUSE_MOTION_EVENT_MASK | AWTEvent.MOUSE_WHEEL_EVENT_MASK | AWTEvent.KEY_EVENT_MASK);
        frame = new JFrame();

        initFrame();
    }

    public void sendClipboard() {
        DataFlavor[] t = tk.getSystemClipboard().getAvailableDataFlavors();
        ClipboardContainer cc = new ClipboardContainer();
        for (DataFlavor f : t) {
            try {
                if (Serializable.class.isAssignableFrom(tk.getSystemClipboard().getData(f).getClass())) {
                    cc.put(f, (Serializable) tk.getSystemClipboard().getData(f));
                }
            } catch (UnsupportedFlavorException | IOException e) {
                //  e.printStackTrace();
            }
        }
        send(cc);
    }

    public void processEvent(Object input) {
        if (input instanceof KeyEvent) {
            KeyEvent ke = (KeyEvent) input;
            if (ke.getID() == KeyEvent.KEY_PRESSED)
                robot.keyPress(ke.getKeyCode());
            else if (ke.getID() == KeyEvent.KEY_RELEASED)
                robot.keyRelease(ke.getKeyCode());
        } else if (input instanceof MouseEvent) {
            MouseEvent me = (MouseEvent) input;
            if (me.getID() == MouseEvent.MOUSE_MOVED || me.getID() == MouseEvent.MOUSE_DRAGGED)
                robot.mouseMove(me.getXOnScreen(), me.getYOnScreen());
            else if (me.getID() == MouseEvent.MOUSE_PRESSED)
                robot.mousePress(InputEvent.getMaskForButton(me.getButton()));
            else if (me.getID() == MouseEvent.MOUSE_RELEASED)
                robot.mouseRelease(MouseEvent.getMaskForButton(me.getButton()));
            else if (me.getID() == MouseEvent.MOUSE_WHEEL)
                robot.mouseWheel(((MouseWheelEvent) me).getWheelRotation());
        }
    }

    @Override
    public void eventDispatched(AWTEvent event) {
        // sendClipboard();
        send(event);
    }

    public JFrame getFrame() {
        return frame;
    }

    public Robot getRobot() {
        return robot;
    }

    private void initFrame() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(0, 0, 10, (int) tk.getScreenSize().getHeight());
        frame.setResizable(false);
        frame.setAlwaysOnTop(true);
        frame.setUndecorated(true);
        frame.setOpacity(0.2f);
        frame.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        frame.setVisible(true);
    }

    public abstract void send(Object o);


}
