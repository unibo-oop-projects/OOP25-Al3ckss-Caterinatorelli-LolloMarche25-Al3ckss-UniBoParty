package it.unibo.UniBoParty.view.minigames.dinosaurGame.impl;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

import it.unibo.UniBoParty.model.minigames.dinosaurGame.api.Obstacle;
import it.unibo.UniBoParty.model.minigames.dinosaurGame.api.Model;
import it.unibo.UniBoParty.view.minigames.dinosaurGame.api.GamePanel;

/**
 * Pannello principale del gioco.
 * Disegna dinosauro e ostacoli.
 */
public class GamePanelImpl extends JPanel implements GamePanel{

    private static final int PANEL_WIDTH = 600;
    private static final int PANEL_HEIGHT = 400;
    private static final int GROUND_HEIGHT = 50;
    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private static final Color GROUND_COLOR = Color.GRAY;
    private static final Color DINO_COLOR = Color.BLACK;
    private static final Color OBSTACLE_COLOR = Color.GREEN;

    private final Model model;

    public GamePanelImpl(Model model) {
        this.model = model;
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setBackground(BACKGROUND_COLOR);
        setFocusable(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Disegna il terreno
        g.setColor(GROUND_COLOR);
        g.fillRect(0, PANEL_HEIGHT - GROUND_HEIGHT, PANEL_WIDTH, GROUND_HEIGHT);

        // Disegna il dinosauro
        g.setColor(DINO_COLOR);
        g.fillRect(model.getDinoX(),
                   model.getDinoY() - model.getDinoHeight(),
                   model.getDinoWidth(),
                   model.getDinoHeight());

        // Disegna gli ostacoli
        g.setColor(OBSTACLE_COLOR);
        for (Obstacle o : model.getObstacles()) {
            g.fillRect(o.getObstX(),
                       o.getObstY() - o.getObstHeight(),
                       o.getObstWidth(),
                       o.getObstHeight());
        }
    }
}
