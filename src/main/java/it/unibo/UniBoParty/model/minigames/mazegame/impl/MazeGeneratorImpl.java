package it.unibo.uniboparty.model.minigames.mazegame.impl;

import java.util.Random;

import it.unibo.uniboparty.model.minigames.mazegame.api.MazeGenerator;
import it.unibo.uniboparty.utilities.CellType;
/**
 * Implementation of the MazeGenerator interface.
 */
public class MazeGeneratorImpl implements MazeGenerator {
    final Random random = new Random();
    final CellType W = CellType.WALL;
    final CellType E = CellType.EMPTY;
    final CellType S = CellType.START;
    final CellType X = CellType.EXIT;

    /**
     * @{inheritDoc}
     */
    @Override
    public CellType[][] generate() {
        // Scegli un layout casuale
        int index = random.nextInt(3); // 3 labirinti predefiniti
        return switch (index) {
            case 0 -> buildMaze1();
            case 1 -> buildMaze2();
            default -> buildMaze3();
        };

    }

    private CellType[][] buildMaze1() {

        CellType[][] layout = new CellType[][] {
                {W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W},
                {W, S, E, E, W, E, E, E, E, W, E, E, E, E, W, E, E, E, E, W},
                {W, W, E, W, W, E, W, W, E, W, E, W, W, E, W, W, W, W, E, W},
                {W, E, E, E, E, E, W, E, E, W, E, E, E, W, E, E, W, E, E, W},
                {W, E, W, W, W, E, W, W, E, W, W, W, W, E, W, W, W, W, E, W},
                {W, E, E, E, W, E, E, E, E, E, E, E, E, E, E, E, E, E, E, W},
                {W, W, W, E, W, W, W, W, W, W, W, W, W, W, W, W, W, W, E, W},
                {W, E, E, E, E, E, E, E, E, W, E, E, E, E, E, E, E, W, E, W},
                {W, E, W, W, W, W, W, E, W, W, W, W, W, W, W, E, W, W, E, W},
                {W, E, E, E, E, E, W, E, E, E, E, E, E, E, E, E, E, E, E, W},
                {W, W, W, W, E, W, W, W, W, W, W, W, W, W, W, W, W, W, E, W},
                {W, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, W},
                {W, E, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, E, W},
                {W, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, W},
                {W, W, W, W, W, W, E, W, W, W, W, W, W, W, W, W, W, E, W, W},
                {W, E, E, E, E, E, E, W, E, E, E, E, E, E, W, E, E, E, E, W},
                {W, E, W, W, W, W, E, W, W, W, W, W, W, E, W, W, W, W, E, W},
                {W, E, E, E, E, E, W, E, E, E, E, E, E, E, E, E, E, E, E, W},
                {W, W, W, W, E, W, W, W, W, W, W, W, W, W, W, W, W, W, X, W},
                {W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W}
        };
        return layout;
    }

    private CellType[][] buildMaze2() {
        CellType[][] layout = new CellType[][] {
                {W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W},
                {W, S, E, W, E, E, E, E, W, E, E, W, E, E, E, E, W, E, E, W},
                {W, W, E, W, E, W, W, E, W, W, E, W, W, W, E, W, W, E, W, W},
                {W, E, E, E, E, E, W, E, E, E, E, E, E, E, W, E, E, E, E, W},
                {W, E, W, W, W, W, W, W, W, W, W, W, E, W, W, W, W, W, W, W},
                {W, E, E, E, E, E, E, E, E, E, E, W, E, E, E, E, E, E, E, W},
                {W, W, W, W, W, W, W, W, W, W, E, W, W, W, W, W, W, W, E, W},
                {W, E, E, E, E, E, E, W, E, E, E, E, E, E, E, W, E, E, E, W},
                {W, E, W, W, W, W, E, W, W, W, W, W, W, W, E, W, W, W, E, W},
                {W, E, E, E, E, E, W, E, E, E, E, E, E, E, E, E, E, E, E, W},
                {W, W, W, W, E, W, W, W, W, W, W, W, W, W, W, W, W, W, E, W},
                {W, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, W},
                {W, E, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, E, W},
                {W, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, W},
                {W, W, W, E, W, W, W, W, W, E, W, W, W, E, W, W, W, W, W, W},
                {W, E, E, E, E, W, E, E, E, E, E, E, E, W, E, E, E, E, E, W},
                {W, E, W, W, W, W, E, W, W, W, W, W, W, E, W, W, W, W, E, W},
                {W, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, W},
                {W, W, W, W, E, E, E, E, E, W, W, W, W, W, W, W, W, W, X, W},
                {W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W}
        };
        return layout;
    }

    private CellType[][] buildMaze3() {

        CellType[][] layout = new CellType[][] {
                {W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W},
                {W, S, E, W, E, W, E, E, E, W, E, E, W, E, W, E, E, E, E, W},
                {W, E, W, W, E, W, W, W, E, W, W, W, W, E, W, W, W, E, W, W},
                {W, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, W},
                {W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, E, W, W, W, W},
                {W, E, E, E, E, E, E, E, E, W, E, E, E, E, E, E, W, E, E, W},
                {W, E, W, W, W, W, W, E, W, W, W, W, W, W, W, E, W, W, W, W},
                {W, E, E, E, E, E, W, E, E, E, E, E, E, E, E, E, E, E, E, W},
                {W, W, W, W, E, W, W, W, W, W, W, W, W, W, W, W, W, W, E, W},
                {W, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, W},
                {W, E, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, E, W},
                {W, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, W},
                {W, W, E, W, W, W, E, W, W, W, W, W, W, W, W, E, W, W, W, W},
                {W, E, E, E, E, W, E, E, E, E, E, E, E, W, E, E, E, E, E, W},
                {W, E, W, W, W, W, E, W, W, W, W, W, W, E, W, W, W, W, E, W},
                {W, E, E, E, E, E, W, E, E, E, E, E, E, E, E, E, E, E, E, W},
                {W, W, W, W, E, W, W, W, W, W, W, W, W, W, W, W, W, W, E, W},
                {W, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, E, W},
                {W, E, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, X, W},
                {W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W, W}
        };
        return layout;
    }

}