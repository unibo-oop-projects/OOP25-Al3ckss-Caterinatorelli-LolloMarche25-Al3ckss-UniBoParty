package unibo.party.minigames.memory.model.api;

/**
 * Interface that represents a single card in the Memory game.
 * It provides basic methods to know the card data and to change its state (covered or revealed).
 */
public interface Card extends CardReadOnly {
    
    /** 
     * @return the unique identifier of the card.
     * This id helps to distinguish cards in the deck. 
     */
    int getId();

    /** 
     * @return the symbol shown by the card when it is revealed.
     * Cards with the same symbol form a pair. 
     */
    Symbol getSymbol();

    /** 
     * @return true if the card is currently revealed (face up), false if it is covered. 
     */
    boolean isRevealed();

    /**
     * Sets the card state to revealed.
     * Used when the player clicks on the card.
     */
    void reveal();

    /** 
     * Sets the card back to hidden (face down).
     * Usually called when two cards do not match.
     */
    void hide();
}
