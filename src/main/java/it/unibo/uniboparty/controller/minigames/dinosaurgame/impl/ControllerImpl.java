package it.unibo.uniboparty.controller.minigames.dinosaurgame.impl;

import java.util.Objects;
import javax.swing.Timer;

import it.unibo.uniboparty.controller.minigames.dinosaurgame.api.Controller;
import it.unibo.uniboparty.model.minigames.dinosaurgame.impl.GameConfig;
import it.unibo.uniboparty.model.minigames.dinosaurgame.impl.GameState;
import it.unibo.uniboparty.model.minigames.dinosaurgame.impl.ModelImpl;
import it.unibo.uniboparty.view.minigames.dinosaurgame.impl.ViewImpl;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Controller implementation that handles input events and game loop updates.
 * 
 * <p>
 * Manages the game loop (timer for physics/state updates), input handling (keyboard),
 * and delegates UI updates to the view via observer pattern.
 * The view registers itself as observer to the model and repaints automatically
 * when the model state changes.
 * </p>
 */
public final class ControllerImpl implements Controller {

    private final ModelImpl model;

    // We store the view reference directly because the controller needs to update it.
    private final ViewImpl view;

    private Timer timer;

    /**
     * Creates the controller and initializes listeners and timers.
     *
     * @param model the game model
     * @param view the game view
     */
    public ControllerImpl(final ModelImpl model, final ViewImpl view) {
        this.model = Objects.requireNonNull(model, "Model cannot be null");
        this.view = Objects.requireNonNull(view, "View cannot be null");

        setupTimer();
        setupKeyListener();
    }

    /**
     * Initializes the timer responsible for updating the game loop.
     */
    private void setupTimer() {
        timer = new Timer(GameConfig.TIMER_DELAY_MS, e -> {
            if (model.getGameState() == GameState.RUNNING) {
                model.update();

                // Stop timer if game over or won
                if (model.getGameState() == GameState.GAME_OVER
                        || model.getGameState() == GameState.WIN) {
                    timer.stop();
                }
            }
        });
        timer.start();
    }

    /**
     * Sets up the key listener handling jump input.
     */
    private void setupKeyListener() {
        view.addPanelKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(final KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE
                        && model.getGameState() == GameState.RUNNING) {
                    model.jump();
                }
            }

            @Override
            public void keyReleased(final KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE
                        && model.getGameState() == GameState.RUNNING) {
                    model.releaseJump();
                }
            }
        });

        view.setPanelFocusable(true);
        view.requestPanelFocus();
    }

    /**
     * Starts the game loop.
     */
    @Override
    public void start() {
        timer.start();
    }

    /**
     * Stops the game loop.
     */
    @Override
    public void stop() {
        timer.stop();
    }

    /**
     * Returns the current game state.
     * 
     * @return 0 if game lost, 1 if game won, 2 if still running
     */
    @Override
    public int getState() {
        return switch (model.getGameState()) {
            case WIN -> 1;
            case GAME_OVER -> 0;
            default -> 2;
        };
    }
}
