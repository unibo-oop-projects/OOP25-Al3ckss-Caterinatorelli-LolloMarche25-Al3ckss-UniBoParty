package it.unibo.uniboparty.controller.player.api;

/**
 * Interface of the Gameplay main controller.
 *
 * <p>
 * Moves players, starts a minigame depending on the position
 * </p>
 */
@FunctionalInterface
public interface GameplayController {

    /**
     * Handles turn management.
     *
     * @param steps number of steps on the board
     */
    void onDiceRolled(int steps);

}
