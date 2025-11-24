package it.unibo.UniBoParty.model.minigames.dinosaurGame.api;

import java.util.List;
/**
 * Interfaccia che definisce le operazioni principali del modello del gioco.
 * Il modello gestisce il dinosauro, gli ostacoli e la logica del gioco.
 */
public interface Model {

    /**
     * Inizia il salto del dinosauro.
     * Se il dinosauro è già in aria, il metodo non ha effetto.
     */
    void jump();

    /**
     * Segnala il rilascio del tasto salto.
     * Permette di implementare salti variabili, più brevi se il tasto viene rilasciato subito.
     */
    void releaseJump();

    /**
     * Aggiorna lo stato del gioco.
     * <p>
     * Operazioni principali:
     * <ul>
     *   <li>Applica la gravità e aggiorna la posizione verticale del dinosauro.</li>
     *   <li>Muove gli ostacoli e genera nuovi ostacoli quando escono dallo schermo.</li>
     *   <li>Controlla le collisioni tra dinosauro e ostacoli.</li>
     *   <li>Incrementa la difficoltà aumentando la velocità degli ostacoli a intervalli regolari.</li>
     * </ul>
     * </p>
     */
    void update();

    /**
     * Restituisce la posizione X del dinosauro sullo schermo.
     *
     * @return coordinata X del dinosauro
     */
    int getDinoX();

    /**
     * Restituisce la posizione Y del dinosauro sullo schermo.
     *
     * @return coordinata Y del dinosauro
     */
    int getDinoY();

    /**
     * Restituisce la larghezza del dinosauro.
     *
     * @return larghezza in pixel
     */
    int getDinoWidth();

    /**
     * Restituisce l'altezza del dinosauro.
     *
     * @return altezza in pixel
     */
    int getDinoHeight();

    /**
     * Restituisce la lista degli ostacoli attualmente presenti nel gioco.
     *
     * @return lista di oggetti Obstacle
     */
    List<Obstacle> getObstacles();
}
