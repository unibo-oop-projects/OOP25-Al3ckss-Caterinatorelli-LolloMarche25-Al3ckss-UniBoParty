package unibo.party.minigames.memory.model.api;

import java.util.List;

/**
 * Main model API for hte Memory game.
 * It contains the core logic: flipping cards, checking matches, and tracking the game progress.
 */
public interface MemoryGameModel {
    
    /**
     * Reveals the card at given index.
     * @param index the position of the card in the deck.
     * @return true if the move is valid and the card is flipped.
     * Returns false if the move cannot be done (for example if the card is already revealed).
     */
    boolean flipCard(int index);
    
    /**
     * If the last turn produced a mismatch that is still waiting, this method hides the two cards again and closes the turn.
     */
    void resolveMismatch();

    /**
     * @return true if there is a mismatch waiting to be resolved.
     */
    boolean hasMismatchPending();

    /**
     * @return true if all pairs have been found and the game is finished.
     */
    boolean isGameOver();

    /**
     * @return an immutable list of all cards on the table.
     * The caller cannot modify the cards directly.
     */
    List<Card> getCards();

    /**
     * Returns a snapshot of the current game state.
     * Useful for the controller or UI to read data safely.
     */
    MemoryGameReadOnlyState getGameState();
}
