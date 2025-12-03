package it.unibo.uniboparty.model.player.api;

/**
 * API for managing multiple players in the board game.
 *
 * <p>
 * Tracks positions, scores, and turn order.
 * Provides basic operations needed by the game controller.
 * </p>
 */
public interface PlayerManager {

    int getNumberOfPlayers();

    int getCurrentPlayerIndex();

    int getCurrentPlayerPosition();

    int getPlayerPosition(int playerIndex);

    void nextPlayer();

    void addScore(int playerIndex, int amount);

    int getScore(int playerIndex);

    /**
     * Executes the entire turn logic for the current player.
     *
     * @param diceRoll the number of steps obtained from the dice throw
     * @return a {@link TurnResult} object containing all the information
     *         about the performed turn (final position, applied effects, minigame to launch, etc.)
     */
    TurnResult playTurn(int diceRoll);
}
