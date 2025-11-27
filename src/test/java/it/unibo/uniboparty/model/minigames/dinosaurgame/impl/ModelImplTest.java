package it.unibo.uniboparty.model.minigames.dinosaurgame.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ModelImplTest {

    private ModelImpl model;

    @BeforeEach
    void setUp() {
        model = new ModelImpl();
    }

    @Test
    void testInitialDinoPosition() {
        assertEquals(50, model.getDinoX());
        assertEquals(350, model.getDinoY());
    }

    @Test
    void testDinoDimensions() {
        assertEquals(40, model.getDinoWidth());
        assertEquals(50, model.getDinoHeight());
    }

    @Test
    void testObstaclesAreCreated() {
        ArrayList<ObstacleImpl> obstacles = (ArrayList<ObstacleImpl>) model.getObstacles();
        assertTrue(obstacles.size() > 0);
    }

    @Test
    void testJumpChangesPosition() {
        int yBefore = model.getDinoY();
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
        ArrayList<ObstacleImpl> obstacles = (ArrayList<ObstacleImpl>) model.getObstacles();
        ObstacleImpl obstacle = obstacles.get(0);
        int xBefore = obstacle.getObstX();
        obstacle.moveObstacle();
        assertTrue(obstacle.getObstX() < xBefore);
    }
}
