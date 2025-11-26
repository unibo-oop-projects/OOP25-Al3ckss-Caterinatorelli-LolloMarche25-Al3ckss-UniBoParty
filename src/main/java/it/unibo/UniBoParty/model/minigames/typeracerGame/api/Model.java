package it.unibo.uniboparty.model.minigames.typeracergame.api;

import it.unibo.uniboparty.model.minigames.typeracergame.impl.GameState;

/**
 * Interface of the logic of TypeRacer.
 */
public interface Model {

    /**
     * Generates and sets a new word.
     */
    void setNewWord();

    /**
     * Returns the current word that needs to be typed.
     *
     * @return current word
     */
    String getCurrentWord();

    /**
     * Increments player's points.
     */
    void incrementPoints();

    /**
     * Returns the sum of the points the player has collected.
     *
     * @return current score
     */
    int getPoints();

    /**
     * Returns the remaining time the player has left.
     *
     * @return time(s)
     */
    int getTime();

    /**
     * Decrements the game timer of one unit (1s)
     */
    void decreaseTime();

    /**
     * Returns the current state of the game.
     *
     * @return stato di gioco
     */
    GameState getState();

    /**
     * Sets the game's states
     *
     * @param state new GameState
     */
    void setState(GameState state);

    /**
     * Ends the match and sets the game state to GAME_OVER.
     */
    void gameOver();
}
