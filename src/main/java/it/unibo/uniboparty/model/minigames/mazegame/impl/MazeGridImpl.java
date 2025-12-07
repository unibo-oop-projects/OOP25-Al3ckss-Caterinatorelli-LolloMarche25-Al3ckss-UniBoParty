package it.unibo.uniboparty.model.minigames.mazegame.impl;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

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

        final AtomicReference<int[]> startPos = new AtomicReference<>(new int[]{-1, -1});
        final AtomicReference<int[]> exitPos = new AtomicReference<>(new int[]{-1, -1});

        IntStream.range(0, rows).forEach(r -> {
            IntStream.range(0, cols).forEach(c -> {
                final CellType type = layout[r][c];
                grid[r][c] = new CellImpl(r, c, type);

                if (type == CellType.START) {
                    startPos.set(new int[]{r, c});
                } else if (type == CellType.EXIT) {
                    exitPos.set(new int[]{r, c});
                }
            });
        });

        this.startRow = startPos.get()[0];
        this.startCol = startPos.get()[1];
        this.exitRow = exitPos.get()[0];
        this.exitCol = exitPos.get()[1];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Cell[][] getGrid() {
       return Arrays.stream(this.grid)
                     .map(row -> {
                         final Cell[] copyRow = new Cell[row.length];
                         System.arraycopy(row, 0, copyRow, 0, row.length);
                         return copyRow;
                     })
                     .toArray(Cell[][]::new);
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
