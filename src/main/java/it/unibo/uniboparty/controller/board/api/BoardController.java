package it.unibo.uniboparty.controller.board.api;

import it.unibo.uniboparty.model.board.CellType;
import it.unibo.uniboparty.utilities.MinigameId;

/**
 * High-level API for interacting with the main board.
 *
 * <p>
 * The controller provides read-only access to the board structure and exposes
 * utility methods used by higher-level modules (such as the player manager or
 * the graphical board view).
 * </p>
 */
public interface BoardController {

    /**
     * Returns the total number of cells contained in the board.
     *
     * @return the size of the board
     */
    int getBoardSize();

    /**
     * Returns the logical type of the cell at the specified index.
     *
     * @param index the index of the cell (0..size-1)
     * @return the {@link CellType} at that position
     */
    CellType getCellTypeAt(int index);

    /**
     * Returns the identifier of the minigame assigned to the specified cell,
     * or {@code null} if the cell does not correspond to a minigame.
     *
     * <p>
     * This method performs no logic beyond retrieving the configured data from
     * the model.
     * </p>
     *
     * @param index the index of the cell
     * @return the {@link MinigameId} of the cell, or {@code null} if none
     */
    MinigameId getMinigameAt(int index);

    /**
     * Checks whether landing on the given cell triggers a minigame.
     *
     * <p>
     * This method <strong>does not start any minigame</strong> and contains
     * no UI logic. It merely inspects the model and returns the
     * {@link MinigameId} associated with the cell if the cell type is
     * {@code MINIGAME}.
     * </p>
     *
     * <p>
     * It is the responsibility of the caller (typically the {@code BoardView}
     * or a player/turn manager) to decide how to handle the returned value —
     * for example, by opening a minigame intro screen.
     * </p>
     *
     * @param position the index of the cell where the player has landed
     * @return the {@link MinigameId}, or {@code null} if the cell does not
     *         trigger a minigame
     */
    MinigameId onPlayerLanded(int position);
}
