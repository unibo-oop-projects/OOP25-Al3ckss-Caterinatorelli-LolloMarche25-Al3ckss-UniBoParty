package it.unibo.uniboparty.model.minigames.dinosaurgame.impl;

import java.util.ArrayList;
import java.util.List;

import it.unibo.uniboparty.model.minigames.dinosaurgame.api.Model;

/**
 * Implementation of the model handling game logic, physics and obstacles.
 */
public class ModelImpl implements Model {

    private static final int DINO_X = GameConfig.INIT_DINO_X;
    private int dinoY = GameConfig.GROUND_Y;

    private static final int DINO_WIDTH = GameConfig.DINO_WIDTH;
    private static final int DINO_HEIGHT = GameConfig.DINO_HEIGHT;

    private double velY;
    private static final double GRAVITY = GameConfig.GRAVITY;

    private int nearestX;
    private boolean isJumping;
    private boolean isHoldingJump;

    private int difficulty;

    /**
     * List of active obstacles in the game.
     */
    public final List<ObstacleImpl> obstacles = new ArrayList<>();

    private GameState gameState = GameState.RUNNING;

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
        nearestX = 0;

        if (isJumping) {
            dinoY += velY;
            velY += isHoldingJump ? GRAVITY * GameConfig.JUMP_GRAVITY : GRAVITY;
        }

        if (dinoY >= GameConfig.GROUND_Y) {
            dinoY = GameConfig.GROUND_Y;
            velY = 0;
            isJumping = false;
        }

        for (final ObstacleImpl o : obstacles) {
            if (o.getObstX() > nearestX) {
                nearestX = o.getObstX();
            }
        }

        for (int i = 0; i < obstacles.size(); i++) {
            final ObstacleImpl o = obstacles.get(i);
            o.moveObstacle();

            if (o.getObstX() + o.getObstWidth() < 0) {
                obstacles.set(i, ObstacleFactory.create(
                        nearestX,
                        GameConfig.GROUND_Y,
                        GameConfig.INIT_OBSTACLE_MIN_DISTANCE,
                        GameConfig.INIT_OBSTACLE_MAX_VARIATION,
                        GameConfig.OBSTACLE_INITIAL_SPEED
                                + (difficulty / GameConfig.DIFFICULTY_INCREMENT_INTERVAL)
                ));
            }
        }

        for (final ObstacleImpl o : obstacles) {
            final boolean overlapX = DINO_X + DINO_WIDTH > o.getObstX()
                    && DINO_X < o.getObstX() + o.getObstWidth();
            final boolean overlapY = dinoY > o.getObstY() - o.getObstHeight();

            if (overlapX && overlapY) {
                gameState = GameState.GAME_OVER;
                return;
            }
        }

        if (difficulty % GameConfig.DIFFICULTY_INCREMENT_INTERVAL == 0) {
            for (final ObstacleImpl o : obstacles) {
                o.setObstSpeed(o.getObstSpeed() + 1);
            }
        }
    }

    @Override
    public void jump() {
        if (!isJumping && gameState == GameState.RUNNING) {
            isJumping = true;
            isHoldingJump = true;
            velY = GameConfig.INIT_JUMP_VELOCITY;
        }
    }

    @Override
    public void releaseJump() {
        isHoldingJump = false;
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
        return obstacles;
    }

    @Override
    public GameState getGameState() {
        return gameState;
    }

    @Override
    public int getDifficulty() {
        return difficulty;
    }

    
}
