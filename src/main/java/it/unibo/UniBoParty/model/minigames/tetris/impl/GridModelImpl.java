package it.unibo.uniboparty.model.minigames.tetris.impl;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import it.unibo.uniboparty.model.minigames.tetris.api.GridModel;
import it.unibo.uniboparty.model.minigames.tetris.api.ModelListener;

/**
 * Implementation of the GridModel interface.
 */
public final class GridModelImpl implements GridModel {
    private final int rows;
    private final int cols;
    private final boolean[][] grid;
    private final List<ModelListener> listeners = new ArrayList<>();

    /**
     * Creates a new GridModelImpl instance with the specified dimensions.
     * 
     * @param rows the number of rows
     * @param cols the number of columns
     */
    public GridModelImpl(final int rows, final int cols) {
        this.rows = rows; 
        this.cols = cols;
        this.grid = new boolean[rows][cols];
    }

    /**
     * used when i need to return a copy.
     * 
     * @param rows2 the number of row
     * @param cols2 the number of columns
     * @param newGrid the grid that needs to be copied
     */
    public GridModelImpl(final int rows2, final int cols2, final boolean[][] newGrid) {
        this.rows = rows2; 
        this.cols = cols2;
        this.grid = new boolean[rows2][cols2];
        for (int r = 0; r < rows2; r++) {
            System.arraycopy(newGrid[r], 0, this.grid[r], 0, cols2);
        }
    }

    /**
     * {@InheritDoc}.
     */
    @Override
    public void addListener(final ModelListener l) { 
        listeners.add(l); 
    }

    /**
     * {@InheritDoc}.
     */
    @Override
    public void removeListener(final ModelListener l) { 
        listeners.remove(l); 
    }

    /**
     * {@InheritDoc}.
     */
    @Override
    public void fireChange() {
         for (final ModelListener l : listeners) {
            l.onModelChanged();
         }
    }

    /**
     * {@InheritDoc}.
     */
    @Override
    public boolean isOccupied(final int r, final int c) { 
        return grid[r][c]; 
    }

    /**
     * {@InheritDoc}.
     */
    @Override
    public boolean canPlace(final PieceImpl piece, final int topR, final int leftC) {
        for (final Point rel : piece.getCells()) {
            final int r = topR + rel.y;
            final int c = leftC + rel.x;
            if (r < 0 || r >= rows || c < 0 || c >= cols) {
                return false;
            }
            if (grid[r][c]) {
                 return false;
            }
        }
        return true;
    }

    /**
     * {@InheritDoc}.
     */
    @Override
    public void place(final PieceImpl piece, final int topR, final int leftC) {
        if (!canPlace(piece, topR, leftC)) {
             throw new IllegalArgumentException("Invalid placement");
        }
        for (final Point rel : piece.getCells()) {
            grid[topR + rel.y][leftC + rel.x] = true;
        }
        //clearFullLines();
        //fireChange();
    }

    /**
     * {@InheritDoc}.
     */
    @Override
    public int clearFullLines() {

        final List<Integer> fullRows = new ArrayList<>();
        final List<Integer> fullCols = new ArrayList<>();

        for (int r = 0; r < rows; r++) {
            boolean full = true;
            for (int c = 0; c < cols; c++) {
                if (!grid[r][c]) { 
                    full = false; 
                    break; 
                }
            }
            if (full) {
                 fullRows.add(r);
            }
        }

        for (int c = 0; c < cols; c++) {
            boolean full = true;
            for (int r = 0; r < rows; r++) {
                if (!grid[r][c]) {
                      full = false; 
                      break;
                }
            }
            if (full) {
                fullCols.add(c);
            }
        }

        for (final int r : fullRows) {
            Arrays.fill(grid[r], false);
        }
        for (final int c : fullCols) {
            for (int r = 0; r < rows; r++) {
                grid[r][c] = false;
            }
        }

        return fullRows.size() + fullCols.size();
    }

    /**
     * {@InheritDoc}.
     */
    @Override
    public void reset() {
        for (int r = 0; r < rows; r++) {
            Arrays.fill(grid[r], false);
        }
        fireChange();
    }

    /**
     * {@InheritDoc}.
     */
    @Override
    public int getRows() {
        return rows; 
    }

    /**
     * {@InheritDoc}.
     */
    @Override
    public int getCols() {
        return cols; 
    }

    /**
     * {@InheritDoc}.
     */
    @Override
    public GridModel copy() {
        final boolean[][] newGrid = new boolean[rows][cols];

        for (int r = 0; r < rows; r++) {
            System.arraycopy(this.grid[r], 0, newGrid[r], 0, cols);
        }
        return new GridModelImpl(rows, cols, newGrid);
    }
}
