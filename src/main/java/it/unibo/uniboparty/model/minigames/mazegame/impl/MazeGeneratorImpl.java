package it.unibo.uniboparty.model.minigames.mazegame.impl;

import java.util.Random;

import it.unibo.uniboparty.model.minigames.mazegame.api.MazeGenerator;
import it.unibo.uniboparty.utilities.CellType;

/**
 * Implementation of the MazeGenerator interface.
 */
public class MazeGeneratorImpl implements MazeGenerator {

    private static final CellType WA = CellType.WALL;
    private static final CellType EM = CellType.EMPTY;
    private static final CellType ST = CellType.START;
    private static final CellType EX = CellType.EXIT;
    private final Random random = new Random();

    /**
     * {@inheritDoc}
     */
    @Override
    public CellType[][] generate() {

        final int index = random.nextInt(3); 
        return switch (index) {
            case 0 -> buildMaze1();
            case 1 -> buildMaze2();
            default -> buildMaze3();
        };

    }

    private CellType[][] buildMaze1() {
        return new CellType[][] {
                {WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA},
                {WA, ST, EM, EM, WA, EM, EM, EM, EM, WA, EM, EM, EM, EM, WA, EM, EM, EM, EM, WA},
                {WA, WA, EM, WA, WA, EM, WA, WA, EM, WA, EM, WA, WA, EM, WA, WA, WA, WA, EM, WA},
                {WA, EM, EM, EM, EM, EM, WA, EM, EM, WA, EM, EM, EM, WA, EM, EM, WA, EM, EM, WA},
                {WA, EM, WA, WA, WA, EM, WA, WA, EM, WA, WA, WA, WA, EM, WA, WA, WA, WA, EM, WA},
                {WA, EM, EM, EM, WA, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, WA},
                {WA, WA, WA, EM, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, EM, WA},
                {WA, EM, EM, EM, EM, EM, EM, EM, EM, WA, EM, EM, EM, EM, EM, EM, EM, WA, EM, WA},
                {WA, EM, WA, WA, WA, WA, WA, EM, WA, WA, WA, WA, WA, WA, WA, EM, WA, WA, EM, WA},
                {WA, EM, EM, EM, EM, EM, WA, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, WA},
                {WA, WA, WA, WA, EM, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, EM, WA},
                {WA, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, WA},
                {WA, EM, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, EM, WA},
                {WA, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, WA},
                {WA, WA, WA, WA, WA, WA, EM, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, EM, WA, WA},
                {WA, EM, EM, EM, EM, EM, EM, WA, EM, EM, EM, EM, EM, EM, WA, EM, EM, EM, EM, WA},
                {WA, EM, WA, WA, WA, WA, EM, WA, WA, WA, WA, WA, WA, EM, WA, WA, WA, WA, EM, WA},
                {WA, EM, EM, EM, EM, EM, WA, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, WA},
                {WA, WA, WA, WA, EM, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, EX, WA},
                {WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA},
        };
    }

    private CellType[][] buildMaze2() {
        return new CellType[][] {
                {WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA},
                {WA, ST, EM, WA, EM, EM, EM, EM, WA, EM, EM, WA, EM, EM, EM, EM, WA, EM, EM, WA},
                {WA, WA, EM, WA, EM, WA, WA, EM, WA, WA, EM, WA, WA, WA, EM, WA, WA, EM, WA, WA},
                {WA, EM, EM, EM, EM, EM, WA, EM, EM, EM, EM, EM, EM, EM, WA, EM, EM, EM, EM, WA},
                {WA, EM, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, EM, WA, WA, WA, WA, WA, WA, WA},
                {WA, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, WA, EM, EM, EM, EM, EM, EM, EM, WA},
                {WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, EM, WA, WA, WA, WA, WA, WA, WA, EM, WA},
                {WA, EM, EM, EM, EM, EM, EM, WA, EM, EM, EM, EM, EM, EM, EM, WA, EM, EM, EM, WA},
                {WA, EM, WA, WA, WA, WA, EM, WA, WA, WA, WA, WA, WA, WA, EM, WA, WA, WA, EM, WA},
                {WA, EM, EM, EM, EM, EM, WA, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, WA},
                {WA, WA, WA, WA, EM, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, EM, WA},
                {WA, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, WA},
                {WA, EM, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, EM, WA},
                {WA, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, WA},
                {WA, WA, WA, EM, WA, WA, WA, WA, WA, EM, WA, WA, WA, EM, WA, WA, WA, WA, WA, WA},
                {WA, EM, EM, EM, EM, WA, EM, EM, EM, EM, EM, EM, EM, WA, EM, EM, EM, EM, EM, WA},
                {WA, EM, WA, WA, WA, WA, EM, WA, WA, WA, WA, WA, WA, EM, WA, WA, WA, WA, EM, WA},
                {WA, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, WA},
                {WA, WA, WA, WA, EM, EM, EM, EM, EM, WA, WA, WA, WA, WA, WA, WA, WA, WA, EX, WA},
                {WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA},
        };

    }

    private CellType[][] buildMaze3() {

        return new CellType[][] {
                {WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA},
                {WA, ST, EM, WA, EM, WA, EM, EM, EM, WA, EM, EM, WA, EM, WA, EM, EM, EM, EM, WA},
                {WA, EM, WA, WA, EM, WA, WA, WA, EM, WA, WA, WA, WA, EM, WA, WA, WA, EM, WA, WA},
                {WA, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, WA},
                {WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, EM, WA, WA, WA, WA},
                {WA, EM, EM, EM, EM, EM, EM, EM, EM, WA, EM, EM, EM, EM, EM, EM, WA, EM, EM, WA},
                {WA, EM, WA, WA, WA, WA, WA, EM, WA, WA, WA, WA, WA, WA, WA, EM, WA, WA, WA, WA},
                {WA, EM, EM, EM, EM, EM, WA, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, WA},
                {WA, WA, WA, WA, EM, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, EM, WA},
                {WA, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, WA},
                {WA, EM, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, EM, WA},
                {WA, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, WA},
                {WA, WA, EM, WA, WA, WA, EM, WA, WA, WA, WA, WA, WA, WA, WA, EM, WA, WA, WA, WA},
                {WA, EM, EM, EM, EM, WA, EM, EM, EM, EM, EM, EM, EM, WA, EM, EM, EM, EM, EM, WA},
                {WA, EM, WA, WA, WA, WA, EM, WA, WA, WA, WA, WA, WA, EM, WA, WA, WA, WA, EM, WA},
                {WA, EM, EM, EM, EM, EM, WA, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, WA},
                {WA, WA, WA, WA, EM, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, EM, WA},
                {WA, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, EM, WA},
                {WA, EM, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, EX, WA},
                {WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA, WA},
        };

    }

}
