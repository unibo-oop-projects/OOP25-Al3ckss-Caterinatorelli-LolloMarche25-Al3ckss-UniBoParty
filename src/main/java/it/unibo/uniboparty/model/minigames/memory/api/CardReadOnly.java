package it.unibo.uniboparty.model.minigames.memory.api;

/**
 * Read-only view of a Memory card.
 * 
 * <p>
 * This interface is used by the View, so the UI can read card information (id, symbol, revealed state) without being allowed to modify it.unibo.uniboparty.model.minigames.memory.apiThe actual card state can only be changed through the writable {@LINK Card} interface.
 * </p>
 */
public interface CardReadOnly {

    /**
     * @return the card id
     */
    int getId();

    /**
     * @return the card symbol
     */
    Symbol getSymbol();

    /**
     * @return {@code true} if the card is revealed, {@code false} if it is hidden
     */
    boolean isRevealed();
}
