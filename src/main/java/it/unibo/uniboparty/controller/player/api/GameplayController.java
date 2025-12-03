package it.unibo.uniboparty.controller.player.api;

import it.unibo.uniboparty.utilities.MinigameId;

/**
 * Interface of the Gameplay main controller.
 *
 * <p>
 * Moves players, starts a minigame depending on the position
 * </p>
 */
public interface GameplayController {

    /**
     * Handles turn management.
     *
     * @param steps number of steps on the board
     */
    void onDiceRolled(int steps);

    /**
     * Handles minigame launching.
     *
     * @param id the id of the minigame the player landed on
     */
    void startMinigame(MinigameId id);
}
