package it.unibo.uniboparty.model.minigames.mazegame;

import org.junit.jupiter.api.Test;
import it.unibo.uniboparty.model.minigames.mazegame.impl.CellImpl;
import it.unibo.uniboparty.utilities.CellType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CellTest {
    private static final int ROW = 5;
    private static final int COL = 10;

    /**
     * test the initialization and getters of CellImpl.
     */
    @Test
    void testCellPathInitializationAndGetters() {
        final CellImpl cell = new CellImpl(ROW, COL, CellType.EMPTY);

        assertEquals(ROW, cell.getRow(), "row does not match");
        assertEquals(COL, cell.getCol(), "col does not match");
        assertEquals(CellType.EMPTY, cell.getType(), "type does not match");
    }

    /**
     * test isWalkable method for the CellType.EMPTY type.
     */
    @Test
    void testIsWalkableForPath() {
        final CellImpl cell = new CellImpl(ROW, COL, CellType.EMPTY);
        assertTrue(cell.isWalkable(), "the EMPTY cells should be walkable");
    }

    /**
     * test isWalkable method for the CellType.WALL type.
     */
    @Test
    void testIsWalkableForWall() {
        final CellImpl cell = new CellImpl(ROW, COL, CellType.WALL);
        assertFalse(cell.isWalkable(), "the WALL cells should not be walkable");
    }

    /**
     * test isWalkable method for the CellType.START type.
     */
    @Test
    void testIsWalkableForStart() {
        final CellImpl cell = new CellImpl(ROW, COL, CellType.START);
        assertTrue(cell.isWalkable(), "the START cells should be walkable");
    }

    /**
     * test isWalkable method for the CellType.EXIT type.
     */
    @Test
    void testIsWalkableForExit() {
        final CellImpl cell = new CellImpl(ROW, COL, CellType.EXIT);
        assertTrue(cell.isWalkable(), "the EXIT cells should be walkable");
    }
}
