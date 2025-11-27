package it.unibo.uniboparty.model.minigames.mazegame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.uniboparty.model.minigames.mazegame.api.Cell;
import it.unibo.uniboparty.model.minigames.mazegame.impl.MazeGridImpl;
import it.unibo.uniboparty.utilities.CellType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * Test class for MazeGridImpl.
 */
class MazeGridTest {

    private static final int START_ROW = 1;
    private static final int START_COL = 0;
    private static final int EXIT_ROW = 2;
    private static final int EXIT_COL = 1;
    private CellType[][] testLayout;

    /**
     * Set up a test layout before each test.
     */
    @BeforeEach
    void setUp() {

        testLayout = new CellType[][]{
            {CellType.WALL, CellType.EMPTY, CellType.WALL},
            {CellType.START, CellType.EMPTY, CellType.EMPTY},
            {CellType.WALL, CellType.EXIT, CellType.WALL},
        };
    }

    /**
     * test the constructor of MazeGridImpl and the correct identification of start and exit positions.
     */
    @Test
    void testConstructorAndStartExitPositions() {
        final MazeGridImpl grid = new MazeGridImpl(testLayout);

        assertEquals(START_ROW, grid.getStartRow());
        assertEquals(START_COL, grid.getStartCol());
        assertEquals(EXIT_ROW, grid.getExitRow());
        assertEquals(EXIT_COL, grid.getExitCol());

        final Cell startCell = grid.getGrid()[START_ROW][START_COL];
        assertEquals(CellType.START, startCell.getType());
        assertEquals(START_ROW, startCell.getRow());
    }

    /**
     * test that getGrid returns a copy of the grid and not the original reference.
     */
    @Test
    void testGetGridReturnsACopy() {
        final MazeGridImpl gridImpl = new MazeGridImpl(testLayout);
        final Cell[][] originalGridCopy = gridImpl.getGrid();
        final Cell[][] secondGridCopy = gridImpl.getGrid();

        assertNotSame(originalGridCopy, secondGridCopy);

        assertNotSame(originalGridCopy[0], secondGridCopy[0]);

        assertSame(originalGridCopy[1][1], secondGridCopy[1][1]);
    }
}
