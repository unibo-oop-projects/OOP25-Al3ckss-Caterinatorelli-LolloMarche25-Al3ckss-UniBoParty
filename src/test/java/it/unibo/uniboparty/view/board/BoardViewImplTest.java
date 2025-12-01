package it.unibo.uniboparty.view.board;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import it.unibo.uniboparty.view.board.impl.BoardViewImpl;
import it.unibo.uniboparty.controller.board.api.BoardController;

class BoardViewImplTest {

    @Test
    void testViewCreatesController() {
        BoardViewImpl view = new BoardViewImpl();
        BoardController controller = view.getController();

        assertNotNull(controller);
        assertEquals(16, controller.getBoardSize());
    }

    @Test
    void testSetPlayerPositionWorksForValidIndices() {
        BoardViewImpl view = new BoardViewImpl();

        assertDoesNotThrow(() -> view.setPlayerPosition(0));
        assertDoesNotThrow(() -> view.setPlayerPosition(15));
    }

    @Test
    void testSetPlayerPositionThrowsForInvalidIndices() {
        BoardViewImpl view = new BoardViewImpl();

        assertThrows(IllegalArgumentException.class, () -> view.setPlayerPosition(-1));
        assertThrows(IllegalArgumentException.class, () -> view.setPlayerPosition(100));
    }
}
