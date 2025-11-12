package it.unibo.UniBoParty.model.minigames.mazegame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.uniboparty.model.minigames.mazegame.api.MazeModel;
import it.unibo.uniboparty.model.minigames.mazegame.impl.MazeModelImpl;
import it.unibo.uniboparty.utilities.Direction;
import it.unibo.uniboparty.view.minigames.mazegame.api.GameObserver;

import static org.junit.jupiter.api.Assertions.*;


public class MazeModelTest {
    private MazeModelImpl model;
    private TestObserver observer;
    
    private static class TestObserver implements GameObserver {
        private int updateCount = 0;

        @Override
        public void onModelUpdated(MazeModel model) {
            updateCount++;
        }

        public int getUpdateCount() {
            return updateCount;
        }
        
        public void resetUpdateCount() {
            this.updateCount = 0;
        }
    }

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
    @SuppressWarnings("unused")
    @Test
    void testMovePlayerFailsAgainstWallOrBoundary() {
        int initialMoves = model.getCurrentMoves();
        int initialRow = model.getPlayer().getRow();
        int initialCol = model.getPlayer().getCol();
        
        
        int successfulMoves = 0;
        for (Direction dir : Direction.values()) {
            if (model.movePlayer(dir)) {
                successfulMoves++;
            }
        }

        
        model.reset();
        observer.resetUpdateCount();
        
        
        model.movePlayer(Direction.DOWN);
        int currentMoves = model.getCurrentMoves();
        
       
        boolean successful = model.movePlayer(Direction.UP); 
        
        if (!successful) {
            assertEquals(currentMoves, model.getCurrentMoves(), "Le mosse NON devono essere incrementate per una mossa fallita.");
            assertEquals(0, observer.getUpdateCount(), "L'observer NON deve essere notificato per una mossa fallita.");
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
  
        int movesBeforeReset = model.getCurrentMoves();
        assertEquals(successfulMoves, movesBeforeReset, "Il conteggio di currentMoves deve corrispondere alle mosse riuscite.");
        assertTrue(successfulMoves > 0, "Almeno una mossa deve essere riuscita per testare correttamente il reset.");
        
        
        if (successfulMoves > 0) { 
             assertFalse(model.getPlayer().getRow() == initialRow && model.getPlayer().getCol() == initialCol,
                         "Il player deve essersi spostato dalla posizione iniziale.");
        }
        model.reset();
        assertEquals(0, model.getCurrentMoves(), "Le mosse correnti del modello devono essere azzerate.");

        int expectedUpdates = successfulMoves + 1;
        assertEquals(expectedUpdates, observer.getUpdateCount(), "L'observer deve essere notificato: Mosse Riuscite + 1 (per Reset).");

        assertEquals(initialRow, model.getPlayer().getRow(), "La riga del player deve tornare alla riga di START (posizione iniziale).");
        assertEquals(initialCol, model.getPlayer().getCol(), "La colonna del player deve tornare alla colonna di START (posizione iniziale).");
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

        assertEquals(65, model.getMaxMoves());
    }
}
