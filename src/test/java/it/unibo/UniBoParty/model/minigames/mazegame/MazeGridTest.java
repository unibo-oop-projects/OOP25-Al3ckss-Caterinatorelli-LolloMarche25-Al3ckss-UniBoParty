package it.unibo.UniBoParty.model.minigames.mazegame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.uniboparty.model.minigames.mazegame.api.Cell;
import it.unibo.uniboparty.model.minigames.mazegame.impl.MazeGridImpl;
import it.unibo.uniboparty.utilities.CellType;

import static org.junit.jupiter.api.Assertions.*;

public class MazeGridTest {

    private CellType[][] testLayout;
    private final int START_ROW = 1;
    private final int START_COL = 0;
    private final int EXIT_ROW = 2;
    private final int EXIT_COL = 1;

    @BeforeEach
    void setUp() {

        testLayout = new CellType[][]{
            {CellType.WALL, CellType.EMPTY, CellType.WALL},
            {CellType.START, CellType.EMPTY, CellType.EMPTY},
            {CellType.WALL, CellType.EXIT, CellType.WALL}
        };
    }

    /**
     * test the constructor of MazeGridImpl and the correct identification of start and exit positions.
     */
    @Test
    void testConstructorAndStartExitPositions() {
        MazeGridImpl grid = new MazeGridImpl(testLayout);

        assertEquals(START_ROW, grid.getStartRow());
        assertEquals(START_COL, grid.getStartCol());
        assertEquals(EXIT_ROW, grid.getExitRow());
        assertEquals(EXIT_COL, grid.getExitCol());
        
        Cell startCell = grid.getGrid()[START_ROW][START_COL];
        assertEquals(CellType.START, startCell.getType());
        assertEquals(START_ROW, startCell.getRow());
    }

    /**
     * test that getGrid returns a copy of the grid and not the original reference.
     */
    @Test
    void testGetGridReturnsACopy() {
        MazeGridImpl gridImpl = new MazeGridImpl(testLayout);
        Cell[][] originalGridCopy = gridImpl.getGrid();
        Cell[][] secondGridCopy = gridImpl.getGrid();

        assertNotSame(originalGridCopy, secondGridCopy);

        assertNotSame(originalGridCopy[0], secondGridCopy[0]);

        assertSame(originalGridCopy[1][1], secondGridCopy[1][1]);
    }
}