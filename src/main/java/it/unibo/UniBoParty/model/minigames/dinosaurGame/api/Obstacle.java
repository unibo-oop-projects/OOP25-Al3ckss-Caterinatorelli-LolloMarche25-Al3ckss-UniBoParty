package it.unibo.UniBoParty.model.minigames.dinosaurGame.api;

/**
 * Interfaccia che definisce le operazioni principali di un ostacolo.
 * Un ostacolo ha posizione, dimensioni e velocità di movimento.
 */
public interface Obstacle {

    /**
     * Muove l'ostacolo verso sinistra secondo la sua velocità.
     */
    int moveObstacle();

    /**
     * Restituisce la posizione X corrente dell'ostacolo.
     *
     * @return coordinata X
     */
    int getObstX();

    /**
     * Imposta la posizione X dell'ostacolo.
     *
     * @param x nuova coordinata X
     */
    void setObstX(int x);

    /**
     * Restituisce la posizione Y dell'ostacolo.
     *
     * @return coordinata Y
     */
    int getObstY();

    /**
     * Restituisce la larghezza dell'ostacolo.
     *
     * @return larghezza in pixel
     */
    int getObstWidth();

    /**
     * Restituisce l'altezza dell'ostacolo.
     *
     * @return altezza in pixel
     */
    int getObstHeight();

    /**
     * Restituisce la velocità dell'ostacolo in pixel per aggiornamento.
     *
     * @return velocità
     */
    int getObstSpeed();

    /**
     * Imposta la velocità dell'ostacolo.
     *
     * @param speed nuova velocità
     */
    void setObstSpeed(int speed);
}
