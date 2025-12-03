package it.unibo.uniboparty.model.minigames.dinosaurgame.api;

/**
 * Interface defining the main operations of an obstacle.
 * An obstacle has position, size, and movement speed.
 */
public interface Obstacle {

    /**
     * Moves the obstacle to the left according to its speed.
     *
     * @return the new X position
     */
    int moveObstacle();

    /**
     * @return the current X position of the obstacle
     */
    int getObstX();

    /**
     * Sets the X position of the obstacle.
     *
     * @param x new X coordinate
     */
    void setObstX(int x);

    /**
     * @return the Y position of the obstacle
     */
    int getObstY();

    /**
     * @return the width of the obstacle in pixels
     */
    int getObstWidth();

    /**
     * @return the height of the obstacle in pixels
     */
    int getObstHeight();

    /**
     * @return the obstacle movement speed in pixels per update
     */
    int getObstSpeed();

    /**
     * Sets the movement speed of the obstacle.
     *
     * @param speed new speed
     */
    void setObstSpeed(int speed);
}
