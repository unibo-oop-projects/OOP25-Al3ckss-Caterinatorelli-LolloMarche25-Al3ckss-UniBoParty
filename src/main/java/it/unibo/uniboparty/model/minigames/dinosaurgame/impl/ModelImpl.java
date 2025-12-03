package it.unibo.uniboparty.model.minigames.dinosaurgame.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import it.unibo.uniboparty.model.minigames.dinosaurgame.api.Model;
import it.unibo.uniboparty.model.minigames.dinosaurgame.api.GameObserver;

/**
 * Implementation of the model handling game logic, physics and obstacles.
 */
public final class ModelImpl implements Model {

    // Constants for the dinosaur
    private static final int DINO_X = GameConfig.INIT_DINO_X;
    private static final int DINO_WIDTH = GameConfig.DINO_WIDTH;
    private static final int DINO_HEIGHT = GameConfig.DINO_HEIGHT;

    // Physics constants
    private static final double GRAVITY = GameConfig.GRAVITY;

    // Dinosaur state
    private int dinoY = GameConfig.GROUND_Y;
    private double velY;
    private boolean isJumping;
    private boolean isHoldingJump;

    // Game state
    private int difficulty;
    private GameState gameState = GameState.RUNNING;

    // Active obstacles
    private final List<ObstacleImpl> obstacles = new ArrayList<>();

    // Observer list
    private final List<GameObserver> observers = new CopyOnWriteArrayList<>();

    /**
     * Creates the model and initializes the starting obstacles.
     */
    public ModelImpl() {
        int lastX = GameConfig.PANEL_WIDTH;
        for (int i = 0; i < GameConfig.NUM_INITIAL_OBSTACLES; i++) {
            final ObstacleImpl o = ObstacleFactory.create(
                lastX,
                GameConfig.GROUND_Y,
                GameConfig.INIT_OBSTACLE_MIN_DISTANCE,
                GameConfig.INIT_OBSTACLE_MAX_VARIATION,
                GameConfig.OBSTACLE_INITIAL_SPEED
            );
            obstacles.add(o);
            lastX = o.getObstX();
        }
    }

    @Override
    public void update() {
        if (gameState == GameState.GAME_OVER) {
            return;
        }

        difficulty++;
        int nearestX = 0;

        if (isJumping) {
            dinoY += velY;
            velY += isHoldingJump ? GRAVITY * GameConfig.JUMP_GRAVITY : GRAVITY;
        }

        if (dinoY >= GameConfig.GROUND_Y) {
            dinoY = GameConfig.GROUND_Y;
            velY = 0;
            isJumping = false;
        }

        // find farthest obstacle x (nearest to spawn)
        for (final ObstacleImpl o : obstacles) {
            if (o.getObstX() > nearestX) {
                nearestX = o.getObstX();
            }
        }

        for (final ObstacleImpl o : obstacles) {
            o.moveObstacle();

            if (o.getObstX() + o.getObstWidth() < 0) {
                final int newSpeed = GameConfig.OBSTACLE_INITIAL_SPEED
                        + (difficulty / GameConfig.DIFFICULTY_INCREMENT_INTERVAL);

                final ObstacleImpl replacement = ObstacleFactory.create(
                    nearestX,
                    GameConfig.GROUND_Y,
                    GameConfig.INIT_OBSTACLE_MIN_DISTANCE,
                    GameConfig.INIT_OBSTACLE_MAX_VARIATION,
                    newSpeed
                );

                o.reset(
                    replacement.getObstX(),
                    replacement.getObstY(),
                    replacement.getObstWidth(),
                    replacement.getObstHeight(),
                    replacement.getObstSpeed()
                );
            }
        }

        // collision detection
        for (final ObstacleImpl o : obstacles) {
            final boolean overlapX = DINO_X + DINO_WIDTH > o.getObstX()
                    && DINO_X < o.getObstX() + o.getObstWidth();
            final boolean overlapY = dinoY > o.getObstY() - o.getObstHeight();

            if (overlapX && overlapY) {
                gameState = GameState.GAME_OVER;
                return;
            }
        }

        // increase obstacle speed periodically
        if (difficulty % GameConfig.DIFFICULTY_INCREMENT_INTERVAL == 0) {
            for (final ObstacleImpl o : obstacles) {
                o.setObstSpeed(o.getObstSpeed() + 1);
            }
        }
        // Notify observers
        notifyObservers();
    }

    @Override
    public void jump() {
        if (!isJumping && gameState == GameState.RUNNING) {
            isJumping = true;
            isHoldingJump = true;
            velY = GameConfig.INIT_JUMP_VELOCITY;
            // notify immediately to update UI responsively
            notifyObservers();
        }
    }

    @Override
    public void releaseJump() {
        isHoldingJump = false;
        // notify to allow shorter jumps to be reflected in the UI
        notifyObservers();
    }

    @Override
    public int getDinoX() {
        return DINO_X;
    }

    @Override
    public int getDinoY() {
        return dinoY;
    }

    @Override
    public int getDinoWidth() {
        return DINO_WIDTH;
    }

    @Override
    public int getDinoHeight() {
        return DINO_HEIGHT;
    }

    @Override
    public List<ObstacleImpl> getObstacles() {
        return new ArrayList<>(obstacles);
    }

    @Override
    public GameState getGameState() {
        return gameState;
    }

    @Override
    public int getDifficulty() {
        return difficulty;
    }

    // Observer methods

    /**
     * Adds Observer.
     * 
     * @param observer the DinoRun's Observer
     */
    @Override
    public void addObserver(final GameObserver observer) {
        observers.add(observer);
    }

    /**
     * Removes Observer.
     * 
     * @param observer the DinoRun's Observer
     */
    @Override
    public void removeObserver(final GameObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        for (final GameObserver observer : observers) {
            observer.modelUpdated();
        }
    }
}
