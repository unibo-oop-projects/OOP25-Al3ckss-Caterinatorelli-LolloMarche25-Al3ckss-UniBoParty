package it.unibo.uniboparty.model.minigames.dinosaurgame.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

class ModelImplTest {

    private static final int INIT_DINO_X = 50;
    private static final int INIT_DINO_Y = 350;
    private static final int DINO_WIDTH = 40;
    private static final int DINO_HEIGHT = 50;

    private ModelImpl model;

    @BeforeEach
    void setUp() {
        model = new ModelImpl();
    }

    @Test
    void testInitialDinoPosition() {
        assertEquals(INIT_DINO_X, model.getDinoX());
        assertEquals(INIT_DINO_Y, model.getDinoY());
    }

    @Test
    void testDinoDimensions() {
        assertEquals(DINO_WIDTH, model.getDinoWidth());
        assertEquals(DINO_HEIGHT, model.getDinoHeight());
    }

    @Test
    void testObstaclesAreCreated() {
        final List<ObstacleImpl> obstacles = model.getObstacles(); // no cast
        assertFalse(obstacles.isEmpty());
    }

    @Test
    void testJumpChangesPosition() {
        final int yBefore = model.getDinoY();
        model.jump();
        model.update();
        assertTrue(model.getDinoY() < yBefore);
    }

    @Test
    void testGameStateInitial() {
        assertEquals(GameState.RUNNING, model.getGameState());
    }

    @Test
    void testObstacleMovement() {
        final List<ObstacleImpl> obstacles = model.getObstacles(); // no cast
        final ObstacleImpl obstacle = obstacles.get(0);
        final int xBefore = obstacle.getObstX();
        obstacle.moveObstacle();
        assertTrue(obstacle.getObstX() < xBefore);
    }
}
