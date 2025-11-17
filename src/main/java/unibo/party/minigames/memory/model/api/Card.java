package unibo.party.minigames.memory.model.api;

/**
 * Interfaccia che definisce il comportamento di una carta del Memory.
 * Fornisce metodi di accesso e controllo dello stato "coperta/rivelata".
 */
public interface Card extends CardReadOnly {
    
    /** @return identificatore univoco della carta */
    int getId();

    /** @return il simbolo associato alla carta */
    Symbol getSymbol();

    /** @return true se la carta è attualmente rivelata */
    boolean isRevealed();

    /** Imposta la carta come rivelata */
    void reveal();

    /** Imposta la carta come coperta */
    void hide();
}
