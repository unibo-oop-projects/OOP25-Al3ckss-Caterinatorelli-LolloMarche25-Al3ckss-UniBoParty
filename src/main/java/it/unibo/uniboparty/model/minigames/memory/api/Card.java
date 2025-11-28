package it.unibo.uniboparty.model.minigames.memory.api;

/**
 * Writable Memory card.
 * 
 * <p>
 * This interface extends {@link CardReadOnly} by adding the methods needed to modify the card state {reveal or hide}. 
 * The View usually interacts only with read-only interface, while the Controller and the Model use this writable version.
 * </p> 
 */
public interface Card extends CardReadOnly {
    
    /** 
     * {@inheritDoc} 
     */
    @Override
    int getId();

    /** 
     * {@inheritDoc}
     */
    @Override
    Symbol getSymbol();

    /** 
     * {@inheritDoc}
     */
    @Override
    boolean isRevealed();

    /**
     * Reveals the card (face up).
     * 
     * <p>
     * Typically called when the player selects the card.
     * </p>
     */
    void reveal();

    /** 
     * Hides the card again (face down).
     * 
     * <p>
     * Usually called when two revealed cards do not match.
     * </p>
     */
    void hide();
}
