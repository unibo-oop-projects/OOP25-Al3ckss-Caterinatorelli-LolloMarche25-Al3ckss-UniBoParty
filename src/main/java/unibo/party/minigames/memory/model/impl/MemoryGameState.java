package unibo.party.minigames.memory.model.impl;

import java.util.List;

import unibo.party.minigames.memory.model.api.CardReadOnly;
import unibo.party.minigames.memory.model.api.MemoryGameReadOnlyState;

/**
 * Immutable snapshot of the current Memory game state.
 * Mean to be read by the controller or the UI,
 * without giving access to modify the real model.
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
     * A defensive copy of the card list is made to keep it read-only.
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

    @Override
    public int getMatchedPairs() {
        return this.matchedPairs;
    }

    @Override
    public int getTotalPairs() {
        return this.totalPairs;
    }

    @Override
    public boolean isGameOver() {
        return this.gameOver;
    }

    @Override
    public boolean isWaitingSecondFlip() {
        return this.waitingSecondFlip;
    }

    @Override
    public List<CardReadOnly> getCards() {
        return this.cards;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

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
