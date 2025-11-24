package it.unibo.UniBoParty.view.minigames.hangman.impl;

import javax.swing.JPanel;
import java.awt.*;

public class HangmanDrawPanel extends JPanel {
    private int errors = 0;

    public void setErrors(int errors) {
        this.errors = errors;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(3));
        g2.setColor(Color.BLACK);

        // Base e Palo (Sempre visibili o allo step 0)
        g2.drawLine(20, 250, 150, 250);
        g2.drawLine(85, 250, 85, 50);
        g2.drawLine(85, 50, 200, 50);
        g2.drawLine(200, 50, 200, 80);

        if (errors >= 1) g2.drawOval(180, 80, 40, 40);
        if (errors >= 2) g2.drawLine(200, 120, 200, 200);
        if (errors >= 3) g2.drawLine(200, 140, 170, 180);
        if (errors >= 4) g2.drawLine(200, 140, 230, 180);
        if (errors >= 5) g2.drawLine(200, 200, 170, 240);
        if (errors >= 6) g2.drawLine(200, 200, 230, 240);
    }
}