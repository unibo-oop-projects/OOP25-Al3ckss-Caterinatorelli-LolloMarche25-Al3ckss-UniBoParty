package unibo.party.minigames.memory.model.api;

import java.util.List;

/** 
 * Interfaccia per la creazione di mazzi di carte Memory.
 * Permette implementazioni diverse (casuale, ordinato, ecc.)
*/
public interface MemoryDeckFactory {
    
    /**
     * Crea e mescola un mazzo di carte con il numero di coppie specificato.
     * 
     * @param numberOffPairs numero di coppie di carte da generare
     * @return lista immutabile di carte
     * @throws IllegalArgumentException se il numero di coppie è invalido
     */
    List<Card> createShuffledDeck(int numberOfPairs);
}
