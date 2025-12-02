package it.unibo.uniboparty.model.minigames.dinosaurgame.api;

import java.util.List;

import it.unibo.uniboparty.model.minigames.dinosaurgame.impl.GameState;
import it.unibo.uniboparty.model.minigames.dinosaurgame.impl.ObstacleImpl;

/**
 * Defines the main operations of the game model.
 * The model manages the dinosaur, obstacles, and game logic.
 */
public interface Model {

    /**
     * Starts the dinosaur's jump.
     * If the dinosaur is already in the air, the method has no effect.
     */
    void jump();

    /**
     * Signals that the jump key has been released.
     * Allows shorter, variable jumps depending on release time.
     */
    void releaseJump();

    /**
     * Updates the state of the game.
     *
     * <p>Main operations:</p>
     * <ul>
     *   <li>Applies gravity and updates the dinosaur's vertical position.</li>
     *   <li>Moves obstacles and generates new ones when old ones leave the screen.</li>
     *   <li>Checks collisions between the dinosaur and obstacles.</li>
     *   <li>Increases difficulty by speeding up obstacles at regular intervals.</li>
     * </ul>
     */
    void update();

    /**
     * @return the dinosaur's X coordinate.
     */
    int getDinoX();

    /**
     * @return the dinosaur's Y coordinate.
     */
    int getDinoY();

    /**
     * @return the dinosaur's width in pixels.
     */
    int getDinoWidth();

    /**
     * @return the dinosaur's height in pixels.
     */
    int getDinoHeight();

    /**
     * @return the list of active obstacles.
     */
    List<ObstacleImpl> getObstacles();

    /**
     * @return the current game state (RUNNING or GAME_OVER).
     */
    GameState getGameState();

    /**
     * @return the difficulty
     */
    int getDifficulty();

    /**
     * Adds an observer that will be notified when the model updates.
     *
     * @param observer the observer to add
     */
    void addObserver(GameObserver observer);

    /**
     * Removes a previously added observer.
     *
     * @param observer the observer to remove
     */
    void removeObserver(GameObserver observer);
}
