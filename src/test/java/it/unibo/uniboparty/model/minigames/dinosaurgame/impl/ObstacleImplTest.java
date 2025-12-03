package it.unibo.uniboparty.model.minigames.dinosaurgame.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for ObstacleImpl.
 */
class ObstacleImplTest {

    private static final int INIT_X = 100;
    private static final int INIT_Y = 350;
    private static final int INIT_WIDTH = 30;
    private static final int INIT_HEIGHT = 50;
    private static final int INIT_SPEED = 5;

    private static final int NEW_X = 200;
    private static final int NEW_SPEED = 10;

    private ObstacleImpl obstacle;

    /**
     * Set up obstacle before each test.
     */
    @BeforeEach
    void setUp() {
        obstacle = new ObstacleImpl(INIT_X, INIT_Y, INIT_WIDTH, INIT_HEIGHT, INIT_SPEED);
    }

    /**
     * Test initial obstacle values.
     */
    @Test
    void testInitialValues() {
        assertEquals(INIT_X, obstacle.getObstX());
        assertEquals(INIT_Y, obstacle.getObstY());
        assertEquals(INIT_WIDTH, obstacle.getObstWidth());
        assertEquals(INIT_HEIGHT, obstacle.getObstHeight());
        assertEquals(INIT_SPEED, obstacle.getObstSpeed());
    }

    /**
     * Test that obstacle moves correctly.
     */
    @Test
    void testMoveObstacle() {
        final int xBefore = obstacle.getObstX();
        obstacle.moveObstacle();
        assertEquals(xBefore - INIT_SPEED, obstacle.getObstX());
    }

    /**
     * Test that setObstX changes position.
     */
    @Test
    void testSetObstX() {
        obstacle.setObstX(NEW_X);
        assertEquals(NEW_X, obstacle.getObstX());
    }

    /**
     * Test that setObstSpeed changes speed and affects movement.
     */
    @Test
    void testSetObstSpeed() {
        obstacle.setObstSpeed(NEW_SPEED);
        assertEquals(NEW_SPEED, obstacle.getObstSpeed());

        final int xBefore = obstacle.getObstX();
        obstacle.moveObstacle();
        assertEquals(xBefore - NEW_SPEED, obstacle.getObstX());
    }
}
