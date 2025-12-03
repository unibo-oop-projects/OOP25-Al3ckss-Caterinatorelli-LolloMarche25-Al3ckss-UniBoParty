package it.unibo.uniboparty.view.minigames.dinosaurgame.impl;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Objects;
import javax.swing.JPanel;

import it.unibo.uniboparty.model.minigames.dinosaurgame.api.Model;
import it.unibo.uniboparty.model.minigames.dinosaurgame.impl.GameConfig;
import it.unibo.uniboparty.model.minigames.dinosaurgame.impl.ObstacleImpl;

/**
 * Panel responsible for drawing the dinosaur game.
 *
 * <p>
 * Stores a reference to the model for rendering the game state.
 * The model reference is immutable and treated as a safe dependency.
 */
public final class GamePanelImpl extends JPanel {

    private static final long serialVersionUID = 1L;
    private static final int PANEL_WIDTH = GameConfig.PANEL_WIDTH;
    private static final int PANEL_HEIGHT = GameConfig.PANEL_HEIGHT;
    private static final int GROUND_HEIGHT = 50;

    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private static final Color GROUND_COLOR = Color.GRAY;
    private static final Color DINO_COLOR = Color.BLACK;
    private static final Color OBSTACLE_COLOR = Color.GREEN;

    private final transient Model model;

    /**
     * Creates a game panel bound to a model.
     *
     * @param model the game model (must not be null)
     */
    public GamePanelImpl(final Model model) {
        this.model = Objects.requireNonNull(model, "Model cannot be null");
    }

    /**
     * Draws the entire game scene (ground, dinosaur, obstacles).
     */
    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);

        // Ground
        g.setColor(GROUND_COLOR);
        g.fillRect(0, PANEL_HEIGHT - GROUND_HEIGHT, PANEL_WIDTH, GROUND_HEIGHT);

        // Dinosaur
        g.setColor(DINO_COLOR);
        g.fillRect(
            model.getDinoX(),
            model.getDinoY() - model.getDinoHeight(),
            model.getDinoWidth(),
            model.getDinoHeight()
        );

        // Obstacles
        g.setColor(OBSTACLE_COLOR);
        for (final ObstacleImpl o : model.getObstacles()) {
            g.fillRect(
                o.getObstX(),
                o.getObstY() - o.getObstHeight(),
                o.getObstWidth(),
                o.getObstHeight()
            );
        }
    }

    /**
     * Initializes the panel settings safely after construction.
     */
    public void initPanel() {
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setBackground(BACKGROUND_COLOR);
        setFocusable(true);
    }
}
