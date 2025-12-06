package it.unibo.uniboparty.controller.minigames.mazegame.api;

import javax.swing.JFrame;

/**
 * Interface representing the Maze Controller.
 */
public interface MazeController {

    /**
     * Start a new game by resetting the maze and player position.
     */
    void startNewGame();

    /**
     * Gets the game view associated with this controller.
     * 
     * @return the game view
     */
    JFrame getView();

    /**
     * Gets the current state of the game.
     * 
     * @return 2 if there is no result, 1 if the player has won, 0 if the game is over
     */
    int getState();
}

