package it.unibo.uniboparty.model.minigames.mazegame.impl;

import java.util.ArrayList;
import java.util.List;

import it.unibo.uniboparty.model.minigames.mazegame.api.Cell;
import it.unibo.uniboparty.model.minigames.mazegame.api.MazeGenerator;
import it.unibo.uniboparty.model.minigames.mazegame.api.MazeModel;
import it.unibo.uniboparty.model.minigames.mazegame.api.Player;
import it.unibo.uniboparty.utilities.CellType;
import it.unibo.uniboparty.utilities.Direction;
import it.unibo.uniboparty.view.minigames.mazegame.api.GameObserver;

/**
 * Implementation of the MazeModel interface.
 */
public class MazeModelImpl implements MazeModel {
    private static final int MINUTE_MILLIS = 30_000;
    private static final int MAX_MOVES_NUM = 65;
    private final MazeGridImpl grid;
    private final Player player;

    private final List<GameObserver> observers = new ArrayList<>();

    private boolean win;
    private boolean lose;
    private int currentMoves;
    private final long startTimeMillis;
 
    /**
     * Constructor for MazeModelImpl that generates a new maze.
     */
    public MazeModelImpl() {
        final MazeGenerator generator = new MazeGeneratorImpl();
        this.grid = new MazeGridImpl(generator.generate());
        this.player = new PlayerImpl(this.grid, grid.getStartRow(), grid.getStartCol());
        this.startTimeMillis = System.currentTimeMillis();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getRows() {
        return this.grid.getGrid().length;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCols() {
        return this.grid.getGrid()[0].length;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Cell getCell(final int row, final int col) {
        return this.grid.getGrid()[row][col];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Player getPlayer() {
        return this.player.getCopy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean movePlayer(final Direction dir) {
        if (win || lose) {
            return false;
        }
        int newRow = this.player.getRow();
        int newCol = this.player.getCol();

        switch (dir) {
            case UP: 
                newRow--; 
                break;
            case DOWN: 
                newRow++; 
                break;
            case LEFT: 
                newCol--; 
                break;
            case RIGHT: 
                newCol++; 
                break;

        }
        if (!isInside(newRow, newCol)) {
            return false;
        }

       final Cell target = getCell(newRow, newCol);
        if (target.getType() == CellType.WALL) {
            return false;
        }

        this.player.setPosition(newRow, newCol);
        currentMoves++;

        checkEndConditions();
        notifyObservers();
        return true;
    }

    private boolean isInside(final int row, final int col) {
        return row >= 0 && row < getRows() && col >= 0 && col < getCols();
    }

    private void checkEndConditions() {
        if (checkWin()) {
            win = true;
        }
    }

    private void notifyObservers() {
        for (final GameObserver o : observers) {
            o.onModelUpdated(this);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getStartTimeMillis() {
        return this.startTimeMillis;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void addObserver(final GameObserver o) {
        this.observers.add(o);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeObserver(final GameObserver o) {
        this.observers.remove(o);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() {
        if (grid == null) {
            return;
        }
        player.setPosition(grid.getStartRow(), grid.getStartCol());
        currentMoves = 0;
        win = false;
        lose = false;
        notifyObservers();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCurrentMoves() {
        return this.currentMoves;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMaxMoves() {
        return MAX_MOVES_NUM;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkWin() {
        return this.player.getCurrentCell().getType() == CellType.EXIT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkLose() {
        final boolean movesExceeded = this.player.getMoves() >= MAX_MOVES_NUM;
        final boolean timeExceeded = (System.currentTimeMillis() - this.getStartTimeMillis()) >= MINUTE_MILLIS;
        return movesExceeded || timeExceeded;
    }

}
