package unibo.party.minigames.memory.model.api;

import java.util.List;

/**
 * API del model del gioco Memory.
 */
public interface MemoryGameModel {
    
    /**
     * Rivela la carta all'indice indicato.
     * @return true se la mossa è accettata ed eseguita.
     */
    boolean flipCard(int index);
    
    /**
     * Se l'ultimo turno è un mismatch "in sospeso", ricopre entrambe le carte e chiude il turno.
     */
    void resolveMismatch();

    /**
     * @return true se c'è un mismatch in sospeso da risolvere.
     */
    boolean hasMismatchPending();

    /**
     * @return true se tutte le coppie sono state trovate.
     */
    boolean isGameOver();

    /**
     * @return lista immutabile delle carte sul tavolo (read-only per il chiamante).
     */
    List<Card> getCards();

    /**
     * Snapshot dello stato corrente.
     */
    MemoryGameReadOnlyState getGameState();
}
