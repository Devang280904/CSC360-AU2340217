package org.example;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class App extends JPanel {
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        g2d.setColor(Color.RED);
        g2d.drawRect(200, 100, 150, 150);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Draw Square in Java");
        App panel = new App();
        frame.add(panel);
        frame.setSize(550, 400);
        frame.setDefaultCloseOperation(3);
        frame.setLocationRelativeTo((Component)null);
        frame.setVisible(true);
    }
}