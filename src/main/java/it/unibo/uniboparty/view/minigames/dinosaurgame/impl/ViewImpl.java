package it.unibo.uniboparty.view.minigames.dinosaurgame.impl;

import java.awt.Dimension;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import it.unibo.uniboparty.model.minigames.dinosaurgame.impl.GameConfig;
import it.unibo.uniboparty.model.minigames.dinosaurgame.impl.ModelImpl;
import it.unibo.uniboparty.model.minigames.dinosaurgame.api.GameObserver;
import it.unibo.uniboparty.view.minigames.dinosaurgame.api.View;

/**
 * Implementation of the game view for the Dinosaur Game.
 * 
 * <p>
 * Manages the main game window and delegates drawing to GamePanelImpl.
 */
public final class ViewImpl implements View, GameObserver {

    private final GamePanelImpl panel1;
    private final JFrame frame;
    private boolean showGameOver;
    private final ModelImpl model;
    /**
     * Creates the view and initializes internal components. The returned frame
     * is not shown automatically; callers should call `createGameFrame()` and
     * then `setVisible(true)`.
     *
     * @param model the game model used by the panel
     */
    public ViewImpl(final ModelImpl model) {
        this.frame = new JFrame("Dino Game");
        this.panel1 = new GamePanelImpl(model);
        this.model = model;

        panel1.setFocusable(true);
        panel1.requestFocusInWindow();

        // Register as observer now so model updates can be observed before
        // the frame is shown. The window listener that removes the observer
        // will be added in createGameFrame().
        this.model.addObserver(this);
    }

    /**
     * Build and return the JFrame that hosts the dinosaur game. The frame is
     * configured but not shown; the caller should call `setVisible(true)`.
     *
     * @return configured {@link JFrame}
     */
    public JFrame createGameFrame() {
        frame.setMinimumSize(new Dimension(GameConfig.PANEL_WIDTH, GameConfig.PANEL_HEIGHT));
        frame.add(this.panel1);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Deregister observer when window is closing to avoid leaks
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent e) {
                model.removeObserver(ViewImpl.this);
            }
        });

        return frame;
    }

    /**
     * Repaints the game panel.
     */
    @Override
    public void repaint() {
        panel1.repaint();
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
     * Adds a key listener to the game panel in a safe way.
     *
     * @param listener the key listener to attach
     */
    public void addPanelKeyListener(final KeyListener listener) {
        panel1.addKeyListener(listener);
    }

    /**
     * Requests focus for the game panel.
     */
    public void requestPanelFocus() {
        panel1.requestFocusInWindow();
    }

    /**
     * Sets the game panel as focusable or not.
     *
     * @param focusable true to allow focus, false otherwise
     */
    public void setPanelFocusable(final boolean focusable) {
        panel1.setFocusable(focusable);
    }

    /**
     * Displays the game-over overlay on the panel.
     */
    public void showGameOverOverlay() {
        this.showGameOver = true;
        panel1.repaint();
    }

    /**
     * @return whether the game-over overlay should be shown.
     */
    public boolean isGameOver() {
        return this.showGameOver;
    }
}
