package it.unibo.uniboparty.model.minigames.mazegame.impl;

import it.unibo.uniboparty.model.minigames.mazegame.api.Cell;
import it.unibo.uniboparty.model.minigames.mazegame.api.MazeGrid;
import it.unibo.uniboparty.model.minigames.mazegame.api.Player;

/**
 * Implementation of the Player interface.
 */
public class PlayerImpl implements Player {
    private int row;
    private int col;
    private int moves;
    private final MazeGrid grid;

    /**
     * Constructor for PlayerImpl.
     * 
     * @param grid rapprezenting the maze grid
     * @param row rapprezenting the position row of the player
     * @param col rapprezenting the position col of the player
     */
    public PlayerImpl(final MazeGrid grid, final int row, final int col) {
        this.row = row;
        this.col = col;
        this.grid = grid;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getRow() {
        return row;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCol() {
        return col;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPosition(final int newRow, final int newCol) {
        this.row = newRow;
        this.col = newCol;
        incrementMoves();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMoves() {
        return moves;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void incrementMoves() {
        moves++;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Cell getCurrentCell() {
        return grid.getGrid()[row][col];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Player getCopy() {
        return new PlayerImpl(this.grid, this.row, this.col);
    }

}
