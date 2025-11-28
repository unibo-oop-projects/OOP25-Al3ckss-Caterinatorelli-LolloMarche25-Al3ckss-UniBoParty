package it.unibo.uniboparty.model.minigames.memory.api;

import java.util.List;

/** 
 * Factory interface for creating Memory game decks.
 * 
 * <p>
 * A deck is composed of pairs of cards with matching symbols.
 * Implementations of this interface are responsible for:
 * <ul>
 *   <li>creating all the card pairs,</li>
 *   <li>duplicating symbols properly,</li>
 *   <li>shuffling the cards before returning the deck.</li>
 * </ul>
 * </p>
*/
public interface MemoryDeckFactory {
    
    /**
     * Creates and shuffles a deck with the given number of pairs.
     * 
     * @param numberOfPairs the number of card pairs to generate
     * @return an immutable list of cards for the game.
     * @throws IllegalArgumentException if the number of pairs is not valid.
     */
    List<Card> createShuffledDeck(int numberOfPairs);
}
