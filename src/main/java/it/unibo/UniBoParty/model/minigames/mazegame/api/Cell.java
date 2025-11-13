package it.unibo.uniboparty.model.minigames.mazegame.api;

import it.unibo.uniboparty.utilities.CellType;

/**
 * Represents a cell in the maze.
 */
public interface Cell {
    /**
     * Gets the row index of the cell in the maze.
     * 
     * @return an int representing the row index.
     */
    int getRow();

    /**
     * Gets the column index of the cell in the maze.
     * 
     * @return an int representing the column index.
     */
    int getCol();

    /**
     * Gets the type of the cell.
     * 
     * @return the CellType of the cell.
     */
    CellType getType();

    /**
     * Checks if the cell is walkable (not a WALL).
     * 
     * @return true if the cell is walkable, false otherwise.
     */
    boolean isWalkable(); 

}
