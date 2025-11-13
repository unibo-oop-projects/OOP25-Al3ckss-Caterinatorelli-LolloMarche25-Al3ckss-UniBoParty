package it.unibo.uniboparty.model.minigames.mazegame.api;

/**
 * Represents a player in the maze game.
 */
public interface Player {
    /**
     * Get the current row of the player.
     * 
     * @return the row index of the player
     */
    int getRow();

    /**
     * Get the current column of the player.
     * 
     * @return the column index of the player
     */
    int getCol();

    /**
     * Set the position of the player.
     * 
     * @param row rappresenting the new row
     * @param col rappresenting the new column
     */
    void setPosition(int row, int col);

    /**
     * Get the number of moves made by the player.
     * 
     * @return an int rappresenting the number of moves made
     */
    int getMoves();

    /**
     * Increment the number of moves made by the player.
     */
    void incrementMoves();

    /**
     * Get the current cell where the player is located.
     * 
     * @return the Cell object representing the current cell of the player
     */
    Cell getCurrentCell();

    /**
     * Get a copy of the player.
     * 
     * @return a new Player object with the same state as the current player
     */
    Player getCopy();
}
