package it.unibo.uniboparty.model.minigames.mazegame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.uniboparty.model.minigames.mazegame.api.MazeModel;
import it.unibo.uniboparty.model.minigames.mazegame.impl.MazeModelImpl;
import it.unibo.uniboparty.utilities.Direction;
import it.unibo.uniboparty.view.minigames.mazegame.api.GameObserver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Test class for MazeModelImpl.
 */
class MazeModelTest {
    private static final int MAX_MOVES_EXPECTED = 65;
    private MazeModelImpl model;
    private TestObserver observer;

    /**
     * Set up the MazeModelImpl and TestObserver before each test.
     */
    @BeforeEach
    void setUp() {
        this.model = new MazeModelImpl(); 
        this.observer = new TestObserver();
        model.addObserver(observer);
        observer.resetUpdateCount();
    }

    /**
     * test that moving the player in a valid direction updates the model correctly.
     */
    @Test
    void testMovePlayer() {
        boolean moved = false;

        for (final Direction dir : Direction.values()) {
            if (model.movePlayer(dir)) {
                moved = true;
                break;
            }
        }
 
        assertTrue(moved, "Almeno una mossa valida deve essere possibile nel labirinto generato.");
        assertEquals(1, model.getCurrentMoves(), "Le mosse correnti devono essere incrementate.");
        assertEquals(1, observer.getUpdateCount(), "L'observer deve essere notificato dopo una mossa valida.");
    }

    /**
     * test all directions where the player cannot move (either due to walls or boundaries).
     */
    @Test
    void testMovePlayerFailsAgainstWallOrBoundary() {
        model.movePlayer(Direction.DOWN);
        final int currentMoves = model.getCurrentMoves();
        final boolean successful = model.movePlayer(Direction.UP); 
        if (!successful) {
            assertEquals(currentMoves, model.getCurrentMoves());
            assertEquals(0, observer.getUpdateCount());
        }
    }
 
    /**
     * Test the reset functionality of the MazeModelImpl.
     */
    @Test
    void testResetFunctionality() {
        final int initialRow = model.getPlayer().getRow();
        final int initialCol = model.getPlayer().getCol();
        int successfulMoves = 0;
        if (model.movePlayer(Direction.RIGHT)) {
            successfulMoves++;
        }
        if (model.movePlayer(Direction.UP)) {
            successfulMoves++;
        }
        final int movesBeforeReset = model.getCurrentMoves();
        assertEquals(successfulMoves, movesBeforeReset);
        assertTrue(successfulMoves > 0);

        if (successfulMoves > 0) { 
            assertFalse(model.getPlayer().getRow() == initialRow && model.getPlayer().getCol() == initialCol);
        }
        model.reset();
        assertEquals(0, model.getCurrentMoves());

        final int expectedUpdates = successfulMoves + 1;
        assertEquals(expectedUpdates, observer.getUpdateCount());

        assertEquals(initialRow, model.getPlayer().getRow());
        assertEquals(initialCol, model.getPlayer().getCol());
    }

    /**
     * Test that the getters return consistent values.
     */
    @Test
    void testGettersAreConsistent() {
        assertTrue(model.getRows() > 0);
        assertTrue(model.getCols() > 0);
        assertNotNull(model.getPlayer());
        assertNotNull(model.getCell(0, 0));
        assertEquals(MAX_MOVES_EXPECTED, model.getMaxMoves());
    }

    private static final class TestObserver implements GameObserver {
        private int updateCount;

        @Override
        public void onModelUpdated(final MazeModel model) {
            updateCount++;
        }

        public int getUpdateCount() {
            return updateCount;
        }

        public void resetUpdateCount() {
            this.updateCount = 0;
        }
    }
}
