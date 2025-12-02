package it.unibo.uniboparty.view.dice.impl;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.Serial;
import javax.swing.JPanel;

/**
 * Custom Swing {@link JPanel} responsible for rendering a graphical representation of a 6-sided die.
 *
 * <p>
 * This class uses Java 2D Graphics APIs to draw the die face dynamically based on the current value.
 * It handles drawing the rounded white body of the die and the correct arrangement of black dots (pips).
 */
public final class DieGraphicsPanel extends JPanel {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final int ARC_SIZE = 20;
    private static final int DOT_SIZE = 20;
    private static final int PADDING = 20;
    private static final Color TABLE_COLOR = new Color(30, 100, 30);

    /**
     * Represents the value '6', which requires specific drawing logic for the side dots.
     */
    private static final int ANGLE_DOT_DIE = 6;

    private int value;

    /**
     * Constructs the panel and initializes the die state.
     *
     * <p>
     * The die starts with a default value of 1. The background is set to match
     * the game table color to ensure visual consistency.
     */
    public DieGraphicsPanel() {
        this.value = 1;
        this.setBackground(TABLE_COLOR);
    }

    /**
     * Updates the face value of the die and triggers a repaint.
     *
     * <p>
     * Calling this method forces the component to redraw itself immediately
     * to reflect the new number.
     *
     * @param value The new value to display (must be between 1 and 6).
     */
    public void setValue(final int value) {
        this.value = value;
        repaint();
    }

    /**
     * Paints the component graphics.
     *
     * <p>
     * This method overrides the standard Swing painting mechanism. It performs the following steps:
     * <ol>
     * <li>Enables Anti-Aliasing for smoother edges.</li>
     * <li>Draws the white rounded rectangle (the body of the die).</li>
     * <li>Calculates the precise coordinates for potential dot positions relative to the panel size.</li>
     * <li>Calls {@link #drawDots} to render the specific pattern for the current value.</li>
     * </ol>
     *
     * @param g the {@code Graphics} object to protect.
     */
    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        final Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        final int w = getWidth();
        final int h = getHeight();

        g2.setColor(Color.WHITE);
        g2.fillRoundRect(PADDING, PADDING, w - (2 * PADDING), h - (2 * PADDING), ARC_SIZE, ARC_SIZE);

        g2.setColor(Color.BLACK);
        g2.drawRoundRect(PADDING, PADDING, w - (2 * PADDING), h - (2 * PADDING), ARC_SIZE, ARC_SIZE);

        g2.setColor(Color.BLACK);

        final int contentW = w - (2 * PADDING);
        final int contentH = h - (2 * PADDING);

        final int centerX = PADDING + (contentW / 2) - (DOT_SIZE / 2);
        final int centerY = PADDING + (contentH / 2) - (DOT_SIZE / 2);

        final int leftX = PADDING + (contentW / 4) - (DOT_SIZE / 2);
        final int rightX = PADDING + (3 * contentW / 4) - (DOT_SIZE / 2);

        final int topY = PADDING + (contentH / 4) - (DOT_SIZE / 2);
        final int bottomY = PADDING + (3 * contentH / 4) - (DOT_SIZE / 2);

        drawDots(g2, value, centerX, centerY, leftX, rightX, topY, bottomY);
    }

    /**
     * Helper method to determine which dots to draw based on the die value.
     *
     * <p>
     * It uses cumulative logic standard to dice faces:
     * <ul>
     * <li>Odd numbers always have a center dot.</li>
     * <li>Numbers >= 2 have top-right and bottom-left dots.</li>
     * <li>Numbers >= 4 add top-left and bottom-right dots.</li>
     * <li>Number 6 adds the middle-left and middle-right dots.</li>
     * </ul>
     *
     * @param g2      The graphics context.
     * @param val     The current value of the die.
     * @param midX    X-coordinate for the center column.
     * @param midY    Y-coordinate for the center row.
     * @param leftX   X-coordinate for the left column.
     * @param rightX  X-coordinate for the right column.
     * @param topY    Y-coordinate for the top row.
     * @param botY    Y-coordinate for the bottom row.
     */
    private void drawDots(final Graphics2D g2, final int val,
                          final int midX, final int midY,
                          final int leftX, final int rightX,
                          final int topY, final int botY) {

        if (val % 2 != 0) {
            g2.fillOval(midX, midY, DOT_SIZE, DOT_SIZE);
        }

        if (val >= 2) {
            g2.fillOval(rightX, topY, DOT_SIZE, DOT_SIZE);
            g2.fillOval(leftX, botY, DOT_SIZE, DOT_SIZE);
        }

        if (val >= 4) {
            g2.fillOval(leftX, topY, DOT_SIZE, DOT_SIZE);
            g2.fillOval(rightX, botY, DOT_SIZE, DOT_SIZE);
        }

        if (val == ANGLE_DOT_DIE) {
            g2.fillOval(leftX, midY, DOT_SIZE, DOT_SIZE);
            g2.fillOval(rightX, midY, DOT_SIZE, DOT_SIZE);
        }
    }
}
