package it.unibo.uniboparty.model.player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.uniboparty.controller.board.api.BoardController;
import it.unibo.uniboparty.model.board.CellType;
import it.unibo.uniboparty.model.player.api.Player;
import it.unibo.uniboparty.model.player.api.PlayerManager;
import it.unibo.uniboparty.model.player.api.TurnResult;
import it.unibo.uniboparty.model.player.impl.PlayerManagerImpl;
import it.unibo.uniboparty.utilities.MinigameId;
import it.unibo.uniboparty.view.board.api.BoardView;

/**
 * Test class for PlayerManagerImpl.
 */
class PlayerManagerImplTest {

    private static final int BOARD_SIZE = 20;
    private static final int NUM_PLAYERS = 3;
    private static final int CELL_POSITION_3 = 3;
    private static final int CELL_POSITION_5 = 5;
    private static final int CELL_POSITION_6 = 6;
    private static final int CELL_POSITION_8 = 8;
    private static final int SCORE_10 = 10;
    private static final int SCORE_5 = 5;

    private PlayerManager playerManager;
    private List<Player> players;
    private MockBoardController boardController;

    @BeforeEach
    void setUp() {
        players = List.of(
            new Player("Alice"),
            new Player("Bob"),
            new Player("Charlie")
        );

        final MockBoardView boardView = new MockBoardView();
        boardController = new MockBoardController(BOARD_SIZE);
        playerManager = new PlayerManagerImpl(players, boardView, boardController);
    }

    @Test
    void testInitialState() {
        assertEquals(NUM_PLAYERS, playerManager.getNumberOfPlayers());
        assertEquals(0, playerManager.getCurrentPlayerIndex());
        assertEquals(0, playerManager.getCurrentPlayerPosition());
    }

    @Test
    void testNextPlayerCycles() {
        assertEquals(0, playerManager.getCurrentPlayerIndex());
        playerManager.nextPlayer();
        assertEquals(1, playerManager.getCurrentPlayerIndex());
        playerManager.nextPlayer();
        assertEquals(2, playerManager.getCurrentPlayerIndex());
        playerManager.nextPlayer();
        assertEquals(0, playerManager.getCurrentPlayerIndex());
    }

    @Test
    void testScoreManagement() {
        assertEquals(0, playerManager.getScore(0));
        playerManager.addScore(0, SCORE_10);
        assertEquals(SCORE_10, playerManager.getScore(0));
        playerManager.addScore(1, SCORE_5);
        assertEquals(SCORE_5, playerManager.getScore(1));
    }

    @Test
    void testPlayTurnNormalCell() {
        boardController.setCellType(CELL_POSITION_3, CellType.NORMAL);
        final TurnResult result = playerManager.playTurn(CELL_POSITION_3);

        assertEquals(CELL_POSITION_3, result.newPosition());
        assertNull(result.minigameToStart());
        assertFalse(result.gameEnded());
        assertEquals(1, playerManager.getCurrentPlayerIndex());
    }

    @Test
    void testPlayTurnMinigameCell() {
        boardController.setCellType(CELL_POSITION_5, CellType.MINIGAME);
        boardController.setMinigame(CELL_POSITION_5, MinigameId.GAME_1);

        final TurnResult result = playerManager.playTurn(CELL_POSITION_5);

        assertEquals(CELL_POSITION_5, result.newPosition());
        assertEquals(MinigameId.GAME_1, result.minigameToStart());
        assertFalse(result.gameEnded());
    }

    @Test
    void testPlayTurnBack2Cell() {
        players.get(0).setPosition(CELL_POSITION_5);
        boardController.setCellType(CELL_POSITION_8, CellType.BACK_2);

        final TurnResult result = playerManager.playTurn(CELL_POSITION_3);

        assertEquals(CELL_POSITION_6, result.newPosition());
        assertEquals(CELL_POSITION_6, players.get(0).getPosition());
    }

    @Test
    void testPlayTurnGameEnd() {
        players.get(0).setPosition(BOARD_SIZE - 3);
        boardController.setCellType(BOARD_SIZE - 1, CellType.NORMAL);

        final TurnResult result = playerManager.playTurn(5);

        assertEquals(BOARD_SIZE - 1, result.newPosition());
        assertTrue(result.gameEnded());
    }

    @Test
    void testApplyMinigameResultWin() {
        players.get(0).setPosition(CELL_POSITION_5);
        playerManager.applyMinigameResult(0, MinigameId.GAME_2, 1);
        assertEquals(CELL_POSITION_6, players.get(0).getPosition());
    }

    @Test
    void testApplyMinigameResultLoss() {
        players.get(0).setPosition(CELL_POSITION_5);
        playerManager.applyMinigameResult(0, MinigameId.GAME_3, 0);
        assertEquals(CELL_POSITION_5 - 1, players.get(0).getPosition());
    }

    private static final class MockBoardView implements BoardView {
        @Override
        public void setPlayerPosition(final int position) {
            // Not needed for tests
        }

        @Override
        public BoardController getController() {
            return null;
        }
    }

    private static final class MockBoardController implements BoardController {
        private final int boardSize;
        private final CellType[] cellTypes;
        private final MinigameId[] minigames;

        MockBoardController(final int boardSize) {
            this.boardSize = boardSize;
            this.cellTypes = new CellType[boardSize];
            this.minigames = new MinigameId[boardSize];

            for (int i = 0; i < boardSize; i++) {
                this.cellTypes[i] = CellType.NORMAL;
            }
        }

        void setCellType(final int index, final CellType type) {
            this.cellTypes[index] = type;
        }

        void setMinigame(final int index, final MinigameId minigame) {
            this.minigames[index] = minigame;
        }

        @Override
        public int getBoardSize() {
            return this.boardSize;
        }

        @Override
        public CellType getCellTypeAt(final int cellIndex) {
            return this.cellTypes[cellIndex];
        }

        @Override
        public MinigameId getMinigameAt(final int cellIndex) {
            return this.minigames[cellIndex];
        }

        @Override
        public MinigameId onPlayerLanded(final int position) {
            return null;
        }
    }
}
