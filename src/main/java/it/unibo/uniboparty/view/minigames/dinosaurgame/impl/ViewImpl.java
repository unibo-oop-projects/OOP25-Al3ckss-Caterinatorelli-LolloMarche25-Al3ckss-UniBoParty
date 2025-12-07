package it.unibo.uniboparty.view.minigames.dinosaurgame.impl;

import java.awt.Dimension;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import it.unibo.uniboparty.model.minigames.dinosaurgame.impl.GameConfig;
import it.unibo.uniboparty.model.minigames.dinosaurgame.api.GameObserver;
import it.unibo.uniboparty.model.minigames.dinosaurgame.api.Model;
import it.unibo.uniboparty.view.minigames.dinosaurgame.api.View;

/**
 * Implementation of the game view for the Dinosaur Game.
 * 
 * <p>
 * Manages the main game window and delegates drawing to GamePanelImpl.
 */
public final class ViewImpl implements View, GameObserver {

    private final GamePanelImpl gamePanel;
    private final JFrame frame;
    private final Model model;
    private boolean frameConfigured;

    /**
     * Creates the view and initializes internal components.
     *
     * @param model the game model used by the panel (non-null)
     * @throws NullPointerException if model is null
     */
    public ViewImpl(final Model model) {
        this.model = Objects.requireNonNull(model, "Model cannot be null");
        this.frame = new JFrame("Dino Game");
        this.gamePanel = new GamePanelImpl(this.model);

        gamePanel.setFocusable(true);
        gamePanel.requestFocusInWindow();

        // Register as observer now so model updates can be observed
        this.model.addObserver(this);
    }

    /**
     * Builds and returns the JFrame hosting the dinosaur game.
     * The frame is configured but not shown; the caller should call `setVisible(true)`.
     * This method can be called only once.
     *
     * @return the configured {@link JFrame}
     * @throws IllegalStateException if called more than once
     */
    public JFrame createGameFrame() {
        if (frameConfigured) {
            throw new IllegalStateException("Frame already configured");
        }
        configureFrame();
        frameConfigured = true;
        return frame;
    }

    /**
     * Configures the internal frame components and layout.
     */
    private void configureFrame() {
        frame.setMinimumSize(new Dimension(GameConfig.PANEL_WIDTH, GameConfig.PANEL_HEIGHT));
        frame.add(this.gamePanel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Deregister observer when window is closing to avoid memory leaks
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent e) {
                model.removeObserver(ViewImpl.this);
            }
        });
    }

    /**
     * Repaints the game panel.
     */
    @Override
    public void repaint() {
        gamePanel.repaint();
    }

    /**
     * Called by the model when it updates.
     * Ensures repaint happens on the Swing event dispatch thread.
     */
    @Override
    public void modelUpdated() {
        SwingUtilities.invokeLater(this::repaint);
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
