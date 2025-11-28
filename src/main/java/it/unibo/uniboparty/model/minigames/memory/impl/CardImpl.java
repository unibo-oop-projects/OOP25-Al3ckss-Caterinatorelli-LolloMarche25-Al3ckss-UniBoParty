package it.unibo.uniboparty.model.minigames.memory.impl;

import it.unibo.uniboparty.model.minigames.memory.api.Card;
import it.unibo.uniboparty.model.minigames.memory.api.Symbol;

/**
 * Default implememtation of the {@link Card} interface.
 * 
 * <p>
 * A card has:
 * <ul>
 *   <li>a unique id,</li>
 *   <li>a symbol (used to check pairs),</li>
 *   <li>a revealed / hidden state.</li>
 * </ul>
 * </p>
 */
public class CardImpl implements Card {

    private final int id;
    private final Symbol symbol;
    private boolean revealed;

    /**
     * Creates a new card, initially hidden.
     * 
     * @param id the card identifier
     * @param symbol the card symbol
     */
    public CardImpl(final int id, final Symbol symbol) {
        this.id = id;
        this.symbol = symbol;
        this.revealed = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getId() {
        return this.id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Symbol getSymbol() {
        return this.symbol;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isRevealed() {
        return this.revealed;
    }    

    /**
     * {@inheritDoc}
     */
    @Override
    public void reveal() {
        this.revealed = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void hide() {
        this.revealed = false;        
    }

    @Override
    public String toString() {
        return "Card {id=" + this.id
         + ", symbol=" + this.symbol 
         + ", revealed=" + this.revealed + "}";
    }
}
