package it.unibo.uniboparty.model.board;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.uniboparty.model.board.impl.BoardModelImpl;
import it.unibo.uniboparty.model.board.api.BoardModel;
import it.unibo.uniboparty.utilities.MinigameId;


class BoardModelImplTest {

    private BoardModel model;

    @BeforeEach
    void setUp() {
        this.model = new BoardModelImpl();
    }

    @Test
    void testBoardSizeIsCorrect() {
        assertEquals(16, this.model.getSize());
    }

    @Test
    void testCellsAreNotNull() {
        for (int i = 0; i < this.model.getSize(); i++) {
            assertNotNull(this.model.getCellAt(i));
        }
    }

    @Test
    void testMinigameCellsHaveCorrectTypeAndId() {
        assertEquals(CellType.MINIGAME, this.model.getCellAt(1).getType());
        assertEquals(MinigameId.GAME_1, this.model.getCellAt(1).getMinigameId());

        assertEquals(CellType.MINIGAME, this.model.getCellAt(3).getType());
        assertEquals(MinigameId.GAME_2, this.model.getCellAt(3).getMinigameId());

        assertEquals(CellType.MINIGAME, this.model.getCellAt(5).getType());
        assertEquals(MinigameId.GAME_3, this.model.getCellAt(5).getMinigameId());

        assertEquals(CellType.MINIGAME, this.model.getCellAt(7).getType());
        assertEquals(MinigameId.GAME_4, this.model.getCellAt(7).getMinigameId());

        assertEquals(CellType.MINIGAME, this.model.getCellAt(9).getType());
        assertEquals(MinigameId.GAME_5, this.model.getCellAt(9).getMinigameId());

        assertEquals(CellType.MINIGAME, this.model.getCellAt(11).getType());
        assertEquals(MinigameId.GAME_6, this.model.getCellAt(11).getMinigameId());

        assertEquals(CellType.MINIGAME, this.model.getCellAt(13).getType());
        assertEquals(MinigameId.GAME_7, this.model.getCellAt(13).getMinigameId());

        assertEquals(CellType.MINIGAME, this.model.getCellAt(15).getType());
        assertEquals(MinigameId.GAME_8, this.model.getCellAt(15).getMinigameId());
    }

    @Test
    void testNormalCellsHaveNoMinigame() {
        final int[] normalIndices = {0, 2, 4, 6, 8, 10, 12, 14};

        for (final int index : normalIndices) {
            final CellModel cell = this.model.getCellAt(index);
            assertEquals(CellType.NORMAL, cell.getType(), "Cell at index " + index + " should be NORMAL");
            assertNull(cell.getMinigameId(), "Cell at index " + index + " should not have a minigame");
        }
    }
}
