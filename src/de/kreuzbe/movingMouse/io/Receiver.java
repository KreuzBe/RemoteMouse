package de.kreuzbe.movingMouse.io;

import de.kreuzbe.movingMouse.net.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
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
            System.out.println(input);
            if (input.isBlank())
                return;
            String[] args = input.split(" ");
            if (!args[0].isBlank() && Integer.parseInt(args[0]) == MouseEvent.MOUSE_MOVED) {
                robot.mouseMove(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
                System.err.println(".");
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
        f.setBounds(0, 0, 10, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight());
        f.setResizable(true);
        f.setAlwaysOnTop(true);
        f.setUndecorated(true);
        f.setOpacity(0.2f);
        f.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        f.setVisible(true);
    }


}
