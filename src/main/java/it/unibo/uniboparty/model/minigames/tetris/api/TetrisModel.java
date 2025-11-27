package it.unibo.uniboparty.model.minigames.tetris.api;

import it.unibo.uniboparty.model.minigames.tetris.impl.PieceImpl;

/**
 * Represents the model for the overall Tetris game logic.
 * Provides methods for managing the rack of pieces, scoring, listeners, and piece generation.
 */
public interface TetrisModel {

    /**
     * Starts a new rack of pieces for the player.
     */
    void newRack();

    /**
     * Consumes (removes) the specified piece from the current rack.
     *
     * @param p the piece to consume
     */
    void consumePiece(PieceImpl p);

    /**
     * Awards points to the player based on the number of cells placed and lines cleared.
     *
     * @param cellsPlaced the number of cells placed on the grid
     * @param linesCleared the number of lines cleared
     */
    void award(int cellsPlaced, int linesCleared);

    /**
     * Checks if there is any possible move left for the player.
     *
     * @return true if at least one move is possible, false otherwise
     */
    boolean hasAnyMove();

    /**
     * Generates and returns a random piece.
     *
     * @return a randomly generated piece
     */
    PieceImpl randomPiece();

    /**
     * Adds a listener to be notified of changes in the game model.
     * Delegate listeners to grid to keep it simple
     *
     * @param l the listener to add
     */
    void addListener(ModelListener l);

    /**
     * Notifies all registered listeners of a change in the game model.
     */
    void notifyAllListeners();

    /**
     * Checks if the specified piece can be placed at the given coordinates on the grid.
     *
     * @param p the piece to check
     * @param r the target row
     * @param c the target column
     * @return true if the piece can be placed, false otherwise
     */
    boolean checkPlacement(PieceImpl p, int r, int c);

    /**
     * Places the specified piece at the given coordinates on the grid.
     *
     * @param p the piece to place
     * @param r the target row
     * @param c the target column
     */
    void askPlacement(PieceImpl p, int r, int c);

    /**
     * Returns the current score of the player.
     *
     * @return the current score
     */
    int getScore();

    /**
     * Clears all full lines in the grid and returns the number of lines cleared.
     *
     * @return the number of lines cleared
     */
    int askClearFullLines();

    /**
     * Returns the grid model associated with the game.
     *
     * @return the grid model
     */
    GridModel getGrid();

    /**
     * Returns the current rack of pieces available to the player.
     *
     * @return an array of pieces in the current rack
     */
    PieceImpl[] getRack();

    /**
     * Selects a piece from the rack for future placement on the grid.
     * If another piece was previously selected, it will be overwritten.
     * 
     * @param p the {@link PieceImpl} chosen by the player
     */
    void selectPiece(PieceImpl p);

    /**
     * Returns the currently selected piece.
     * If no piece is selected, returns {@code null}.
     * 
     * @return the {@link PieceImpl} currently selected, or {@code null} if none is selected
     */
    PieceImpl getSelected();

    /**
     * Attempts to place the selected piece on the grid at the specified coordinates.
     * If the placement is valid, the grid is updated, points are awarded,
     * any full lines are cleared, and the piece is consumed from the rack.
     *
     *
     * @param r the target row
     * @param c the target column
     */
    void tryPlaceAt(int r, int c);

}
