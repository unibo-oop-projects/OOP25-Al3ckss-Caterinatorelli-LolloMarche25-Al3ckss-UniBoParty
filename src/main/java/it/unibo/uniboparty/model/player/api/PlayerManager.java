package it.unibo.uniboparty.model.player.api;

import java.util.List;

/**
 * API for managing multiple players in the board game.
 */
public interface PlayerManager {

    /**
     * @return the number of players in the match
     */
    int getNumberOfPlayers();

    /**
     * @return the index of the current player
     */
    int getCurrentPlayerIndex();

    /**
     * @return the current {@link Player}
     */
    Player getCurrentPlayer();

    /**
     * @return an unmodifiable list of all players
     */
    List<Player> getPlayers();

    /**
     * @return the position of the current player
     */
    int getCurrentPlayerPosition();

    /**
     * @param playerIndex the index of the player
     * @return the position of the specified player
     */
    int getPlayerPosition(int playerIndex);

    /**
     * Moves the turn to the next player.
     */
    void nextPlayer();

    /**
     * @param playerIndex the index of the player
     * @param amount the score amount to add
     */
    void addScore(int playerIndex, int amount);

    /**
     * @param playerIndex the index of the player
     * @return the score of the specified player
     */
    int getScore(int playerIndex);

    /**
     * Performs the player's turn by applying the dice roll result.
     *
     * @param diceRoll the dice result
     * @return a {@link TurnResult} describing the turn outcome
     */
    TurnResult playTurn(int diceRoll);

    /**
     * Applies the result of a minigame to the specified player.
     *
     * <p>
     * This method moves the player forward or backward based on the minigame outcome:
     * <ul>
     *   <li>resultCode = 1 (win): player moves forward by 1 cell</li>
     *   <li>resultCode = 0 (loss): player moves backward by 1 cell</li>
     *   <li>resultCode = 2 (in progress): no action taken</li>
     * </ul>
     * </p>
     *
     * @param playerIndex the index of the player who played the minigame
     * @param minigameId the identifier of the minigame that was played
     * @param resultCode the result of the minigame (0, 1, or 2)
     */
    void applyMinigameResult(int playerIndex, it.unibo.uniboparty.utilities.MinigameId minigameId, int resultCode);
}
