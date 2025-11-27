package it.unibo.uniboparty.model.minigames.sudoku.api;

/**
 * Interface defining logic and data managment for the Sudoku game.
 *
 * <p>
 * It's responsible for maintaining the state of the game board,
 * validating player's moves and keeping track on the number of errors.
 */
public interface ISudokuModel {

    /**
     * Gets the character present in the initial puzzle at the specified coordinates.
     *
     * @param r The row index (0-8).
     * @param c The column index (0-8).
     *
     * @return A String representing the value (e.g., "5") or a placeholder (e.g., "-") if empty.
     */
    String getInitialPuzzleAt(int r, int c);

    /**
     * Gets the current number of errors committed by the player.
     *
     * @return The integer count of errors.
     */
    int getErrorCount();

    /**
     * Validates a move attempted by the player.
     *
     * <p>
     * If the move is correct (matches the solution), the model updates the internal user grid state.
     * If the move is incorrect, the error counter is incremented.
     *
     *
     * @param r      The row index of the cell.
     * @param c      The column index of the cell.
     * @param number The number (1-9) input by the user.
     *
     * @return true if the number matches the solution at that position, false otherwise.
     */

    boolean isMoveCorrect(int r, int c, int number);

    /**
     * Checks if a specific cell is part of the initial puzzle configuration.
     * Fixed cells cannot be modified by the user.
     *
     * @param r The row index.
     * @param c The column index.
     *
     * @return true if the cell was pre-filled in the initial puzzle, false if it is editable.
     */
    boolean isCellFixed(int r, int c);

    /**
     * Checks if the game has been successfully completed.
     *
     * <p>
     * The game is won when the user's grid exactly matches the solution grid.
     *
     *
     * @return true if the puzzle is solved, false otherwise.
     */
    boolean isGameWon();
}
