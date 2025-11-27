package it.unibo.uniboparty.model.minigames.dinosaurgame.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ObstacleImplTest {

    private ObstacleImpl obstacle;

    @BeforeEach
    void setUp() {
        obstacle = new ObstacleImpl(100, 350, 30, 50, 5);
    }

    @Test
    void testInitialValues() {
        assertEquals(100, obstacle.getObstX());
        assertEquals(350, obstacle.getObstY());
        assertEquals(30, obstacle.getObstWidth());
        assertEquals(50, obstacle.getObstHeight());
        assertEquals(5, obstacle.getObstSpeed());
    }

    @Test
    void testMoveObstacle() {
        int xBefore = obstacle.getObstX();
        obstacle.moveObstacle();
        assertEquals(xBefore - 5, obstacle.getObstX());
    }

    @Test
    void testSetObstX() {
        obstacle.setObstX(200);
        assertEquals(200, obstacle.getObstX());
    }

    @Test
    void testSetObstSpeed() {
        obstacle.setObstSpeed(10);
        assertEquals(10, obstacle.getObstSpeed());
        
        int xBefore = obstacle.getObstX();
        obstacle.moveObstacle();
        assertEquals(xBefore - 10, obstacle.getObstX());
    }
}
