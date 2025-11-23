package unibo.party.minigames.memory.model.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import unibo.party.minigames.memory.model.api.Card;
import unibo.party.minigames.memory.model.api.Symbol;
import unibo.party.minigames.memory.model.api.MemoryDeckFactory;

public final class MemoryDeckFactoryImpl implements MemoryDeckFactory{

    @Override
    public List<Card> createShuffledDeck(int numberOfPairs) {
        if(numberOfPairs < 1) {
            throw new IllegalArgumentException("The number of pairs must be at least 1");
        }

        final Symbol[] symbols = Symbol.values();

        // Make sure we have enough symbols to create the required number of pairs
        if(numberOfPairs > symbols.length) {
            throw new IllegalArgumentException("Too many pairs requested compared to available symbols");
        }

        final List<Card> deck = new ArrayList<>(numberOfPairs * 2);
        int idCounter = 0;

        // Create two cards with the same symbol to form a pair
        for(int i = 0; i < numberOfPairs; i++) {
            final Symbol s = symbols[i];
            deck.add(new CardImpl(idCounter++, s));
            deck.add(new CardImpl(idCounter++, s));
        }

        Collections.shuffle(deck);

        // Return an immutable copy so the deck cannot be modified from outside
        return List.copyOf(deck);
    }
}
