package it.unibo.uniboparty.controller.minigames.typeracergame.api;

/**
 * Interface of Typeracer's Controller.
 */
@FunctionalInterface
public interface Controller {

    /**
     * Returns the current game state.
     * 
     * @return 0 if game lost, 1 if game won, 2 if still running
     */
    int getState();
}
