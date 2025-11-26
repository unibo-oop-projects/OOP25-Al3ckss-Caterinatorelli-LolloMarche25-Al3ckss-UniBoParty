package it.unibo.uniboparty.model.minigames.dinosaurgame.impl;

import it.unibo.uniboparty.model.minigames.dinosaurgame.api.Obstacle;

/**
 * Implementation of obstacles
 */
public class ObstacleImpl implements Obstacle {

    private int obstX;
    private final int obstY;
    private final int obstWidth, obstHeight;
    private int obstSpeed;

    public ObstacleImpl(final int obstX, final int obstY, final int obstWidth, final int obstHeight, final int obstSpeed) {
        this.obstX = obstX;
        this.obstY = obstY;
        this.obstWidth = obstWidth;
        this.obstHeight = obstHeight;
        this.obstSpeed = obstSpeed;
    }

    @Override
    public int moveObstacle() {
        return obstX -= obstSpeed;
    }

    @Override
    public int getObstX() {
        return obstX;
    }

    @Override
    public void setObstX(final int obstX) {
        this.obstX = obstX;
    }

    @Override
    public int getObstY() {
        return obstY;
    }

    @Override
    public int getObstWidth() {
        return obstWidth;
    }

    @Override
    public int getObstHeight() {
        return obstHeight;
    }

    @Override
    public int getObstSpeed() {
        return obstSpeed;
    }

    @Override
    public void setObstSpeed(final int obstSpeed) {
        this.obstSpeed = obstSpeed;
    }
}
