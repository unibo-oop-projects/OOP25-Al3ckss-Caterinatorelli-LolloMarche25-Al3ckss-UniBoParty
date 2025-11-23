package unibo.party.minigames.memory.view.api;

import unibo.party.minigames.memory.controller.api.MemoryGameController;
import unibo.party.minigames.memory.model.api.MemoryGameReadOnlyState;

/**
 * View API for the Memory game.
 * The view shows the game to the user and updates the UI based on the model state.
 */
public interface MemoryGameView {
    
    /** 
     * Connects the controller to this view.
     * The controller will handle all user interactions. 
     */
    void setController(MemoryGameController controller);

    /**
     * Redraws the UI based on the current game state.
     * Called by the controller after every move.
     */
    void render(MemoryGameReadOnlyState state);

    /**
     * Updates the info panel (for example: timer or number of moves).
     * @param secondsElapsed the number of seconds since the game started
     * @param moves the number of moves made so far
     */
    void updateInfoPanel(int secondsElapsed, int moves);

    /**
     * Enables/disables all buttons/cells in the grid.
     * Useful when the game needs to wait before resolving a mismatch.
     */
    void setAllButtonsDisabled(boolean disabled);
}
