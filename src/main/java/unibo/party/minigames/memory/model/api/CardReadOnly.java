package unibo.party.minigames.memory.model.api;

/**
 * Read-only view of a Memory card.
 * This interface is used by the UI, so the UI can read card data but cannot change the card state.
 */
public interface CardReadOnly {
    int getId();
    Symbol getSymbol();
    boolean isRevealed();
}
