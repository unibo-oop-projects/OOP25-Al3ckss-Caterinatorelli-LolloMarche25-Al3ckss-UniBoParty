package it.unibo.uniboparty.model.minigames.memory.impl;

import java.util.List;

import it.unibo.uniboparty.model.minigames.memory.api.CardReadOnly;
import it.unibo.uniboparty.model.minigames.memory.api.MemoryGameReadOnlyState;

/**
 * Immutable snapshot of the current Memory game state.
 * 
 * <p>
 * The Controller and the View use this class to read information about the match without modifying the actual Model. 
 * All fields are final, and a defensive copy of the card list is stored.
 * </p>
 */
public final class MemoryGameState implements MemoryGameReadOnlyState {
    
    private final int matchedPairs;
    private final int totalPairs;
    private final boolean gameOver;
    private final boolean waitingSecondFlip;
    private final List<CardReadOnly> cards;
    private final String message;
    private final int moves;

    /**
     * Creates a new immutable game state snapshot.
     * 
     * @param matchedPairs number of pairs already found
     * @param totalPairs total number of pairs in the game
     * @param gameOver whether the game is finished
     * @param waitingSecondFlip {@code true} if only one card is currently revealed
     * @param cards list of all cards (a defensive copy will be made)
     * @param message short message describing the current situation
     * @param moves number of moves performed by the player
     */
    public MemoryGameState(
        final int matchedPairs,
        final int totalPairs,
        final boolean gameOver,
        final boolean waitingSecondFlip,
        final List<CardReadOnly> cards,
        final String message,
        final int moves
    ) {
        this.matchedPairs = matchedPairs;
        this.totalPairs = totalPairs;
        this.gameOver = gameOver;
        this.waitingSecondFlip = waitingSecondFlip;
        this.cards = List.copyOf(cards);
        this.message = message;
        this.moves = moves;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMatchedPairs() {
        return this.matchedPairs;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getTotalPairs() {
        return this.totalPairs;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isGameOver() {
        return this.gameOver;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isWaitingSecondFlip() {
        return this.waitingSecondFlip;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CardReadOnly> getCards() {
        return this.cards;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMessage() {
        return this.message;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMoves() {
        return this.moves;
    }

    @Override
    public String toString() {
        return "MemoryGameState {" +
              "matchedPairs=" + matchedPairs + 
              ", totalPairs=" + totalPairs + 
              ", gameOver=" + gameOver + 
              ", waitingSecondFlip=" + waitingSecondFlip + 
              ", cards=" + cards.size() + " cards" + 
              ", message=" + message + '\'' + 
              ", moves=" + moves +
              '}';
    }
}
