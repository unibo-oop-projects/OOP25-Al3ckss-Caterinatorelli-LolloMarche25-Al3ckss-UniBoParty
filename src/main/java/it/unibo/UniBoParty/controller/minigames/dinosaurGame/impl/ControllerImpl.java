package it.unibo.uniboparty.controller.minigames.dinosaurgame.impl;

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
 */
public final class ControllerImpl implements Controller {

    private final ModelImpl model;
    private final ViewImpl view;
    private Timer timer;

    /**
     * Creates the controller and initializes listeners and timers.
     *
     * @param model the game model
     * @param view the game view
     */
    public ControllerImpl(final ModelImpl model, final ViewImpl view) {
        this.model = model;
        this.view = view;

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
                view.repaint();

                if (model.getGameState() == GameState.GAME_OVER) {
                    timer.stop();
                    // Game over reached
                }
            }
        });
        timer.start();
    }

    /**
     * Sets up the key listener handling jump input.
     */
    private void setupKeyListener() {
        view.getPanel().addKeyListener(new KeyAdapter() {

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

        view.getPanel().setFocusable(true);
        view.getPanel().requestFocusInWindow();
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
}
