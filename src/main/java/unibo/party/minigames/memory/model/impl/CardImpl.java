package unibo.party.minigames.memory.model.impl;

import unibo.party.minigames.memory.model.api.Card;
import unibo.party.minigames.memory.model.api.Symbol;

public class CardImpl implements Card {

    private final int id;
    private final Symbol symbol;
    private boolean revealed;

    public CardImpl(final int id, final Symbol symbol) {
        this.id = id;
        this.symbol = symbol;
        this.revealed = false;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public Symbol getSymbol() {
        return this.symbol;
    }

    @Override
    public boolean isRevealed() {
        return this.revealed;
    }    

    @Override
    public void reveal() {
        this.revealed = true;
    }

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
