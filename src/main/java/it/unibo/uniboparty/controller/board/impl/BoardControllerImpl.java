package it.unibo.uniboparty.controller.board.impl;

import it.unibo.uniboparty.controller.board.api.BoardController;
import it.unibo.uniboparty.model.board.CellType;
import it.unibo.uniboparty.model.board.api.BoardModel;
import it.unibo.uniboparty.utilities.MinigameId;

/**
 * Default implementation of the {@link BoardController} interface
 *
 * <p>
 * It provides read-only access to the {@link BoardModel} and
 * high-level operations related to the player's position on the board.
 * </p>
 */
public final class BoardControllerImpl implements BoardController {

    private final BoardModel model;

    /**
     * Creates a controller for the given board model.
     *
     * @param model the board model to be used by this controller
     */
    public BoardControllerImpl(final BoardModel model) {
        this.model = model;
    }

    @Override
    public int getBoardSize() {
        return this.model.getSize();
    }

    @Override
    public CellType getCellTypeAt(final int index) {
        return this.model.getCellAt(index).getType();
    }

    @Override
    public MinigameId getMinigameAt(final int index) {
        return this.model.getCellAt(index).getMinigameId();
    }

    @Override
    public MinigameId onPlayerLanded(final int position) {
        final CellType type = this.getCellTypeAt(position);
        if (type == CellType.MINIGAME) {
            return this.getMinigameAt(position);
        }
        return null;
    }
}
