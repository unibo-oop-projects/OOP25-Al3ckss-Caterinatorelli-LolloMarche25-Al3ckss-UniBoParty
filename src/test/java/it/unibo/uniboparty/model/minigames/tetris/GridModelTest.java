package it.unibo.uniboparty.model.minigames.tetris;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import it.unibo.uniboparty.model.minigames.tetris.api.ModelListener;
import it.unibo.uniboparty.model.minigames.tetris.impl.GridModelImpl;
import it.unibo.uniboparty.model.minigames.tetris.impl.PieceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.awt.Color;

class GridModelTest {
    private static final int ROWS = 10;
    private static final int COLS = 10;
    private static final int TEST_ROWANDCOL = 5;
    private GridModelImpl grid;
    private PieceImpl dot;
    private PieceImpl block;
    private TestListener listener;

    @BeforeEach
    void setUp() {
        grid = new GridModelImpl(ROWS, COLS);
        listener = new TestListener();
        grid.addListener(listener);

        dot = PieceImpl.of(new int[][]{{0, 0}}, "Dot", Color.BLACK);

        block = PieceImpl.of(new int[][]{{0, 0}, {0, 1}, {1, 0}, {1, 1}}, "O", Color.YELLOW);
    }

    /**
     * test grid dimensions and reset functionality.
     */
    @Test
    void testDimensionsAndReset() {
        assertEquals(ROWS, grid.getRows());
        assertEquals(COLS, grid.getCols());
        grid.reset();
        assertEquals(1, listener.getCallCount());
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                assertFalse(grid.isOccupied(r, c));
            }
        }
    }

    /**
     * test canPlace method for valid placements.
     */
    @Test
    void testCanPlaceValidPlacement() {

        assertTrue(grid.canPlace(block, TEST_ROWANDCOL, TEST_ROWANDCOL));
        assertTrue(grid.canPlace(dot, ROWS - 1, COLS - 1));
    }

    /**
     * test canPlace method for invalid placements (out of bounds).
     */
    @Test
    void testCanPlaceInvalidBoundary() {

        assertFalse(grid.canPlace(block, ROWS - 1, TEST_ROWANDCOL));
        assertFalse(grid.canPlace(block, TEST_ROWANDCOL, COLS - 1));
    }

    /**
     * test canPlace method against occupied cells.
     */
    @Test
    void testCanPlaceAgainstOccupied() {
        grid.place(dot, 0, 0);

        assertFalse(grid.canPlace(dot, 0, 0));
        assertFalse(grid.canPlace(block, 0, 0)); 
    }

    /**
     * test place method and isOccupied method.
     */
    @Test
    void testPlaceAndIsOccupied() {
        listener.reset();
        grid.place(dot, 2, 3);

        assertTrue(grid.isOccupied(2, 3));
        assertFalse(grid.isOccupied(2, 4));

        assertEquals(0, listener.getCallCount()); 

        assertThrows(IllegalArgumentException.class, () -> grid.place(dot, 2, 3));
    }

    /**
     * test clearFullLines method for various scenarios.
     */
    @Test
    void testClearFullLinesNoLines() {

        grid.place(dot, TEST_ROWANDCOL, TEST_ROWANDCOL);

        final int cleared = grid.clearFullLines();
        assertEquals(0, cleared);
        assertTrue(grid.isOccupied(TEST_ROWANDCOL, TEST_ROWANDCOL)); // La cella deve rimanere
    }

    /**
     * test clearFullLines method when a full row is present.
     */
    @Test
    void testClearFullLinesFullRow() {

        for (int c = 0; c < COLS; c++) {
            grid.place(dot, TEST_ROWANDCOL, c);
        }

        final int cleared = grid.clearFullLines();

        assertEquals(1, cleared);

        for (int c = 0; c < COLS; c++) {
            assertFalse(grid.isOccupied(TEST_ROWANDCOL, c));
        }
    }

    /**
     * test clearFullLines method when a full column is present.
     */
    @Test
    void testClearFullLinesFullColumn() {

        for (int r = 0; r < ROWS; r++) {
            grid.place(dot, r, TEST_ROWANDCOL);
        }

        final int cleared = grid.clearFullLines();

        assertEquals(1, cleared);

        for (int r = 0; r < ROWS; r++) {
            assertFalse(grid.isOccupied(r, TEST_ROWANDCOL));
        }
    }

    /**
     * test clearFullLines method when both a full row and column overlap.
     */
    @Test
    void testClearFullLinesRowAndColumnOverlap() {

    for (int r = 0; r < ROWS; r++) {
        grid.place(dot, r, TEST_ROWANDCOL);
    }

    for (int c = 0; c < COLS; c++) {

        if (!grid.isOccupied(TEST_ROWANDCOL, c)) { 
            grid.place(dot, TEST_ROWANDCOL, c);
        }
    }

    final int cleared = grid.clearFullLines();

    assertEquals(2, cleared, "Dovrebbe contare 2 (1 riga + 1 colonna)");
    for (int r = 0; r < ROWS; r++) {
        for (int c = 0; c < COLS; c++) {
            assertFalse(grid.isOccupied(r, c));
        }
    }
}

/**
 * fake listener for testing purposes.
 */
static class TestListener implements ModelListener {
     private int callCount;

    @Override
    public void onModelChanged() {
        callCount++;
    }

    int getCallCount() {
        return callCount;
    }

    void reset() {
        callCount = 0;
    }
}
}
