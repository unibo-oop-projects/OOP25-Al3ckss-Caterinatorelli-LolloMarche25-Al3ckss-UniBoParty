package it.unibo.uniboparty.model.minigames.mazegame.impl;

import it.unibo.uniboparty.model.minigames.mazegame.api.Cell;
import it.unibo.uniboparty.utilities.CellType;

/**
 * Implementation of the Cell interface.
 */
public class CellImpl implements Cell {
    private final int row;
    private final int col;
    private final CellType type;
    /**
     * Constructor for CellImpl.
     * 
     * @param row rapprezenting the row index of the cell
     * @param col rapprezenting the column index of the cell
     * @param type rapprezenting the type of the cell
     */

    public CellImpl(final int row, final int col, final CellType type) {
        this.row = row;
        this.col = col;
        this.type = type;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getRow() {
        return this.row;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCol() {
        return this.col;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CellType getType() {
        return this.type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isWalkable() {
        return this.type != CellType.WALL;
    }

}
