package unibo.party.minigames.memory.controller.api;

import unibo.party.minigames.memory.view.api.MemoryGameView;

/**
 * Controller API for the Memory game.
 * The View calls these methods to notify user actions.
 */
public interface MemoryGameController {
    
    /**
     * Handles a click on the card at position (r, c) in the grid.
     * @param r the row index of the clicked card.
     * @param c the column index of the clicked card.
     */
    void onCardClicked(int r, int c);

    /**
     * @return the View connected to this controller, so it can be added to the application's Scene.
     */
    MemoryGameView getView();
}
