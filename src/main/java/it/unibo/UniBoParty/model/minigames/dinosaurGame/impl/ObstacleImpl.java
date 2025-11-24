package it.unibo.UniBoParty.model.minigames.dinosaurGame.impl;

import it.unibo.UniBoParty.model.minigames.dinosaurGame.api.Obstacle;

public class ObstacleImpl implements Obstacle {

    private int obstX, obstY;
    private int obstWidth, obstHeight;
    private int obstSpeed;

    public ObstacleImpl(int obstX, int obstY, int obstWidth, int obstHeight, int obstSpeed) {
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
    public void setObstX(int obstX) {
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
    public void setObstSpeed(int obstSpeed) {
        this.obstSpeed = obstSpeed;
    }
}
