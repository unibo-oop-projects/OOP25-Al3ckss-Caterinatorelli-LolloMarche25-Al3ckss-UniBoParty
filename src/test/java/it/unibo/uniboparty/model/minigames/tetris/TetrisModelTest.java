package it.unibo.uniboparty.model.minigames.tetris;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import it.unibo.uniboparty.model.minigames.tetris.api.ModelListener;
import it.unibo.uniboparty.model.minigames.tetris.impl.PieceImpl;
import it.unibo.uniboparty.model.minigames.tetris.impl.TetrisModelImpl;
import it.unibo.uniboparty.model.minigames.tetris.impl.StandardPieces; 
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TetrisModelTest {

    private static final int ROWS = 5;
    private static final int COLS = 5;
    private static final int SCORE_PER_LINE = 10;
    private TetrisModelImpl model;
    private TestListener listener;

    @BeforeEach
    void setUp() {
        model = new TetrisModelImpl(ROWS, COLS);
        listener = new TestListener();
        model.addListener(listener);
        listener.reset();
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
        final PieceImpl[] initialRack = model.getRack();
        final PieceImpl consumed = initialRack[0];

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

        final int expectedScore = model.getScore() + 4 + 2 * SCORE_PER_LINE;
        model.award(4, 2); 
        assertEquals(expectedScore, model.getScore());
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
        final PieceImpl p1 = model.randomPiece();
        final PieceImpl p2 = model.randomPiece();
        assertNotNull(p1);
        assertNotNull(p2);
        assertTrue(StandardPieces.ALL.contains(p1));
    }

    /**
     * test selectPiece and getSelected methods.
     */
    @Test
    void testSelectAndGetSelectedPiece() {
        final PieceImpl p = model.getRack()[0];
        model.selectPiece(p);

        assertEquals(p, model.getSelected());
    }

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
