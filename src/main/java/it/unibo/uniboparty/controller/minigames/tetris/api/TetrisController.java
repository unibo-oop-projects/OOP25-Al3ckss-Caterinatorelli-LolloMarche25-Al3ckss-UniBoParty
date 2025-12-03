package it.unibo.uniboparty.controller.minigames.tetris.api;

import javax.swing.JFrame;

/**
 * Interface representing the controller for the Tetris game.
 * The controller is responsible for managing the game flow and interactions
 * between the model and the view.
 */
public interface TetrisController {
    /**
     * Checks whether the game is over.
     * The game is considered over when there are no more valid moves available.
     * In this case, a "Game Over" message with the final score is shown.
     */
    void checkGameOver();

    /**
     * Starts the Tetris game by making the game view visible.
     */
    void startGame();

    /**
     * Gets the game view associated with this controller.
     * 
     * @return the game view
     */
    JFrame getView();
}
