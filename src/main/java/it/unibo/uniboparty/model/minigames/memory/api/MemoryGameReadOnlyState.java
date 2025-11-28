package it.unibo.uniboparty.model.minigames.memory.api;

import java.util.List;

/**
 * Immutable and read-only snapshot of the Memory game state.
 * 
 * <p>
 * The Controller and the View use this interface to safely read information about the current match without modifying the Model.
 * </p>
 */
public interface MemoryGameReadOnlyState {

    /**
     * @return the number of matched pairs
     */
    int getMatchedPairs();

    /**
     * @return the total number of pairs
     */
    int getTotalPairs();

    /**
     * @return {@code true} if the game is over
     */
    boolean isGameOver();

    /**
     * @return {@code true} if only one card is currently revealed
     */
    boolean isWaitingSecondFlip();

    /**
     * @return an unmodifiable list of {@link CardReadOnly}
     */
    List<CardReadOnly> getCards();

    /**
     * @return a human-friendly message for the player
     */
    String getMessage();

    /**
     * @return the number of moves
     */
    int getMoves();
}
