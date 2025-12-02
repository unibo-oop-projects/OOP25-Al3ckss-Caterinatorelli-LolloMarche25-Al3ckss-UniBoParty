package it.unibo.uniboparty.controller.minigames.dinosaurgame.api;

/**
 * Interface for the game controller.
 * Handles game start, stop and input management.
 */
public interface Controller {

    /**
     * Starts the controller and the game loop.
     */
    void start();

    /**
     * Stops the game loop and timers.
     */
    void stop();
}
