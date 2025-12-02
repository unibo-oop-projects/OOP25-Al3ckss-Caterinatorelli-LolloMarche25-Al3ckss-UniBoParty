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
     * Decrements the game timer of one unit (1s).
     */
    void decreaseTime();

    /**
     * Returns the current state of the game.
     *
     * @return current GameState
     */
    GameState getState();

    /**
     * Sets the game's state.
     *
     * @param state new GameState
     */
    void setState(GameState state);

    /**
     * Ends the match and sets the game state to GAME_OVER.
     */
    void gameOver();

    /**
     * Adds an observer that will be notified when the model updates.
     *
     * @param observer the observer to add
     */
    void addObserver(GameObserver observer);

    /**
     * Removes an observer previously added.
     *
     * @param observer the observer to remove
     */
    void removeObserver(GameObserver observer);
}
