package unibo.party.minigames.memory.model.api;

/**
 * Vista read-only di una carta per la UI.
 */
public interface CardReadOnly {
    int getId();
    Symbol getSymbol();
    boolean isRevealed();
}
