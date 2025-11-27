package it.unibo.uniboparty.model.minigames.mazegame.api;

/**
 * Interface representing the maze grid.
 */
public interface MazeGrid {
    /**
     * Get the grid of Cells representing the maze.
     * 
     * @return the 2D array of Cells.
     */
    Cell[][] getGrid();

    /**
     * Get the initial row index of the maze.
     * 
     * @return an int rappresenting the start row.
     */
    int getStartRow();

    /**
     * Get the initial col index of the maze.
     * 
     * @return an int rappresenting the start col.
     */
    int getStartCol();

    /**
     * Get the exit row index of the maze.
     * 
     * @return an int rappresenting exit row .
     * 
     */
    int getExitRow();

    /**
     * Get the exit column index of the maze.
     * 
     * @return an int rappresenting exit col
     */
    int getExitCol();
}
