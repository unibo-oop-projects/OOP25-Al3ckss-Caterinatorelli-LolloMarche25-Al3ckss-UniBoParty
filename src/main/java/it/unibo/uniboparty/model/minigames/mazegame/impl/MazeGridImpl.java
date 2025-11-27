package it.unibo.uniboparty.model.minigames.mazegame.impl;

import it.unibo.uniboparty.model.minigames.mazegame.api.Cell;
import it.unibo.uniboparty.model.minigames.mazegame.api.MazeGrid;
import it.unibo.uniboparty.utilities.CellType;

/**
 * Implementation of the MazeGrid interface.
 */
public class MazeGridImpl implements MazeGrid {
    private final Cell[][] grid;
    private final int startRow;
    private final int startCol;
    private final int exitRow;
    private final int exitCol;

    /**
     * Constructor for MazeGridImpl.
     * 
     * @param layout rapprezenting the layout of the maze
     */
    public MazeGridImpl(final CellType[][] layout) {
        final int rows = layout.length;
        final int cols = layout[0].length;
        this.grid = new Cell[rows][cols];
        int foundStartRow = -1;
        int foundStartCol = -1;
        int foundExitRow = -1;
        int foundExitCol = -1;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                final CellType type = layout[r][c];
                grid[r][c] = new CellImpl(r, c, type);
                if (type == CellType.START) {
                    foundStartRow = r;
                    foundStartCol = c;
                } else if (type == CellType.EXIT) {
                    foundExitRow = r;
                    foundExitCol = c;
                }
            }
        }
        this.startRow = foundStartRow;
        this.startCol = foundStartCol;
        this.exitRow = foundExitRow;
        this.exitCol = foundExitCol;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Cell[][] getGrid() {
        final int rows = grid.length;
        final int cols = grid[0].length;
        final Cell[][] copy = new Cell[rows][cols];
        for (int r = 0; r < rows; r++) {
            System.arraycopy(grid[r], 0, copy[r], 0, cols);
        }
        return copy;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getStartRow() {
        return this.startRow;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getStartCol() {
        return this.startCol;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getExitRow() {
        return this.exitRow;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getExitCol() {
        return this.exitCol;
    }

}
