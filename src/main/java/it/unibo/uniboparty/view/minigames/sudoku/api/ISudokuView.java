package it.unibo.uniboparty.view.minigames.sudoku.api;

import java.awt.event.ActionListener;

/**
 * Interface defining the behavior of the Sudoku User Interface.
 *
 * <p>
 * It allows the Controller to interact with the GUI elements, such as
 * updating the grid, handling button events, and showing dialogs.
 */
public interface ISudokuView {

    /**
     * Registers a listener for the numeric buttons (keypad 1-9).
     *
     * @param listener The action to perform when a number is selected.
     * @param number   The specific number (1-9) associated with the button.
     */
    void addNumberButtonListener(ActionListener listener, int number);

    /**
     * Registers a listener for the grid tiles.
     *
     * @param listener The action to perform when a tile is clicked.
     * @param r        The row index of the tile.
     * @param c        The column index of the tile.
     */
    void addTileListener(ActionListener listener, int r, int c);

    /**
     * Updates the text of a specific tile in the grid.
     * Used when the user correctly guesses a number.
     *
     * @param r    The row index.
     * @param c    The column index.
     * @param text The number to display.
     */
    void setTileText(int r, int c, String text);

    /**
     * Configures a tile as a fixed initial value.
     * Usually applies specific styling (e.g., bold font, different background) to indicate
     * it cannot be changed.
     *
     * @param r    The row index.
     * @param c    The column index.
     * @param text The initial number to display.
     */
    void setTileFixed(int r, int c, String text);

    /**
     * Updates the label displaying the current error count.
     *
     * @param errors The new error count to display.
     */
    void updateErrorLabel(int errors);

    /**
     * Displays a victory pop-up to the user and ends the game.
     */
    void showGameWon();

    /**
     * Displays a "Game Over" pop-up to the user and ends the game.
     */
    void showGameOver();
}
