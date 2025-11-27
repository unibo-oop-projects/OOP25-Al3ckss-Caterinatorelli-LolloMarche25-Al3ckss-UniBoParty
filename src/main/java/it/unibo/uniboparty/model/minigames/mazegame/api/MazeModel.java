package it.unibo.uniboparty.model.minigames.mazegame.api;

import it.unibo.uniboparty.utilities.Direction;
import it.unibo.uniboparty.view.minigames.mazegame.api.GameObserver;

/**
 * Main model interface.
 * exposes operation for moving the player, getting cells,resetting and querying game state
 * notify observers on state changes.
 */
public interface MazeModel {
    /**
     * Get maze dimensions (number of rows).
     * 
     * @return an int rappresenting number of rows
     */
    int getRows();

    /**
     * Get maze dimensions (number of columns).
     * 
     * @return an int rappresenting number of columns
     */
    int getCols();

    /**
     * Get a specific cell.
     * 
     * @param row current row
     * @param col current col
     * @return a cell at the given position
     */
    Cell getCell(int row, int col);

    /**
     * Get the player.
     * 
     * @return a Player object representing the player
     */
    Player getPlayer();

    /**
     * Move the player in the given direction.
     * 
     * @param dir the direction to move
     * @return true if the move was successful, false otherwise
     */
    boolean movePlayer(Direction dir);

    /**
     * Get the current number of moves made by the player.
     * 
     * @return an int rappresenting current moves
     */
    int getCurrentMoves();

    /**
     * Get the maximum number of moves allowed.
     * 
     * @return an int rappresenting maximum moves allowed
     */
    int getMaxMoves();

    /**
     * Reset the game to the initial state.
     */
    void reset();

    /**
     * Get the start time in milliseconds.
     * 
     * @return a long rappresenting start time in milliseconds
     */
    long getStartTimeMillis();

    /**
     * Add an observer to the model.
     * 
     * @param o the observer to add
     */
    void addObserver(GameObserver o);

    /**
     * Remove an observer from the model.
     * 
     * @param o the observer to remove
     */
    void removeObserver(GameObserver o);

    /**
     * Check if the player has won.
     * 
     * @return true if the player has won, false otherwise
     */
    boolean checkWin();

    /**
     * Check if the player has lost.
     * 
     * @return true if the player has lost, false otherwise
     */
    boolean checkLose();

}

