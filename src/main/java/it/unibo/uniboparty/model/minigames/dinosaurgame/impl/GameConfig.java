package it.unibo.uniboparty.model.minigames.dinosaurgame.impl;

/**
 * Collection of DinoRun's constants to avoid magic numbers.
 */
public final class GameConfig {

    public static final int GROUND_Y = 350;
    public static final int DINO_WIDTH = 40;
    public static final int DINO_HEIGHT = 50;
    public static final int INIT_DINO_X = 50;

    public static final int PANEL_WIDTH = 600;
    public static final int PANEL_HEIGHT = 400;
    public static final double GRAVITY = 0.7;
    public static final int INIT_JUMP_VELOCITY = -15;

    public static final int NUM_INITIAL_OBSTACLES = 3;
    public static final int INIT_OBSTACLE_MIN_DISTANCE = 400;
    public static final int INIT_OBSTACLE_MAX_VARIATION = 300;
    public static final int OBSTACLE_INITIAL_SPEED = 5;

    public static final int TIMER_DELAY_MS = 12;

    public static final int DIFFICULTY_INCREMENT_INTERVAL = 500;
    public static final double JUMP_GRAVITY = 0.65;

    public static final int WIN_TIME_SECONDS = 30;

    private GameConfig() { }
}
