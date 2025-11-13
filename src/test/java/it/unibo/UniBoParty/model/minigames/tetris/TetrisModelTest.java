package it.unibo.uniboparty.model.minigames.tetris;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.uniboparty.model.minigames.tetris.api.GridModel;
import it.unibo.uniboparty.model.minigames.tetris.api.ModelListener;
import it.unibo.uniboparty.model.minigames.tetris.impl.GridModelImpl;
import it.unibo.uniboparty.model.minigames.tetris.impl.PieceImpl;
import it.unibo.uniboparty.model.minigames.tetris.impl.TetrisModelImpl;
import it.unibo.uniboparty.model.minigames.tetris.impl.StandardPieces; 
import java.awt.Point;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class TetrisModelTest {
    private TetrisModelImpl model;
    private TestListener listener;
    private static final int ROWS = 5;
    private static final int COLS = 5;

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
        model = new TetrisModelImpl(ROWS, COLS);
        listener = new TestListener();
        model.addListener(listener);
        listener.reset(); // reset dopo l'inizializzazione di newRack() nel costruttore
    }

    /**
     * test initial setup of the model.
     */
    @Test
    void testInitialSetupAndRack() {
        assertEquals(0, model.getScore());
        assertEquals(3, model.getRack().length, "Il rack deve iniziare con 3 pezzi.");
        assertNull(model.getSelected(), "Nessun pezzo deve essere selezionato all'inizio.");
        assertEquals(0, listener.getCallCount(), "Nessuna notifica dopo il reset del listener.");
    }

    /**
     * test newRack and consumePiece methods.
     */
    @Test
    void testNewRackAndConsumePiece() {
        PieceImpl[] initialRack = model.getRack();
        PieceImpl consumed = initialRack[0];

        model.consumePiece(consumed);
        assertEquals(2, model.getRack().length);

        model.consumePiece(model.getRack()[0]);
        assertEquals(1, model.getRack().length);

        model.consumePiece(model.getRack()[0]);
        assertEquals(3, model.getRack().length);

        assertEquals(4, listener.getCallCount()); 
    }

    /**
     * test award method.
     */
    @Test
    void testAwardScore() {
        model.award(1, 0); // 1 cella piazzata
        assertEquals(1, model.getScore());
        
        model.award(4, 2); // 4 celle piazzate + 2 linee * 10 = 24
        assertEquals(25, model.getScore());
    }

    /**
     * test hasAnyMove method.
     */
    @Test
    void testHasAnyMove() {

        assertTrue(model.hasAnyMove());

    }

    /**
     * test randomPiece method.
     */
    @Test
    void testRandomPiece() {
        PieceImpl p1 = model.randomPiece();
        PieceImpl p2 = model.randomPiece();
        assertNotNull(p1);
        assertNotNull(p2);
        assertTrue(StandardPieces.ALL.contains(p1));
    }

    /**
     * test selectPiece and getSelected methods.
     */
    @Test
    void testSelectAndGetSelectedPiece() {
        PieceImpl p = model.getRack()[0];
        model.selectPiece(p);

        assertEquals(p, model.getSelected());
    }

    /**
     * test tryPlaceAt method for valid placement.
     */
    @Test
    void testTryPlaceAt_ValidPlacement() {
        PieceImpl pieceToPlace = model.getRack()[0];
        model.selectPiece(pieceToPlace);
        listener.reset();
    
        model.tryPlaceAt(0, 0); 

        GridModel grid = model.getGrid();
        for (Point rel : pieceToPlace.getCells()) {
            assertTrue(grid.isOccupied(rel.y, rel.x));
        }

        assertNull(model.getSelected());
        assertFalse(List.of(model.getRack()).contains(pieceToPlace));
        assertEquals(2, model.getRack().length);

        int expectedScore = pieceToPlace.getCells().size();
        assertEquals(expectedScore, model.getScore());
    }

    /**
     * test tryPlaceAt method for invalid placement.
     */
    @Test
    void testTryPlaceAt_InvalidPlacement() {
        PieceImpl pieceToPlace = model.getRack()[0];
        model.selectPiece(pieceToPlace);

        ((GridModelImpl) model.getGrid()).place(pieceToPlace, 0, 0); 

        model.tryPlaceAt(0, 0); 

        assertNull(model.getSelected());
        assertTrue(List.of(model.getRack()).contains(pieceToPlace));

        assertEquals(0, model.getScore());
    }
}
