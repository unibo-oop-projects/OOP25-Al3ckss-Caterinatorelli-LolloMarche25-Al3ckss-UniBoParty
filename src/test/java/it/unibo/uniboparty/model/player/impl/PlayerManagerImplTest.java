package it.unibo.uniboparty.model.player.impl;

import it.unibo.uniboparty.model.player.api.PlayerManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for PlayerManagerImpl.
 */
class PlayerManagerImplTest {

    private static final int NUM_PLAYERS = 4;
    private static final int DEFAULT_STEPS = 5;
    private static final int DEFAULT_BOARD_SIZE = 20;
    private static final int SMALL_BOARD_SIZE = 10;
    private static final int LARGE_STEPS = 15;
    private static final int SCORE_A = 10;
    private static final int SCORE_B = 5;
    private static final int NEG_DELTA = -3;
    private static final int EXPECTED_SCORE = 7;

    private PlayerManager playerManager;

    /**
     * Initialize a new PlayerManager before each test.
     */
    @BeforeEach
    void setUp() {
        playerManager = new PlayerManagerImpl(NUM_PLAYERS);
    }

    /**
     * Test that PlayerManager initializes with correct number of players.
     */
    @Test
    void testInitialization() {
        assertEquals(NUM_PLAYERS, playerManager.getNumberOfPlayers());
        assertEquals(0, playerManager.getCurrentPlayerIndex());
    }

    /**
     * Test initial positions are zero.
     */
    @Test
    void testInitialPositions() {
        for (int i = 0; i < NUM_PLAYERS; i++) {
            assertEquals(0, playerManager.getPlayerPosition(i));
        }
    }

    /**
     * Test initial scores are zero.
     */
    @Test
    void testInitialScores() {
        for (int i = 0; i < NUM_PLAYERS; i++) {
            assertEquals(0, playerManager.getScore(i));
        }
    }

    /**
     * Test moving the current player.
     */
    @Test
    void testMoveCurrentPlayer() {
        final int steps = DEFAULT_STEPS;
        final int boardSize = DEFAULT_BOARD_SIZE;
        final int newPos = playerManager.moveCurrentPlayer(steps, boardSize);

        assertEquals(steps, newPos);
        assertEquals(steps, playerManager.getCurrentPlayerPosition());
    }

    /**
     * Test position capped at board size.
     */
    @Test
    void testPositionCappedAtBoardSize() {
        final int boardSize = SMALL_BOARD_SIZE;
        final int steps = LARGE_STEPS;
        final int newPos = playerManager.moveCurrentPlayer(steps, boardSize);

        assertEquals(boardSize - 1, newPos);
    }

    /**
     * Test nextPlayer advances the turn correctly.
     */
    @Test
    void testNextPlayer() {
        assertEquals(0, playerManager.getCurrentPlayerIndex());
        playerManager.nextPlayer();
        assertEquals(1, playerManager.getCurrentPlayerIndex());
        playerManager.nextPlayer();
        assertEquals(2, playerManager.getCurrentPlayerIndex());
    }

    /**
     * Test nextPlayer wraps around after last player.
     */
    @Test
    void testNextPlayerWrapAround() {
        for (int i = 0; i < NUM_PLAYERS; i++) {
            playerManager.nextPlayer();
        }
        assertEquals(0, playerManager.getCurrentPlayerIndex());
    }

    /**
     * Test adding score to a player.
     */
    @Test
    void testAddScore() {
        playerManager.addScore(0, SCORE_A);
        assertEquals(SCORE_A, playerManager.getScore(0));

        playerManager.addScore(1, SCORE_B);
        assertEquals(SCORE_B, playerManager.getScore(1));
    }

    /**
     * Test that negative scores can be added.
     */
    @Test
    void testAddNegativeScore() {
        playerManager.addScore(0, SCORE_A);
        playerManager.addScore(0, NEG_DELTA);
        assertEquals(EXPECTED_SCORE, playerManager.getScore(0));
    }

    /**
     * Test exception for invalid player index in addScore.
     */
    @Test
    void testAddScoreInvalidIndex() {
        assertThrows(IllegalArgumentException.class, () -> {
            playerManager.addScore(-1, SCORE_B);
        });
    }

    /**
     * Test exception for invalid number of players.
     */
    @Test
    void testInvalidNumberOfPlayers() {
        assertThrows(IllegalArgumentException.class, () -> {
            new PlayerManagerImpl(0);
        });
    }
}
