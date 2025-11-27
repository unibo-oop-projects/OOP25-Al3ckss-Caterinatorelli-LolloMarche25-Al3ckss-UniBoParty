package it.unibo.uniboparty.view.minigames.hangman.impl;

import javax.swing.JPanel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.Serial;

/**
 * Pannello personalizzato per disegnare l'omino dell'impiccato.
 */
public final class HangmanDrawPanel extends JPanel {

    @Serial
    private static final long serialVersionUID = 1L;

    // --- COSTANTI PER IL DISEGNO (Per risolvere Magic Numbers) ---
    // Coordinate base e struttura
    private static final int BASE_Y = 250;
    private static final int BASE_X_START = 20;
    private static final int BASE_X_END = 150;
    private static final int POLE_X = 85;
    private static final int ROPE_TOP_Y = 50;
    private static final int ROPE_X = 200;
    private static final int ROPE_BOTTOM_Y = 80;

    // Coordinate corpo umano
    private static final int HEAD_Y = 80;
    private static final int HEAD_SIZE = 40;
    private static final int BODY_TOP_Y = 120;
    private static final int BODY_BOTTOM_Y = 200;

    // Braccia
    private static final int ARM_START_Y = 140;
    private static final int HAND_Y = 180;
    private static final int LEFT_HAND_X = 170;
    private static final int RIGHT_HAND_X = 230;

    // Gambe
    private static final int LEG_BOTTOM_Y = 240;
    private static final int LEFT_FOOT_X = 170;
    private static final int RIGHT_FOOT_X = 230;

    // Spessore linea
    private static final int STROKE_WIDTH = 3;
    private static final int ERR_LEG_SX = 5;
    private static final int ERR_LEG_DX = 6;

    // Variabili di stato
    private int errors;

    /**
     * Imposta il numero di errori correnti per aggiornare il disegno.
     *
     * @param errors Il numero di errori (0-6).
     */
    public void setErrors(final int errors) {
        this.errors = errors;
        repaint();
    }

    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        final Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(STROKE_WIDTH));
        g2.setColor(Color.BLACK);

        // DISEGNO STRUTTURA FISSA (Sempre visibile)
        // Base terra
        g2.drawLine(BASE_X_START, BASE_Y, BASE_X_END, BASE_Y);
        // Palo verticale
        g2.drawLine(POLE_X, BASE_Y, POLE_X, ROPE_TOP_Y);
        // Trave orizzontale
        g2.drawLine(POLE_X, ROPE_TOP_Y, ROPE_X, ROPE_TOP_Y);
        // Corda piccola
        g2.drawLine(ROPE_X, ROPE_TOP_Y, ROPE_X, ROPE_BOTTOM_Y);

        // DISEGNO OMINO (Progressivo)
        // 1. Testa
        if (errors >= 1) {
            // Centriamo la testa rispetto alla corda (ROPE_X - metà raggio)
            g2.drawOval(ROPE_X - (HEAD_SIZE / 2), HEAD_Y, HEAD_SIZE, HEAD_SIZE);
        }
        // 2. Corpo
        if (errors >= 2) {
            g2.drawLine(ROPE_X, BODY_TOP_Y, ROPE_X, BODY_BOTTOM_Y);
        }
        // 3. Braccio Sinistro
        if (errors >= 3) {
            g2.drawLine(ROPE_X, ARM_START_Y, LEFT_HAND_X, HAND_Y);
        }
        // 4. Braccio Destro
        if (errors >= 4) {
            g2.drawLine(ROPE_X, ARM_START_Y, RIGHT_HAND_X, HAND_Y);
        }
        // 5. Gamba Sinistra
        if (errors >= ERR_LEG_SX) {
            g2.drawLine(ROPE_X, BODY_BOTTOM_Y, LEFT_FOOT_X, LEG_BOTTOM_Y);
        }
        // 6. Gamba Destra (Game Over)
        if (errors >= ERR_LEG_DX) {
            g2.drawLine(ROPE_X, BODY_BOTTOM_Y, RIGHT_FOOT_X, LEG_BOTTOM_Y);
        }
    }
}
