package unibo.party.minigames.memory.model.api;

import java.util.List;

/**
 * Snapshot immutabile e read-only dello stato del gioco.
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
