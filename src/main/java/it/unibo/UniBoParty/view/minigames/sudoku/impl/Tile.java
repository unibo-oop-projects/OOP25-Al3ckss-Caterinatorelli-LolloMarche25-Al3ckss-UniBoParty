package it.unibo.UniBoParty.view.minigames.sudoku.impl;

import javax.swing.JButton;

/**
 * Helper class representing a single cell (Tile) of the Sudoku grid.
 * <p>
 * It extends {@link JButton} to provide clickable functionality and
 * stores its specific coordinates (row and column) within the 9x9 grid.
 * </p>
 */
public class Tile extends JButton {

    public final int rownum;
    public final int colnum;

    /**
     * Constructs a new Tile at the specified position.
     *
     * @param rownum The specific row index (0-8).
     * @param colnum The specific column index (0-8).
     */
    public Tile(int rownum, int colnum) {
        super();
        this.rownum = rownum;
        this.colnum = colnum;
    }
}