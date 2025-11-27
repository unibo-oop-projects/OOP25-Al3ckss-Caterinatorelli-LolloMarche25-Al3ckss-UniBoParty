package it.unibo.uniboparty.model.minigames.mazegame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.uniboparty.model.minigames.mazegame.impl.MazeGridImpl;
import it.unibo.uniboparty.model.minigames.mazegame.impl.PlayerImpl;
import it.unibo.uniboparty.utilities.CellType;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerTest {

    private static final int INITIAL_ROW = 0;
    private static final int INITIAL_COL = 0;
    private PlayerImpl player;

    @BeforeEach
    void setUp() {
        final CellType[][] layout = {
            {CellType.START},
            {CellType.EMPTY},
        };
        final MazeGridImpl grid = new MazeGridImpl(layout);
        this.player = new PlayerImpl(grid, INITIAL_ROW, INITIAL_COL);
    }

    /**
     * test the constructor and getters of PlayerImpl.
     */
    @Test
    void testConstructorAndGetInitialPosition() {
        assertEquals(INITIAL_ROW, player.getRow());
        assertEquals(INITIAL_COL, player.getCol());
        assertEquals(0, player.getMoves(), "Le mosse iniziali dovrebbero essere 0");
    }

    /**
     * test setPosition updates coordinates and increments moves.
     */
    @Test
    void testSetPositionUpdatesCoordinatesAndIncrementsMoves() {
        final int newRow = 1;
        final int newCol = 0;

        player.setPosition(newRow, newCol);

        assertEquals(newRow, player.getRow());
        assertEquals(newCol, player.getCol());

        assertEquals(1, player.getMoves(), "Il conteggio delle mosse dovrebbe essere 1 dopo setPosition");
    }

    /**
     * test incrementMoves method.
     */
    @Test
    void testIncrementMovesManually() {
        player.incrementMoves();
        assertEquals(1, player.getMoves());

        player.incrementMoves();
        assertEquals(2, player.getMoves());
    }

    /**
     * test getCurrentCell returns the correct cell based on player's position.
     */
    @Test
    void testGetCurrentCell() {

        assertEquals(CellType.START, player.getCurrentCell().getType(), "La cella iniziale deve essere START");

        player.setPosition(1, 0);
        assertEquals(CellType.EMPTY, player.getCurrentCell().getType(), "La nuova cella deve essere PATH");
    }
}
