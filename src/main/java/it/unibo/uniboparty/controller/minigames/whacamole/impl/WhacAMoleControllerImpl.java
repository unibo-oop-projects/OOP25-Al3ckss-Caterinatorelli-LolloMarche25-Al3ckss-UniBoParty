package it.unibo.uniboparty.controller.minigames.whacamole.impl;

import java.awt.event.ActionListener;

import javax.swing.Timer;

import it.unibo.uniboparty.controller.minigames.whacamole.api.WhacAMoleController;
import it.unibo.uniboparty.model.minigames.whacamole.WhacAMoleGameState;
import it.unibo.uniboparty.model.minigames.whacamole.api.WhacAMoleModel;
import it.unibo.uniboparty.model.minigames.whacamole.impl.WhacAMoleGame;

/**
 * Default Swing implementation of the {@link WhacAMoleController} interface.
 * 
 * <p>
 * This Controller:
 * <ul>
 *     <li>owns a concrete Model instance,</li>
 *     <li>starts and stops an internal Swing {@link Timer} to
 *         periodically update the game logic,</li>
 *     <li>translates user input (hole clicks) into actions on the Model,</li>
 *     <li>exposes read-only game state information to the View.</li>
 * </ul>
 * </p>
 */
public final class WhacAMoleControllerImpl implements WhacAMoleController {

    /** Interval between two logic updates (in milliseconds). */
    private static final int TICK_MILLIS = 100;

    /**
     * Result codes for the game:
     * 2 = in progress, 1 = won, 0 = lost.
     */
    private static final int RESULT_LOST = 0;
    private static final int RESULT_WON = 1;
    private static final int RESULT_IN_PROGRESS = 2;

    private final WhacAMoleModel game;
    private Timer gameLoop;

    /**
     * Encoded game result: 2 = in progress, 1 = won, 0 = lost.
     */
    private int resultCode;

    /**
     * Creates a new controller with its own game Model.
     */
    public WhacAMoleControllerImpl() {
        this.game = new WhacAMoleGame();
        this.resultCode = RESULT_IN_PROGRESS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startGame() {
        this.game.startGame();
        this.resultCode = RESULT_IN_PROGRESS;

        // If a previous loop exists, stop it before starting a new one
        if (this.gameLoop != null) {
            this.gameLoop.stop();
        }

        // Create a periodic task that advances the game logic
        final ActionListener task = e -> {
            updateGameLogic(TICK_MILLIS);
            stopIfGameOver();
        };

        this.gameLoop = new Timer(TICK_MILLIS, task);
        this.gameLoop.start();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateGameLogic(final long elapsedMillis) {
        this.game.tick(elapsedMillis);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onHoleClicked(final int index) {
        this.game.hitHole(index);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WhacAMoleGameState getState() {
        return this.game.getGameState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCurrentObjectABomb() {
        return this.game.isCurrentObjectABomb();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getResultCode() {
        return this.resultCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stopIfGameOver() {
        final WhacAMoleGameState state = this.game.getGameState();
        if (this.gameLoop != null && state.isGameOver()) {
            this.gameLoop.stop();

            // Simple rule:
            // if the player scored at least 1 point, consider it a win,
            // otherwise it is a loss.
            if (state.getScore() > 0) {
                this.resultCode = RESULT_WON;
            } else {
                this.resultCode = RESULT_LOST;
            }
        }
    }
}
