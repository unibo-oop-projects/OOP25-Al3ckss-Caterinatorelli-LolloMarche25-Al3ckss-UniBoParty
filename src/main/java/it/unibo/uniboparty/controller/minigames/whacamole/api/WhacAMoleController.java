package it.unibo.uniboparty.controller.minigames.whacamole.api;

import it.unibo.uniboparty.model.minigames.whacamole.WhacAMoleGameState;

/**
 * Controller interface for the Whac-A-Mole game.
 * 
 * <p>
 * The Controller stays between the View and the Model:
 * it receives interactions from the player, updates the game logic, and provides read-only game state information to the View.
 * </p>
 */
public interface WhacAMoleController {

    /**
     * Starts a new game.
     * 
     * <p>
     * Implementations may also create and start an internal timer to periodically update the game logic.
     * </p>
     */
    void startGame();

    /**
     * Advances the game logic by the given amount of time.
     * 
     * @param elapsedMillis number of milliseconds passed since last tick.
     */
    void updateGameLogic(long elapsedMillis);

    /**
     * Called when the player clicks on a specific hole.
     * 
     * @param index the index of the hole clicked by the player.
     */
    void onHoleClicked(int index);

    /**
     * Returns the current game state snapshot for the View.
     * 
     * <p>
     * The View uses this method to update the UI.
     * </p>
     * 
     * @return the current {@link WhacAMoleGameState}.
     */
    WhacAMoleGameState getState();

    /**
     * Indicates whether the current visible object is a bomb.
     * 
     * @return {@code true} if the visible object is a bomb, {@code false} if it is a mole or nothing is visible
     */
    boolean isCurrentObjectABomb();

    /**
     * Stops any internal game loop or timer once the game is over.
     * 
     * <p>
     * This method is called after each update or UI refresh.
     * </p>
     */
    void stopIfGameOver();

    /**
     * Encoded result of the current match.
     *
     * <ul>
     *   <li>2 = game still in progress</li>
     *   <li>1 = game won</li>
     *   <li>0 = game lost</li>
     * </ul>
     *
     * @return the result code
     */
    int getResultCode();
}
