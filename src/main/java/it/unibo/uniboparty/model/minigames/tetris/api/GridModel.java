package it.unibo.uniboparty.model.minigames.tetris.api;

import it.unibo.uniboparty.model.minigames.tetris.impl.PieceImpl;

/**
 * Represents the model for the Tetris grid.
 * Provides methods for managing listeners, placing pieces, checking occupancy,
 * clearing lines, and resetting the grid.
 */
public interface GridModel {

    /**
     * Adds a listener to be notified of changes in the grid model.
     *
     * @param l the listener to add
     */
    void addListener(ModelListener l);

    /**
     * Removes a previously added listener.
     *
     * @param l the listener to remove
     */
    void removeListener(ModelListener l);

    /**
     * Notifies all registered listeners of a change in the grid model.
     */
    void fireChange();

    /**
     * Checks if the specified cell in the grid is occupied.
     *
     * @param r the row index
     * @param c the column index
     * @return true if the cell is occupied, false otherwise
     */
    boolean isOccupied(int r, int c);

    /**
     * Determines if the given piece can be placed at the specified position.
     *
     * @param piece the piece to place
     * @param topR the top row index for placement
     * @param leftC the left column index for placement
     * @return true if the piece can be placed, false otherwise
     */
    boolean canPlace(PieceImpl piece, int topR, int leftC);

    /**
     * Places the given piece at the specified position in the grid.
     *
     * @param piece the piece to place
     * @param topR the top row index for placement
     * @param leftC the left column index for placement
     */
    void place(PieceImpl piece, int topR, int leftC);

    /**
     * Clears all full lines in the grid and returns the number of lines cleared.
     *
     * @return the number of lines cleared
     */
    int clearFullLines();

    /**
     * Resets the grid to its initial empty state.
     */
    void reset();

    /**
     * Returns the number of rows in the grid.
     *
     * @return the number of rows
     */
    int getRows();

    /**
     * Returns the number of columns in the grid.
     *
     * @return the number of columns
     */
    int getCols();

    /**
     * Create a copy of the grid.
     * 
     * @return the copy created
     */
    GridModel copy();

}
