package it.unibo.uniboparty.model.minigames.memory.api;

import java.util.List;

/**
 * Main modl API for the Memory game.
 * 
 * <p>
 * This interface defines the core logic of the game:
 * flipping cards, checking for matches, resolving mismatches, and tracking the overall game progress.
 * </p>
 */
public interface MemoryGameModel {
    
    /**
     * Tries to reveal the card at the given index.
     * 
     * <p>
     * The move is valid only if the card is currently hidden and the game is not waiting for a mismatch to be resolved.
     * </p>
     * 
     * @param index the position of the card inside the deck
     * @return {@code true} if the card has been flipped successfully; {@code false} if the move is not allowed
     */
    boolean flipCard(int index);
    
    /**
     * Resolves the last mismatch, if any.
     * 
     * <p>
     * When two flipped cards do not match, the model keeps them revealed until the controller calls this method, which hides them again and closes the turn.
     * </p>
     */
    void resolveMismatch();

    /**
     * @return {@code true} if two non-matching cards are currently revealed
     */
    boolean hasMismatchPending();

    /**
     * @return {@code true} if all pairs have been found
     */
    boolean isGameOver();

    /**
     * @return an immutable list of all cards on the table.
     */
    List<Card> getCards();

    /**
     * Returns a read-only snapshot of the current game state.
     * 
     * <p>
     * Useful for the controller or the UI to read the game data without modifying the model
     * </p>
     * 
     * @return the current {@link MemoryGameReadOnlyState}
     */
    MemoryGameReadOnlyState getGameState();
}
