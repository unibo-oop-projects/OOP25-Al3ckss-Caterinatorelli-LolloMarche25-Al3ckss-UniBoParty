package unibo.party.board.controller;

import unibo.party.board.model.BoardModel;
import unibo.party.board.model.CellType;
import unibo.party.minigames.MinigameId;

/**
 * Controller for the main board.
 *
 * <p>
 * It provides read-only access to the {@link BoardModel} and
 * high-level operations related to the player's position on the board.
 * </p>
 */
public final class BoardController {

    private final BoardModel model;

    /**
     * Creates a controller for the given board model.
     *
     * @param model the board model to be used by this controller
     */
    public BoardController(final BoardModel model) {
        this.model = model;
    }

    /**
     * Returns the size of the board, expressed as the number of cells.
     *
     * @return the number of cells of the board
     */
    public int getBoardSize() {
        return this.model.getSize();
    }

    /**
     * Returns the type of the cell at the given index.
     *
     * @param index the position of the cell
     * @return the {@link CellType} at the given index
     */
    public CellType getCellTypeAt(final int index) {
        return this.model.getCellAt(index).getType();
    }

    /**
     * Returns the minigame associated with the cell at the given index,
     * if any.
     *
     * @param index the position of the cell
     * @return the {@link MinigameId} of the cell, or {@code null} if none
     */
    public MinigameId getMinigameAt(final int index) {
        return this.model.getCellAt(index).getMinigameId();
    }

    /**
     * Handles the event where the player lands on the given position.
     * If the cell is of type {@link CellType#MINIGAME}, the corresponding
     * minigame is returned.
     *
     * @param position the index of the cell where the player has landed
     * @return the {@link MinigameId} if the cell is a minigame cell,
     *         {@code null} otherwise
     */
    public MinigameId onPlayerLanded(final int position) {
        final CellType type = this.getCellTypeAt(position);
        if (type == CellType.MINIGAME) {
            return this.getMinigameAt(position);
        }
        return null;
    }
}
