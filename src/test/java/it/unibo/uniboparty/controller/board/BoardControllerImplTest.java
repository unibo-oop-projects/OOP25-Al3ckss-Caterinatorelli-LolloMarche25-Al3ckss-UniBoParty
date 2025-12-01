package it.unibo.uniboparty.controller.board;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.uniboparty.model.board.api.BoardModel;
import it.unibo.uniboparty.model.board.impl.BoardModelImpl;
import it.unibo.uniboparty.model.board.CellType;
import it.unibo.uniboparty.utilities.MinigameId;
import it.unibo.uniboparty.controller.board.api.BoardController;
import it.unibo.uniboparty.controller.board.impl.BoardControllerImpl;

class BoardControllerImplTest {

    private BoardModel model;
    private BoardController controller;

    @BeforeEach
    void setUp() {
        this.model = new BoardModelImpl();
        this.controller = new BoardControllerImpl(this.model);
    }

    @Test
    void testBoardSizeDelegatesToModel() {
        assertEquals(this.model.getSize(), this.controller.getBoardSize());
        assertEquals(16, this.controller.getBoardSize());
    }

    @Test
    void testGetCellTypeAtReturnsCorrectTypes() {
        final int[] minigameIndices = {1, 3, 5, 7, 9, 11, 13, 15};
        for (final int index : minigameIndices) {
            assertEquals(
                CellType.MINIGAME,
                this.controller.getCellTypeAt(index),
                "Cell at index " + index + " should be MINIGAME"
            );
        }

        final int[] normalIndices = {0, 2, 4, 6, 8, 10, 12, 14};
        for (final int index : normalIndices) {
            assertEquals(
                CellType.NORMAL,
                this.controller.getCellTypeAt(index),
                "Cell at index " + index + " should be NORMAL"
            );
        }
    }

    @Test
    void testGetMinigameAtReturnsCorrectIds() {
        assertEquals(MinigameId.GAME_1, this.controller.getMinigameAt(1));
        assertEquals(MinigameId.GAME_2, this.controller.getMinigameAt(3));
        assertEquals(MinigameId.GAME_3, this.controller.getMinigameAt(5));
        assertEquals(MinigameId.GAME_4, this.controller.getMinigameAt(7));
        assertEquals(MinigameId.GAME_5, this.controller.getMinigameAt(9));
        assertEquals(MinigameId.GAME_6, this.controller.getMinigameAt(11));
        assertEquals(MinigameId.GAME_7, this.controller.getMinigameAt(13));
        assertEquals(MinigameId.GAME_8, this.controller.getMinigameAt(15));
    }

    @Test
    void testGetMinigameAtReturnsNullOnNormalCells() {
        final int[] normalIndices = {0, 2, 4, 6, 8, 10, 12, 14};
        for (final int index : normalIndices) {
            assertNull(
                this.controller.getMinigameAt(index),
                "Cell at index " + index + " should not have a minigame id"
            );
        }
    }

    @Test
    void testOnPlayerLandedReturnsMinigameIdForMinigameCells() {
        assertEquals(MinigameId.GAME_1, this.controller.onPlayerLanded(1));
        assertEquals(MinigameId.GAME_2, this.controller.onPlayerLanded(3));
        assertEquals(MinigameId.GAME_3, this.controller.onPlayerLanded(5));
        assertEquals(MinigameId.GAME_4, this.controller.onPlayerLanded(7));
        assertEquals(MinigameId.GAME_5, this.controller.onPlayerLanded(9));
        assertEquals(MinigameId.GAME_6, this.controller.onPlayerLanded(11));
        assertEquals(MinigameId.GAME_7, this.controller.onPlayerLanded(13));
        assertEquals(MinigameId.GAME_8, this.controller.onPlayerLanded(15));
    }

    @Test
    void testOnPlayerLandedReturnsNullForNormalCells() {
        final int[] normalIndices = {0, 2, 4, 6, 8, 10, 12, 14};
        for (final int index : normalIndices) {
            assertNull(
                this.controller.onPlayerLanded(index),
                "onPlayerLanded should return null for NORMAL cell at index " + index
            );
        }
    }
}
