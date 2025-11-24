package it.unibo.UniBoParty.model.minigames.dinosaurGame.impl;

import it.unibo.UniBoParty.model.minigames.dinosaurGame.api.Model;
import it.unibo.UniBoParty.model.minigames.dinosaurGame.api.Obstacle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ModelImpl implements Model {
    private final int GROUND_Y = 350;
    private final int INIT_VEL = -15;

    private int dinoX = 50;
    private int dinoY = GROUND_Y;
    private int dinoWidth = 40;
    private int dinoHeight = 50;

    private double velY = 0;
    private final double gravity = 0.7;
    private int nearestX = 0;

    private boolean isJumping = false;
    private boolean isHoldingJump = false;

    private final List<Obstacle> obstacles;
    private int difficulty = 0;

    private Random random = new Random();

    private static final int NUM_INITIAL_OBSTACLES = 3;
    private static final int INIT_OBSTACLE_MIN_DISTANCE = 400;
    private static final int INIT_OBSTACLE_MAX_VARIATION = 300;
    private static final int OBSTACLE_INITIAL_SPEED = 5;

    private static final int[][] OBSTACLE_TYPES = {
        {20, 50}, // piccolo e alto
        {50, 40}, // largo e basso
        {35, 70}  // medio e molto alto
    };

    public ModelImpl() {
        obstacles = new ArrayList<>();
        int lastX = 600;
        for (int i = 0; i < NUM_INITIAL_OBSTACLES; i++) {
            ObstacleImpl o = generateRandomObstacle(lastX);
            obstacles.add(o);
            lastX = o.getObstX();
        }
    }

    private ObstacleImpl generateRandomObstacle(int minX) {
        int[] type = OBSTACLE_TYPES[random.nextInt(OBSTACLE_TYPES.length)];
        int width = type[0];
        int height = type[1];
        int x = minX + INIT_OBSTACLE_MIN_DISTANCE + random.nextInt(INIT_OBSTACLE_MAX_VARIATION);
        return new ObstacleImpl(x, GROUND_Y, width, height, OBSTACLE_INITIAL_SPEED);
    }

    public void update() {
        nearestX = 0;
        difficulty++;

        if (isJumping) {
            dinoY += velY;
            velY += isHoldingJump ? gravity * 0.65 : gravity;
        }

        if (dinoY >= GROUND_Y) {
            dinoY = GROUND_Y;
            velY = 0;
            isJumping = false;
        }

        for (Obstacle o : obstacles) {
            if (o.getObstX() > nearestX) nearestX = o.getObstX();
        }

        for (int i = 0; i < obstacles.size(); i++) {
            Obstacle o = obstacles.get(i);
            o.moveObstacle();
            if (o.getObstX() + o.getObstWidth() < 0) {
                obstacles.set(i, generateRandomObstacle(nearestX));
            }
        }

        for (Obstacle o : obstacles) {
            boolean overlapX = dinoX + dinoWidth > o.getObstX() && dinoX < o.getObstX() + o.getObstWidth();
            boolean overlapY = dinoY > o.getObstY() - o.getObstHeight();
            if (overlapX && overlapY) System.out.println("Game over");
        }

        if (difficulty % 500 == 0) {
            for (Obstacle o : obstacles) o.setObstSpeed(o.getObstSpeed() + 1);
            System.out.println("Difficulty Up");
        }
    }

    public void jump() {
        if (!isJumping) {
            isJumping = true;
            isHoldingJump = true;
            velY = INIT_VEL;
        }
    }

    public void releaseJump() {
        isHoldingJump = false;
    }

    public int getDinoX() { return dinoX; }
    public int getDinoY() { return dinoY; }
    public int getDinoWidth() { return dinoWidth; }
    public int getDinoHeight() { return dinoHeight; }
    public List<Obstacle> getObstacles() { return new ArrayList<>(obstacles); }
}
