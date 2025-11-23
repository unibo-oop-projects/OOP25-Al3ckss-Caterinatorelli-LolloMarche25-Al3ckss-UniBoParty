package unibo.party.minigames.memory.model.api;

import java.util.List;

/**
 * Immutable and read-only snapshot of the game state.
 * Used by the controller or UI to read information without modifying the model.
 */
public interface MemoryGameReadOnlyState {
    int getMatchedPairs();
    int getTotalPairs();
    boolean isGameOver();
    boolean isWaitingSecondFlip();
    List<CardReadOnly> getCards();
    String getMessage();
    int getMoves();
}
