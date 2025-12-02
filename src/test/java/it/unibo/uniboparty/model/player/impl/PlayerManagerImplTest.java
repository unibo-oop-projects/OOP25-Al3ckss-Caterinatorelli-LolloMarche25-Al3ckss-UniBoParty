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

    private PlayerManager playerManager;
    private static final int NUM_PLAYERS = 4;

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
        final int steps = 5;
        final int boardSize = 20;
        final int newPos = playerManager.moveCurrentPlayer(steps, boardSize);

        assertEquals(steps, newPos);
        assertEquals(steps, playerManager.getCurrentPlayerPosition());
    }

    /**
     * Test position capped at board size.
     */
    @Test
    void testPositionCappedAtBoardSize() {
        final int boardSize = 10;
        final int steps = 15;
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
        playerManager.addScore(0, 10);
        assertEquals(10, playerManager.getScore(0));

        playerManager.addScore(1, 5);
        assertEquals(5, playerManager.getScore(1));
    }

    /**
     * Test that negative scores can be added.
     */
    @Test
    void testAddNegativeScore() {
        playerManager.addScore(0, 10);
        playerManager.addScore(0, -3);
        assertEquals(7, playerManager.getScore(0));
    }

    /**
     * Test exception for invalid player index in addScore.
     */
    @Test
    void testAddScoreInvalidIndex() {
        assertThrows(IllegalArgumentException.class, () -> {
            playerManager.addScore(-1, 5);
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
