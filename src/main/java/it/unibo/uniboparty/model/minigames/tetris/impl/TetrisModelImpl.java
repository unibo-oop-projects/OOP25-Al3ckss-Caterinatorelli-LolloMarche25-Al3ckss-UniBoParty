package it.unibo.uniboparty.model.minigames.tetris.impl;

import it.unibo.uniboparty.model.minigames.tetris.api.TetrisModel;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import it.unibo.uniboparty.model.minigames.tetris.api.GridModel;
import it.unibo.uniboparty.model.minigames.tetris.api.ModelListener;

/**
 * Implementation of the TetrisModel interface.
 */
public final class TetrisModelImpl implements TetrisModel {
    private final GridModel grid;
    private final Random rng = new Random();
    private final List<PieceImpl> rack;
    private PieceImpl selected;
    private int score;

    /**
     * Creates a new TetrisModelImpl instance with the specified grid dimensions.
     * 
     * @param rows the number of rows
     * @param cols the number of columns
     */
    public TetrisModelImpl(final int rows, final int cols) {
        this.grid = new GridModelImpl(rows, cols);
        this.score = 0;
        this.rack = new ArrayList<>();
        this.selected = null;
        newRack();
    }

    /**
     * {@InheritDoc}.
     */
    @Override
    public void newRack() {
        rack.clear();
        while (rack.size() < 3) {
            rack.add(randomPiece());
        }
        notifyAllListeners();
    }

    /**
     * {@InheritDoc}.
     */
    @Override
    public void consumePiece(final PieceImpl p) {
        rack.remove(p);
        if (rack.isEmpty()) {
            newRack();
        }
        notifyAllListeners();
    }

    /**
     * {@InheritDoc}.
     */
    @Override
    public void award(final int cellsPlaced, final int linesCleared) {
        score += cellsPlaced + (linesCleared * 10);
    }

    /**
     * {@InheritDoc}.
     */
    @Override
    public boolean hasAnyMove() {
        for (final PieceImpl p : rack) {
            for (int r = 0; r < grid.getRows(); r++) {
                for (int c = 0; c < grid.getCols(); c++) {
                    if (grid.canPlace(p, r, c)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * {@InheritDoc}.
     */
    @Override

    public PieceImpl randomPiece() {
        final List<PieceImpl> bag = StandardPieces.ALL;
        return bag.get(rng.nextInt(bag.size()));
    }

    /**
     * {@InheritDoc}.
     */
    @Override
    // Delegate listeners to grid to keep it simple
    public void addListener(final ModelListener l) {
        grid.addListener(l);
    }

    /**
     * {@InheritDoc}.
     */
    @Override
    public void notifyAllListeners() {
        grid.fireChange(); /* no-op but ensures consistency */
    }

    /**
     * {@InheritDoc}.
     */
    @Override
    public boolean checkPlacement(final PieceImpl p, final int r, final int c) {
        return this.grid.canPlace(p, r, c);
    }

    /**
     * {@InheritDoc}.
     */
    @Override
    public void askPlacement(final PieceImpl p, final int r, final int c) {
        this.grid.place(p, r, c);
    }

    /**
     * {@InheritDoc}.
     */
    @Override
    public int getScore() {
        return this.score;
    }

    /**
     * {@InheritDoc}.
     */
    @Override
    public int askClearFullLines() {
        return this.grid.clearFullLines();
    }

    /**
     * {@InheritDoc}.
     */
    @Override
    public GridModel getGrid() {
        return this.grid.copy();
    }

    /**
     * {@InheritDoc}.
     */
    @Override
    public PieceImpl[] getRack() {
        return Collections.unmodifiableList(rack).toArray(new PieceImpl[0]);
    }

    /**
     * {@InheritDoc}.
     */
    @Override
    public void selectPiece(final PieceImpl p) {
        selected = p;
    }

    /**
     * {@InheritDoc}.
     */
    @Override
    public PieceImpl getSelected() {
        return selected;
    }

    /**
     * {@InheritDoc}.
     */
    @Override
    public void tryPlaceAt(final int r, final int c) {
        if (this.selected == null) {
            return;
        }
        if (this.grid.canPlace(this.selected, r, c)) {
            final int cellsPlaced = this.selected.getCells().size();
            this.grid.place(this.selected, r, c);
            final int linesCleared = this.grid.clearFullLines();
            award(cellsPlaced, linesCleared);
            consumePiece(this.selected);
            this.selected = null;
        } else {
            this.selected = null;
        }
    }

}
