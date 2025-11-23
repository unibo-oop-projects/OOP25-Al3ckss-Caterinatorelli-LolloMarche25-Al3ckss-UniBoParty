package unibo.party.minigames.memory.model.api;

import java.util.List;

/** 
 * Interface for creating Memory card decks.
*/
public interface MemoryDeckFactory {
    
    /**
     * Creates and shuffles a deck with the given number of pairs.
     * 
     * @param numberOffPairs the number of card pairs to generate.
     * @return an immutable list of cards for the game.
     * @throws IllegalArgumentException if the number of pairs is not valid.
     */
    List<Card> createShuffledDeck(int numberOfPairs);
}
