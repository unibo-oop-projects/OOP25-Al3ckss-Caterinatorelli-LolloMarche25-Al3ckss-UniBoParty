package it.unibo.UniBoParty.view.minigames.sudoku.impl;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Image;

/**
 * Custom JPanel to set an image as background,
 * resizing it to the panel dimension.
 */
public class ImagePanel extends JPanel {

    private final Image backgroundImage;

    /**
     * Creates a panel with an image as background.
     * @param backgroundImage The image used as a background.
     */
    public ImagePanel(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    /**
     * Overrides the standard painting method to draw a scaled background image.
     * <p>
     * It first calls {@code super.paintComponent(g)} to ensure the panel is properly
     * cleared and rendered. Then, if a background image is set, it draws the image
     * stretched to fill the entire width and height of the panel.
     * </p>
     *
     * @param g The {@link java.awt.Graphics} context used for drawing.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}