package it.unibo.uniboparty.controller.minigames.memory.api;

/**
 * Controller API for the Memory game.
 * 
 * <p>
 * The View calls the methods of this interface to notify user actions, such as clicking on a card.
 * </p>
 */
public interface MemoryGameController {
    
    /**
     * Called when the user clicks on a card in the grid.
     * 
     * @param r the row index of the clicked card
     * @param c the column index of the clicked card
     */
    void onCardClicked(int r, int c);
}
