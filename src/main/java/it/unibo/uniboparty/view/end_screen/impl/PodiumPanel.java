package it.unibo.uniboparty.view.end_screen.impl;

import it.unibo.uniboparty.model.end_screen.api.Player;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.Serial;
import java.net.URL;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;

/**
 * Custom {@link JPanel} responsible for rendering the graphical podium.
 *
 * <p>
 * This panel draws a background image, a congratulatory title, and graphical bars
 * representing the top players (1st, 2nd, and 3rd place). It uses Java 2D Graphics
 * for custom rendering and precise positioning.
 */
public class PodiumPanel extends JPanel {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = Logger.getLogger(PodiumPanel.class.getName());

    private static final String BG_IMAGE_PATH = "sudoku_icons/background.jpeg";

    private static final int BASE_Y_OFFSET = 100;
    private static final int BAR_WIDTH = 100;
    private static final int FIRST_HEIGHT = 160;
    private static final int SECOND_HEIGHT = 110;
    private static final int THIRD_HEIGHT = 80;
    private static final int GAP = 20;
    private static final int PLAYER_TEXT_OFFSET = 25;
    private static final Font PLAYER_FONT = new Font("Arial", Font.BOLD, 16);

    private static final String TITLE_TEXT = "Congratulations!";
    private static final int TITLE_Y_POS = 60;
    private static final Font TITLE_FONT = new Font("SansSerif", Font.BOLD | Font.ITALIC, 42);
    private static final Color TITLE_COLOR = new Color(50, 50, 50);

    private final List<Player> topPlayers;
    private final transient Image backgroundImage;

    /**
     * Constructs the podium panel.
     *
     * <p>
     * It creates a defensive copy of the player list and attempts to load the
     * background image from the resources. If the image is not found, a warning
     * is logged and the background will be rendered as a solid color.
     *
     * @param topPlayers the list of {@link Player} objects representing the winners.
     */
    public PodiumPanel(final List<Player> topPlayers) {

        this.topPlayers = new ArrayList<>(topPlayers);

        final URL imgUrl = getClass().getClassLoader().getResource(BG_IMAGE_PATH);
        if (imgUrl != null) {
            this.backgroundImage = new ImageIcon(imgUrl).getImage();
        } else {
            this.backgroundImage = null;
            LOGGER.log(Level.WARNING, "Warning: Background image ''" + BG_IMAGE_PATH + "'' not found.");
        }
    }

    /**
     * Paints the component graphics.
     *
     * <p>
     * This method overrides the standard Swing painting. It enables anti-aliasing
     * for smoother text and shapes, then draws the background, the title, and the
     * podium bars in sequence.
     *
     * @param g the {@code Graphics} object to protect.
     */
    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        final Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        drawBackground(g2);

        drawTitle(g2);

        if (topPlayers.isEmpty()) {
            return;
        }
        drawPodiumBars(g2);
    }

    /**
     * Draws the background image or a fallback color.
     *
     * @param g2 the graphics context.
     */
    private void drawBackground(final Graphics2D g2) {
        if (backgroundImage != null) {

            g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        } else {

            g2.setColor(Color.WHITE);
            g2.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    /**
     * Renders the "Congratulations!" title centered at the top.
     *
     * @param g2 the graphics context.
     */
    private void drawTitle(final Graphics2D g2) {
        g2.setFont(TITLE_FONT);
        g2.setColor(TITLE_COLOR);

        final FontMetrics metrics = g2.getFontMetrics(TITLE_FONT);
        final int textWidth = metrics.stringWidth(TITLE_TEXT);
        final int x = (getWidth() - textWidth) / 2;

        g2.drawString(TITLE_TEXT, x, TITLE_Y_POS);
    }

    /**
     * Calculates the position of the podium bars and triggers their drawing.
     *
     * <p>
     * The first place is centered, the second place is to the left, and the third
     * place is to the right.
     *
     * @param g2 the graphics context.
     */
    private void drawPodiumBars(final Graphics2D g2) {
        final int baseY = getHeight() - BASE_Y_OFFSET;
        final int firstX = (getWidth() / 2) - (BAR_WIDTH / 2);
        final int secondX = firstX - BAR_WIDTH - GAP;
        final int thirdX = firstX + BAR_WIDTH + GAP;

        g2.setFont(PLAYER_FONT);

        if (!topPlayers.isEmpty()) {
            drawSingleBar(g2, firstX, baseY, FIRST_HEIGHT, Color.YELLOW, "1. " + topPlayers.get(0).getName());
        }
        if (topPlayers.size() >= 2) {
            drawSingleBar(g2, secondX, baseY, SECOND_HEIGHT, Color.LIGHT_GRAY, "2. " + topPlayers.get(1).getName());
        }
        if (topPlayers.size() >= 3) {
            drawSingleBar(g2, thirdX, baseY, THIRD_HEIGHT, Color.ORANGE, "3. " + topPlayers.get(2).getName());
        }
    }

    /**
     * Draws a single podium bar with the player's name above it.
     *
     * @param g       the graphics context.
     * @param x       the x-coordinate for the bar.
     * @param baseY   the base y-coordinate (bottom of the bar).
     * @param height  the height of the bar (depends on rank).
     * @param color   the color of the bar (Gold, Silver, or Bronze).
     * @param text    the text to display (Rank + Name).
     */
    private void drawSingleBar(final Graphics2D g, final int x, final int baseY,
                               final int height, final Color color, final String text) {
        final int yPos = baseY - height;

        g.setColor(color);
        g.fillRect(x, yPos, BAR_WIDTH, height);

        g.setColor(Color.BLACK);
        g.drawRect(x, yPos, BAR_WIDTH, height);

        final FontMetrics metrics = g.getFontMetrics(PLAYER_FONT);
        final int textWidth = metrics.stringWidth(text);
        final int centeredTextX = x + (BAR_WIDTH - textWidth) / 2;

        g.drawString(text, centeredTextX, yPos - PLAYER_TEXT_OFFSET);
    }
}
