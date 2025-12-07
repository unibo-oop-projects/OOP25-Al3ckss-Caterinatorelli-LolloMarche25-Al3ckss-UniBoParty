package it.unibo.uniboparty.view.minigames.dinosaurgame.impl;

import java.awt.Dimension;
import java.awt.event.KeyListener;
import java.util.Objects;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import it.unibo.uniboparty.model.minigames.dinosaurgame.impl.GameConfig;
import it.unibo.uniboparty.model.minigames.dinosaurgame.api.GameObserver;
import it.unibo.uniboparty.model.minigames.dinosaurgame.api.Model;
import it.unibo.uniboparty.view.minigames.dinosaurgame.api.View;

/**
 * Implementation of the game view for the Dinosaur Game as a JPanel.
 * 
 * <p>
 * Manages the game panel and delegates drawing to GamePanelImpl.
 */
public final class ViewImpl extends JPanel implements View, GameObserver {

    private static final long serialVersionUID = 1L;

    private final GamePanelImpl gamePanel;
    private final transient Model model;

    /**
     * Creates the view panel and initializes internal components.
     *
     * @param model the game model used by the panel (non-null)
     * @throws NullPointerException if model is null
     */
    public ViewImpl(final Model model) {
        this.model = Objects.requireNonNull(model, "Model cannot be null");
        this.gamePanel = new GamePanelImpl(this.model);

        gamePanel.setFocusable(true);
        gamePanel.requestFocusInWindow();

        // Register as observer now so model updates can be observed
        this.model.addObserver(this);

        setPreferredSize(new Dimension(GameConfig.PANEL_WIDTH, GameConfig.PANEL_HEIGHT));
        add(this.gamePanel);
    }

    /**
     * Called by the model when it updates.
     * Ensures repaint happens on Swing event thread.
     */
    @Override
    public void modelUpdated() {
        SwingUtilities.invokeLater(() -> {
            if (gamePanel != null) {
                gamePanel.repaint();
            }
        });
    }

    /**
     * Unregisters this view from the model.
     * Should be called when the view is no longer needed (e.g., window closing).
     */
    public void unbindModel() {
        model.removeObserver(this);
    }

    /**
     * Adds a key listener to the game panel.
     *
     * @param listener the key listener to attach (must not be null)
     */
    public void addPanelKeyListener(final KeyListener listener) {
        gamePanel.addKeyListener(Objects.requireNonNull(listener, "Listener cannot be null"));
    }

    /**
     * Requests focus for the game panel.
     */
    public void requestPanelFocus() {
        gamePanel.requestFocusInWindow();
    }

    /**
     * Sets the game panel as focusable or not.
     *
     * @param focusable true to allow focus, false otherwise
     */
    public void setPanelFocusable(final boolean focusable) {
        gamePanel.setFocusable(focusable);
    }
}
