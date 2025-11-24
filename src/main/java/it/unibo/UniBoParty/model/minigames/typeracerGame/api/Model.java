package it.unibo.UniBoParty.model.minigames.typeracerGame.api;

import it.unibo.UniBoParty.model.minigames.typeracerGame.impl.GameState;
/**
 * Rappresenta la logica di gioco principale del TypeRacer.
 * Gestisce stato, punteggio, parola corrente e timer.
 */
public interface Model {

    /**
     * Genera e imposta una nuova parola da scrivere.
     */
    void setNewWord();

    /**
     * Restituisce la parola attualmente da digitare.
     *
     * @return parola corrente
     */
    String getCurrentWord();

    /**
     * Incrementa il punteggio del giocatore.
     */
    void incrementPoints();

    /**
     * Restituisce il punteggio totale del giocatore.
     *
     * @return punteggio attuale
     */
    int getPoints();

    /**
     * Restituisce il tempo rimanente di gioco.
     *
     * @return tempo in secondi
     */
    int getTime();

    /**
     * Decrementa il timer di gioco di un'unità (es. 1 secondo).
     */
    void decreaseTime();

    /**
     * Restituisce lo stato corrente della partita.
     *
     * @return stato di gioco
     */
    GameState getState();

    /**
     * Imposta un nuovo stato di gioco.
     *
     * @param state nuovo GameState
     */
    void setState(GameState state);

    /**
     * Termina la partita e imposta lo stato su GAME_OVER.
     */
    void gameOver();
}
