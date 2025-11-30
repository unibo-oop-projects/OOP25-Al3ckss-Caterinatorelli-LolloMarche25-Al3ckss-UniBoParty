package it.unibo.uniboparty.model.player.impl;

import it.unibo.uniboparty.model.player.api.PlayerManager;

/**
 * Default implementation of the PlayerManager.
 *
 * <p>
 * Manages multiple players, their positions on the board,
 * their scores, and the turn order.
 * </p>
 */
public final class PlayerManagerImpl implements PlayerManager {

    private final int numberOfPlayers;
    private final int[] positions;
    private final int[] scores;

    private int currentPlayerIndex;

    /**
     * Creates a new PlayerManager with the given number of players.
     *
     * @param numberOfPlayers the number of players in the match
     */
    public PlayerManagerImpl(final int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
        this.positions = new int[numberOfPlayers];
        this.scores = new int[numberOfPlayers];
        this.currentPlayerIndex = 0;
    }

    @Override
    public int getNumberOfPlayers() {
        return this.numberOfPlayers;
    }

    @Override
    public int getCurrentPlayerIndex() {
        return this.currentPlayerIndex;
    }

    @Override
    public int getCurrentPlayerPosition() {
        return this.positions[this.currentPlayerIndex];
    }

    @Override
    public int getPlayerPosition(final int playerIndex) {
        return this.positions[playerIndex];
    }

    @Override
    public int moveCurrentPlayer(final int steps, final int boardSize) {
        final int oldPos = this.positions[this.currentPlayerIndex];
        int newPos = oldPos + steps;

        if (newPos >= boardSize) {
            newPos = boardSize - 1;
        }

        this.positions[this.currentPlayerIndex] = newPos;
        return newPos;
    }

    @Override
    public void nextPlayer() {
        this.currentPlayerIndex = (this.currentPlayerIndex + 1) % this.numberOfPlayers;
    }

    @Override
    public void addScore(final int playerIndex, final int amount) {
        this.scores[playerIndex] += amount;
    }

    @Override
    public int getScore(final int playerIndex) {
        return this.scores[playerIndex];
    }
}
