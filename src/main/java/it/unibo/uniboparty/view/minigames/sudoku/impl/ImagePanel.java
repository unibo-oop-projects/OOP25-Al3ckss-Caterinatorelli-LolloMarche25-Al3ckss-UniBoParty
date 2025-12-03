package it.unibo.uniboparty.view.minigames.sudoku.impl;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Image;

/**
 * Custom {@link JPanel} implementation that paints a scaled background image.
 *
 * <p>
 * This utility class overrides the standard painting mechanism to display an image
 * that automatically resizes to fill the entire area of the panel.
 */
public class ImagePanel extends JPanel {

    private static final long serialVersionUID = 1L;

    /**
     * The image to be drawn. Marked transient as Image is not Serializable.
     */
    private final transient Image backgroundImage;

    /**
     * Constructs a new {@code ImagePanel} with the specified background.
     *
     * @param backgroundImage The {@link Image} to use as the background. If {@code null}, no background image.
     */
    public ImagePanel(final Image backgroundImage) {

        if (backgroundImage != null) {
            this.backgroundImage = new ImageIcon(backgroundImage).getImage();
        } else {
            this.backgroundImage = null;
        }
    }

    /**
     * Paints the component graphics.
     *
     * <p>
     * This method draws the background image, scaling it to fit the current
     * width and height of the component.
     *
     * @param g The {@link Graphics} context used for drawing.
     */
    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);

        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
