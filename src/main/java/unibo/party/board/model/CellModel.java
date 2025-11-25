package unibo.party.board.model;

import unibo.party.minigames.MinigameId;

/**
 * Immutable model for a single cell on the main board.
 * A cell has a {@link CellType} and may optionally hold a minigame id.
 */
public final class CellModel {

    private final CellType type;
    /**
     * The id of the minigame in this cell, or {@code null} if there is none.
     */
    private final MinigameId minigameId;

    /**
     * Creates a new cell with the given type and optional minigame.
     *
     * @param type       the type of the cell
     * @param minigameId the id of the minigame in this cell, may be {@code null}
     */
    public CellModel(final CellType type, final MinigameId minigameId) {
        this.type = type;
        this.minigameId = minigameId;
    }

    /**
     * Returns the type of this cell.
     *
     * @return the cell type
     */
    public CellType getType() {
        return this.type;
    }

    /**
     * Returns the minigame id associated with this cell.
     *
     * @return the minigame id, or {@code null} if this cell has no minigame
     */
    public MinigameId getMinigameId() {
        return this.minigameId;
    }
}
