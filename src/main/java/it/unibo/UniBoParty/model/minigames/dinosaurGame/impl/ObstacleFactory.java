package it.unibo.uniboparty.model.minigames.dinosaurgame.impl;

import java.util.Random;

/**
 * Factory to create obstacles
 */
public final class ObstacleFactory {

    
    private static final Random RANDOM = new Random();
    
    private static final int[][] OBSTACLE_TYPES = {
        {20, 50},
        {50, 40},
        {35, 70}
    };

    private ObstacleFactory() { }
    
    /**
     * Creates an obstacle.
     * 
     * @param startX
     * @param groundY
     * @param minDistance
     * @param maxVariation
     * @param speed
     * @return
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
