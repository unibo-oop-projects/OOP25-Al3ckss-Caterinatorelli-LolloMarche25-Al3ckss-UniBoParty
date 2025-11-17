package unibo.party.minigames.memory.model.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import unibo.party.minigames.memory.model.api.Card;
import unibo.party.minigames.memory.model.api.Symbol;
import unibo.party.minigames.memory.model.api.MemoryDeckFactory;

/**
 * Implementazione della factory per creare mazzi di carte Memory.
 */
public final class MemoryDeckFactoryImpl implements MemoryDeckFactory{

    @Override
    public List<Card> createShuffledDeck(int numberOfPairs) {
        if(numberOfPairs < 1) {
            throw new IllegalArgumentException("Il numero di coppie deve essere almeno 1");
        }

        final Symbol[] symbols = Symbol.values();
        if(numberOfPairs > symbols.length) {
            throw new IllegalArgumentException("Troppi simboli richiesti rispetto a quelli disponibili");
        }

        final List<Card> deck = new ArrayList<>(numberOfPairs * 2);
        int idCounter = 0;

        // Genera le coppie di carte
        for(int i = 0; i < numberOfPairs; i++) {
            final Symbol s = symbols[i];
            deck.add(new CardImpl(idCounter++, s));
            deck.add(new CardImpl(idCounter++, s));
        }

        // Mischia le carte
        Collections.shuffle(deck);
        return List.copyOf(deck);
    }
}
