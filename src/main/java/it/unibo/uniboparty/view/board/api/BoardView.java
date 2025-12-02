package it.unibo.uniboparty.view.board.api;

import it.unibo.uniboparty.controller.board.api.BoardController;

/**
 * Public API for the main board view.
 */
public interface BoardView {

    /**
     * Updates the logical position of the player,
     * refreshes the board UI,
     * and automatically launches the intro screen of the corresponding
     * minigame if the player lands on a MINIGAME cell.
     * 
     * <p>
     * This method is typically called by the part of the application that manages
     * player turns and movement (e.g., the PlayersManager).
     * </p>
     * 
     * @param position index of the destination cell
     * @throws IllegalArgumentException if {@code position} is outside the board bounds.
     */
    void setPlayerPosition(int position);

    /**
     * @return the controller used by this view
     */
    BoardController getController();
}
