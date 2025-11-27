package it.unibo.uniboparty.view.dice.impl;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.Serial;
import javax.swing.JPanel;

/**
 * Custom Panel that draws a die face (1-6) using Java 2D Graphics.
 */
public final class DieGraphicsPanel extends JPanel {

    @Serial
    private static final long serialVersionUID = 1L;

    // --- COSTANTI PER IL DISEGNO ---
    private static final int ARC_SIZE = 20; // Arrotondamento bordi dado
    private static final int DOT_SIZE = 20; // Dimensione pallini
    private static final int PADDING = 20;  // Margine dai bordi
    private static final Color TABLE_COLOR = new Color(30, 100, 30);
    private static final int ANGLE_DOT_DIE = 6;

    private int value;

    /**
     * Constructor. Initializes the die with value 1.
     */
    public DieGraphicsPanel() {
        this.value = 1;
        this.setBackground(TABLE_COLOR); // Sfondo verde tavolo da gioco
    }

    /**
     * Sets the face value of the die and repaints.
     *
     * @param value The new value (1-6).
     */
    public void setValue(final int value) {
        this.value = value;
        repaint(); // Chiama paintComponent
    }

    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        final Graphics2D g2 = (Graphics2D) g;

        // Attiva Antialiasing per cerchi perfetti
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        final int w = getWidth();
        final int h = getHeight();

        // 1. Disegna il corpo del dado (Bianco con bordo nero)
        g2.setColor(Color.WHITE);
        g2.fillRoundRect(PADDING, PADDING, w - (2 * PADDING), h - (2 * PADDING), ARC_SIZE, ARC_SIZE);

        g2.setColor(Color.BLACK);
        g2.drawRoundRect(PADDING, PADDING, w - (2 * PADDING), h - (2 * PADDING), ARC_SIZE, ARC_SIZE);

        g2.setColor(Color.BLACK); // Colore dei pallini

        // Calcolo coordinate relative
        final int contentW = w - (2 * PADDING);
        final int contentH = h - (2 * PADDING);

        final int centerX = PADDING + (contentW / 2) - (DOT_SIZE / 2);
        final int centerY = PADDING + (contentH / 2) - (DOT_SIZE / 2);

        final int leftX = PADDING + (contentW / 4) - (DOT_SIZE / 2);
        final int rightX = PADDING + (3 * contentW / 4) - (DOT_SIZE / 2);

        final int topY = PADDING + (contentH / 4) - (DOT_SIZE / 2);
        final int bottomY = PADDING + (3 * contentH / 4) - (DOT_SIZE / 2);

        // Logica per disegnare i pallini in base al numero
        drawDots(g2, value, centerX, centerY, leftX, rightX, topY, bottomY);
    }

    private void drawDots(final Graphics2D g2, final int val,
                          final int midX, final int midY,
                          final int leftX, final int rightX,
                          final int topY, final int botY) {

        // Il pallino centrale c'è solo per i numeri dispari (1, 3, 5)
        if (val % 2 != 0) {
            g2.fillOval(midX, midY, DOT_SIZE, DOT_SIZE);
        }

        // 2, 3, 4, 5, 6 hanno pallini agli angoli opposti (Alto-Dx, Basso-Sx)
        if (val >= 2) {
            g2.fillOval(rightX, topY, DOT_SIZE, DOT_SIZE);
            g2.fillOval(leftX, botY, DOT_SIZE, DOT_SIZE);
        }

        // 4, 5, 6 hanno anche gli altri due angoli (Alto-Sx, Basso-Dx)
        if (val >= 4) {
            g2.fillOval(leftX, topY, DOT_SIZE, DOT_SIZE);
            g2.fillOval(rightX, botY, DOT_SIZE, DOT_SIZE);
        }

        // Il 6 ha anche i laterali centrali
        if (val == ANGLE_DOT_DIE) {
            g2.fillOval(leftX, midY, DOT_SIZE, DOT_SIZE);
            g2.fillOval(rightX, midY, DOT_SIZE, DOT_SIZE);
        }
    }
}
