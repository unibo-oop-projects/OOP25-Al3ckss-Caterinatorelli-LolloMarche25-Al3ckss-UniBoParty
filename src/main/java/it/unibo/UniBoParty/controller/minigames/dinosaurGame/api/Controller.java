package it.unibo.UniBoParty.controller.minigames.dinosaurGame.api;

/**
 * Interfaccia per il controller del gioco.
 * Permette di gestire l’avvio, la pausa e la gestione degli input.
 */
public interface Controller {

    /**
     * Avvia il controller e il ciclo di gioco.
     */
    void start();

    /**
     * Ferma il ciclo di gioco e i timer.
     */
    void stop();
}
