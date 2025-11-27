package it.unibo.uniboparty.controller.board.api;

import it.unibo.uniboparty.model.board.CellType;
import it.unibo.uniboparty.utilities.MinigameId;

/**
 * High-level API for interacting with the main board.
 */
public interface BoardController {
    
    /**
     * @return the number of cells of the board
     */
    int getBoardSize();

    /**
     * @param index the position of the cell
     * @return the type of the cell at the given index
     */
    CellType getCellTypeAt(int index);

    /**
     * @param index the position of the cel
     * @return the minigame id associated with that cell, or {@code null} if none
     */
    MinigameId getMinigameAt(int index);

    /**
     * Called when the player lands on a given position
     * 
     * @param position the index of the cell where the player has landed
     * @return the minigame id if the cell triggers a minigame, {@code null} oterwise
     */
    MinigameId onPlayerLanded(int position);
}
