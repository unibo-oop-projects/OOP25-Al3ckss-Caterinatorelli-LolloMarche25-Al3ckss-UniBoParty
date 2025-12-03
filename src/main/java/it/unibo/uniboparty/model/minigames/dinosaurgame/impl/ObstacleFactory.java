package it.unibo.uniboparty.model.minigames.dinosaurgame.impl;

import java.util.Random;

/**
 * Factory to create obstacles.
 */
public final class ObstacleFactory {

    private static final Random RANDOM = new Random();

    private static final int[][] OBSTACLE_TYPES = {
        {20, 50},
        {50, 40},
        {35, 70},
    };

    private ObstacleFactory() { }

    /**
     * Creates a new obstacle with random type and position.
     * 
     * @param startX      the x-coordinate to start from
     * @param groundY     the y-coordinate of the ground level
     * @param minDistance the minimum horizontal distance from startX
     * @param maxVariation maximum additional random variation in horizontal distance
     * @param speed       the speed of the obstacle
     * @return a newly created ObstacleImpl instance
     */
    public static ObstacleImpl create(
            final int startX,
            final int groundY,
            final int minDistance,
            final int maxVariation,
            final int speed
    ) {
        final int[] type = OBSTACLE_TYPES[RANDOM.nextInt(OBSTACLE_TYPES.length)];
        final int width = type[0];
        final int height = type[1];

        final int x = startX + minDistance + RANDOM.nextInt(maxVariation);

        return new ObstacleImpl(x, groundY, width, height, speed);
    }
}
