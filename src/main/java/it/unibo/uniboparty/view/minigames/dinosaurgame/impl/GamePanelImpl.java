package it.unibo.uniboparty.view.minigames.dinosaurgame.impl;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

import it.unibo.uniboparty.model.minigames.dinosaurgame.impl.GameConfig;
import it.unibo.uniboparty.model.minigames.dinosaurgame.impl.ModelImpl;
import it.unibo.uniboparty.model.minigames.dinosaurgame.impl.ObstacleImpl;

/**
 * Panel responsible for drawing the dinosaur game.
 */
public class GamePanelImpl extends JPanel {

    private static final long serialVersionUID = 1L;
    private static final int PANEL_WIDTH = GameConfig.PANEL_WIDTH;
    private static final int PANEL_HEIGHT = GameConfig.PANEL_HEIGHT;
    private static final int GROUND_HEIGHT = 50;

    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private static final Color GROUND_COLOR = Color.GRAY;
    private static final Color DINO_COLOR = Color.BLACK;
    private static final Color OBSTACLE_COLOR = Color.GREEN;

    private final transient ModelImpl model;

    /**
     * Creates a game panel bound to a model.
     * Non chiama metodi overridable nel costruttore per evitare warning.
     *
     * @param model the game model
     */
    public GamePanelImpl(final ModelImpl model) {
        this.model = model;
    }

    /**
     * Draws the entire game scene (ground, dinosaur, obstacles).
     */
    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);

        // Disegna il terreno
        g.setColor(GROUND_COLOR);
        g.fillRect(0, PANEL_HEIGHT - GROUND_HEIGHT, PANEL_WIDTH, GROUND_HEIGHT);

        // Disegna il dinosauro
        g.setColor(DINO_COLOR);
        g.fillRect(
            model.getDinoX(),
            model.getDinoY() - model.getDinoHeight(),
            model.getDinoWidth(),
            model.getDinoHeight()
        );

        // Disegna gli ostacoli
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
     * This avoids calling overridable methods in the constructor.
     */
    public void initPanel() {
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setBackground(BACKGROUND_COLOR);
        setFocusable(true);
    }
}
