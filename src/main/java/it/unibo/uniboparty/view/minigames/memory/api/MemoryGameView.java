package it.unibo.uniboparty.view.minigames.memory.api;

import it.unibo.uniboparty.controller.minigames.memory.api.MemoryGameController;
import it.unibo.uniboparty.model.minigames.memory.api.MemoryGameReadOnlyState;

/**
 * View API for the Memory game.
 * 
 * <p>
 * The View is responsible for showing the cards on the screen and updating the UI whenever the Model changes. It does not contain game logic.
 * </p>
 */
public interface MemoryGameView {
    
    /**
     * Sets the controller that will receive user actions.
     * 
     * <p>
     * This method is called once in the View constructor.
     * </p> 
     * 
     * @param controller the controller that handles user interactions
     */
    void setController(MemoryGameController controller);

    /**
     * Redraws the UI using the given read-only game state.
     * 
     * @param state the current snapshot of the game state
     */
    void render(MemoryGameReadOnlyState state);

    /**
     * Updates the info panel (for example: timer or number of moves).
     * 
     * @param secondsElapsed the number of seconds since the game started
     * @param moves the number of moves made so far
     */
    void updateInfoPanel(int secondsElapsed, int moves);

    /**
     * Enables/disables all buttons/cells in the grid.
     * 
     * <p>
     * This is used during mismatch delays, to avoid clicks whent two revealed cards are momentarily shown to the user.
     * </p>
     * 
     * @param disabled true to disable all buttons, false to enable them
     */
    void setAllButtonsDisabled(boolean disabled);
}
