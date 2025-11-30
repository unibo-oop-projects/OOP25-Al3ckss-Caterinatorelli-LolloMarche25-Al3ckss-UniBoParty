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

    /**
     * Returns the total number of players in the match.
     *
     * @return the number of players
     */
    int getNumberOfPlayers();

    /**
     * Returns the index of the player whose turn is currently active.
     *
     * @return the current player index
     */
    int getCurrentPlayerIndex();

    /**
     * Returns the board position of the current player.
     *
     * @return position of the current player
     */
    int getCurrentPlayerPosition();

    /**
     * Returns the board position of a specific player.
     *
     * @param playerIndex index of the player
     * @return the player's position
     */
    int getPlayerPosition(int playerIndex);

    /**
     * Moves the current player by the given number of steps.
     * Position is capped to the board size.
     *
     * @param steps     how many cells the player moves
     * @param boardSize total number of board cells
     * @return the new position of the current player
     */
    int moveCurrentPlayer(int steps, int boardSize);

    /**
     * Advances the turn to the next player.
     */
    void nextPlayer();

    /**
     * Adds a score bonus to the given player.
     *
     * @param playerIndex player index
     * @param amount      amount of points to add (may be negative)
     */
    void addScore(int playerIndex, int amount);

    /**
     * Gets the current score of a specific player.
     *
     * @param playerIndex index of the player
     * @return the player's score
     */
    int getScore(int playerIndex);
}
