package it.unibo.uniboparty.model.minigames.memory.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.unibo.uniboparty.model.minigames.memory.api.Card;
import it.unibo.uniboparty.model.minigames.memory.api.MemoryDeckFactory;
import it.unibo.uniboparty.model.minigames.memory.api.Symbol;

/**
 * Default implementation of the {@link MemoryDeckFactory} interface.
 * 
 * <P>
 * This class creates a Memory deck by:
 * <ul>
 *   <li>selecting the required number of symbols,</li>
 *   <li>creating two cards for each symbol (forming pairs),</li>
 *   <li>assigning an incremental id to each card,</li>
 *   <li>shuffling the whole deck before returning it.</li>
 * </ul>
 * The returned deck is immutable.
 * </p>
 */
public final class MemoryDeckFactoryImpl implements MemoryDeckFactory {

    /**
     * {@inheritDoc}
     */
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
