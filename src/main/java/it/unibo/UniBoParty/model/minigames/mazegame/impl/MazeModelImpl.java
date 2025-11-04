package it.unibo.UniBoParty.model.minigames.mazegame.impl;

import java.util.ArrayList;
import java.util.List;

import it.unibo.UniBoParty.model.minigames.mazegame.api.Cell;
import it.unibo.UniBoParty.utilities.CellType;
import it.unibo.UniBoParty.utilities.Direction;
import it.unibo.UniBoParty.model.minigames.mazegame.api.MazeGenerator;
import it.unibo.UniBoParty.model.minigames.mazegame.api.MazeModel;
import it.unibo.UniBoParty.model.minigames.mazegame.api.Player;
import it.unibo.UniBoParty.view.minigames.mazegame.api.GameObserver;
/**
 * Implementation of the MazeModel interface.
 */
public class MazeModelImpl implements MazeModel {
    private static final int MINUTE_MILLIS = 30000;
    private static final int MAX_MOVES_NUM = 65;
    private final MazeGridImpl grid;
    private final Player player;

    private final List<GameObserver> observers = new ArrayList<>();

    private boolean win = false;
    private boolean lose = false;
    private int maxMoves = MAX_MOVES_NUM;
    private int currentMoves = 0;
    private final long startTimeMillis;
    private long timeLimitMillis = MINUTE_MILLIS;
 
    /**
     * Constructor for MazeModelImpl that generates a new maze.
     */
    public MazeModelImpl() {
        MazeGenerator generator = new MazeGeneratorImpl();
        this.grid = new MazeGridImpl(generator.generate());
        this.player = new PlayerImpl(this.grid, grid.getStartRow(), grid.getStartCol());
        this.startTimeMillis = System.currentTimeMillis();
    }

    /**
     * @{inheritDoc}
     */
    @Override
    public int getRows() {
        return this.grid.getGrid().length;
    }

    /**
     * @{inheritDoc}
     */
    @Override
    public int getCols() {
        return this.grid.getGrid()[0].length;
    }

    /**
     * @{inheritDoc}
     */
    @Override
    public Cell getCell(int row, int col) {
        return this.grid.getGrid()[row][col];
    }

    /**
     * @{inheritDoc}
     */
    @Override
    public Player getPlayer() {
        return this.player;
    }

    /**
     * @{inheritDoc}
     */
    @Override
    public boolean movePlayer(Direction dir) {
        if (win || lose)
            return false;
        int newRow = this.player.getRow();
        int newCol = this.player.getCol();

        // Calcola nuova posizione in base alla direzione
        switch (dir) {
            case UP -> newRow--; // vai su
            case DOWN -> newRow++; // vai giù
            case LEFT -> newCol--; // vai a sinistra
            case RIGHT -> newCol++; // vai a destra
        }
        if (!isInside(newRow, newCol))
            return false;

        Cell target = getCell(newRow, newCol);
        if (target.getType() == CellType.WALL)
            return false;

        this.player.setPosition(newRow, newCol);
        currentMoves++;

        checkEndConditions();
        notifyObservers();
        return true;
    }

    private boolean isInside(int row, int col) {
        return row >= 0 && row < getRows() && col >= 0 && col < getCols();
    }

    private void checkEndConditions() {
        if (checkWin()) {
            win = true;
            return;
        }
    }

    private void notifyObservers() {
        for (GameObserver o : observers) {
            o.onModelUpdated(this);
        }
    }

    /**
     * @{inheritDoc}
     */
    @Override
    public int getMaxMoves() {
        return this.maxMoves;
    }

    /**
     * @{inheritDoc}
     */
    @Override
    public long getStartTimeMillis() {
        return this.startTimeMillis;
    }

    /**
     * @{inheritDoc}
     */
    @Override
    public long getTimeLimitMillis() {
        return this.timeLimitMillis;
    }

    /**
     * @{inheritDoc}
     */

    @Override
    public void addObserver(GameObserver o) {
        this.observers.add(o);
    }

    /**
     * @{inheritDoc}
     */
    @Override
    public void removeObserver(GameObserver o) {
        this.observers.remove(o);
    }

    /**
     * @{inheritDoc}
     */
    @Override
    public void reset() {
        if (grid == null)
            return;
        player.setPosition(grid.getStartRow(), grid.getStartCol());
        currentMoves = 0;
        win = false;
        lose = false;
        notifyObservers();
    }

    /**
     * @{inheritDoc}
     */
    @Override
    public int getCurrentMoves() {
        return this.currentMoves;
    }

    /**
     * @{inheritDoc}
     */
    @Override
    public boolean checkWin() {
        return this.player.getCurrentCell().getType() == CellType.EXIT;
    }

    /**
     * @{inheritDoc}
     */
    @Override
    public boolean checkLose() {
        boolean movesExceeded = this.player.getMoves() >= this.getMaxMoves();
        boolean timeExceeded = (System.currentTimeMillis() - this.getStartTimeMillis()) >= this.getTimeLimitMillis();
        return movesExceeded || timeExceeded;
    }

}
