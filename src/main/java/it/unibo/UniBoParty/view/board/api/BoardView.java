package it.unibo.uniboparty.view.board.api;

import it.unibo.uniboparty.controller.board.api.BoardController;

/**
 * Public API for the main board view.
 */
public interface BoardView {

    /**
     * Updates the logical position of the player and refreshes the view.
     * 
     * @param position index of the cell where the player is currently located
     */
    void setPlayerPosition(int position);

    /**
     * @return the controller used by this view
     */
    BoardController getController();
}
