package it.unibo.uniboparty.model.minigames.typeracergame.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for TypeRacer Game ModelImpl.
 */
class ModelImplTest {

    private ModelImpl model;

    /**
     * Set up model before each test.
     */
    @BeforeEach
    void setUp() {
        model = new ModelImpl();
    }

    /**
     * Test initial game state.
     */
    @Test
    void testInitialGameState() {
        assertEquals(GameState.READY, model.getState());
        assertEquals(0, model.getPoints());
        assertEquals(GameConfig.INITIAL_TIME_SECONDS, model.getTime());
        assertNull(model.getCurrentWord());
    }

    /**
     * Test that setNewWord generates a valid word from WordList.
     */
    @Test
    void testSetNewWordGeneratesValidWord() {
        model.setNewWord();
        final String word = model.getCurrentWord();

        assertNotNull(word);
        assertTrue(WordList.WORDS.contains(word));
    }

    /**
     * Test that incrementPoints increases the score.
     */
    @Test
    void testIncrementPoints() {
        model.incrementPoints();
        model.incrementPoints();
        model.incrementPoints();

        assertEquals(3, model.getPoints());
    }

    /**
     * Test that decreaseTime reduces time and eventually triggers game over.
     */
    @Test
    void testDecreaseTimeAndGameOver() {
        model.setState(GameState.RUNNING);
        final int initialTime = model.getTime();

        model.decreaseTime();
        assertEquals(initialTime - 1, model.getTime());

        while (model.getTime() > 0) {
            model.decreaseTime();
        }

        assertEquals(GameState.GAME_OVER, model.getState());
    }

    /**
     * Test setState changes game state correctly.
     */
    @Test
    void testSetState() {
        model.setState(GameState.RUNNING);
        assertEquals(GameState.RUNNING, model.getState());

        model.setState(GameState.GAME_OVER);
        assertEquals(GameState.GAME_OVER, model.getState());
    }
}
