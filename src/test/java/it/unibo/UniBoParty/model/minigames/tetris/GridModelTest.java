package it.unibo.uniboparty.model.minigames.tetris;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import it.unibo.uniboparty.model.minigames.tetris.api.ModelListener;
import it.unibo.uniboparty.model.minigames.tetris.impl.GridModelImpl;
import it.unibo.uniboparty.model.minigames.tetris.impl.PieceImpl;

import java.awt.Color;

import static org.junit.jupiter.api.Assertions.*;

class GridModelTest {
    private GridModelImpl grid;
    private PieceImpl dot;
    private PieceImpl block;
    private TestListener listener;
    private static final int ROWS = 10;
    private static final int COLS = 10;

    /**
     *fake listener for testing purposes.
     */
    static class TestListener implements ModelListener {
        private int callCount = 0;
        @Override
        public void onModelChanged() {
            callCount++;
        }
        int getCallCount() { return callCount; }
        void reset() { callCount = 0; }
    }

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

        assertTrue(grid.canPlace(block, 5, 5));
        assertTrue(grid.canPlace(dot, 9, 9));
    }

    /**
     * test canPlace method for invalid placements (out of bounds).
     */
    @Test
    void testCanPlaceInvalidBoundary() {

        assertFalse(grid.canPlace(block, ROWS - 1, 5));
        assertFalse(grid.canPlace(block, 5, COLS - 1));
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
    void testClearFullLines_NoLines() {
        // Piazziamo solo una cella
        grid.place(dot, 5, 5);
        
        int cleared = grid.clearFullLines();
        assertEquals(0, cleared);
        assertTrue(grid.isOccupied(5, 5)); // La cella deve rimanere
    }

    /**
     * test clearFullLines method when a full row is present.
     */
    @Test
    void testClearFullLines_FullRow() {
        // Riempiamo intenzionalmente una riga (riga 5)
        for(int c = 0; c < COLS; c++) {
            grid.place(dot, 5, c);
        }
        
        int cleared = grid.clearFullLines();
        
        assertEquals(1, cleared);

        for (int c = 0; c < COLS; c++) {
            assertFalse(grid.isOccupied(5, c));
        }
    }

    /**
     * test clearFullLines method when a full column is present.
     */
    @Test
    void testClearFullLines_FullColumn() {

        for(int r = 0; r < ROWS; r++) {
            grid.place(dot, r, 5);
        }

        int cleared = grid.clearFullLines();

        assertEquals(1, cleared);

        for (int r = 0; r < ROWS; r++) {
            assertFalse(grid.isOccupied(r, 5));
        }
    }

    @Test
    void testClearFullLines_RowAndColumnOverlap() {

    for(int r = 0; r < ROWS; r++) {
        grid.place(dot, r, 5);
    }

    for(int c = 0; c < COLS; c++) {

        if (!grid.isOccupied(5, c)) { 
            grid.place(dot, 5, c);
        }
    }

    int cleared = grid.clearFullLines();

    assertEquals(2, cleared, "Dovrebbe contare 2 (1 riga + 1 colonna)");
    for (int r = 0; r < ROWS; r++) {
        for (int c = 0; c < COLS; c++) {
            assertFalse(grid.isOccupied(r, c));
        }
    }
}
}