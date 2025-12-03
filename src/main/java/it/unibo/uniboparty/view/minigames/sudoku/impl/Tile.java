package it.unibo.uniboparty.view.minigames.sudoku.impl;

import javax.swing.JButton;

/**
 * Helper class representing a single cell (Tile) of the Sudoku grid.
 *
 * <p>
 * It extends {@link JButton} to provide clickable functionality and
 * stores its specific coordinates (row and column) within the 9x9 grid.
 */
public class Tile extends JButton {

    private static final long serialVersionUID = 1L;

    @SuppressWarnings("PMD.UnusedPrivateField")
    private final int rowNum;

    @SuppressWarnings("PMD.UnusedPrivateField")
    private final int colNum;

    /**
     * Constructs a new Tile at the specified position.
     *
     * @param rowNum The specific row index (0-8).
     * @param colNum The specific column index (0-8).
     */
    public Tile(final int rowNum, final int colNum) {
        super();
        this.rowNum = rowNum;
        this.colNum = colNum;
    }
}
