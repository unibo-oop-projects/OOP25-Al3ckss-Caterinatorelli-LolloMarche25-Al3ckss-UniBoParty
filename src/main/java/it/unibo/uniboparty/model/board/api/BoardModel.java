package it.unibo.uniboparty.model.board.api;

import java.util.List;

import it.unibo.uniboparty.model.board.CellModel;

/**
 * Read-only API for the game board model.
 */
public interface BoardModel {

    /**
     * @return total number of cells in the board
     */
    int getSize();

    /**
     * @param index the index of the cell
     * @return the cell at the given index
     * @throws IndexOutOfBoundsException if index is outside the board range
     */
    CellModel getCellAt(int index);

    /**
     * @return an unmodifiable list of all cells
     */
    List<CellModel> getCells();
}
