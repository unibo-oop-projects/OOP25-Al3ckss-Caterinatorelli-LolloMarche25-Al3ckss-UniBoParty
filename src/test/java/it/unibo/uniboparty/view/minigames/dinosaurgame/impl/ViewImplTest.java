package it.unibo.uniboparty.view.minigames.dinosaurgame.impl;

import it.unibo.uniboparty.model.minigames.dinosaurgame.impl.ModelImpl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for Dinosaur Game ViewImpl.
 */
class ViewImplTest {

    private ViewImpl view;

    /**
     * Set up model and view before each test.
     */
    @BeforeEach
    void setUp() {
        final ModelImpl model = new ModelImpl();
        view = new ViewImpl(model);
    }

    /**
     * Test that the view initializes successfully.
     */
    @Test
    void testViewInitializes() {
        assertNotNull(view);
    }

    /**
     * Test that game over state is initially false.
     */
    @Test
    void testGameOverStateInitiallyFalse() {
        assertFalse(view.isGameOver());
    }

    /**
     * Test that showing game over overlay sets the game over state to true.
     */
    @Test
    void testShowGameOverOverlay() {
        view.showGameOverOverlay();
        assertTrue(view.isGameOver());
    }

    /**
     * Test that repaint and focus operations work without throwing exceptions.
     */
    @Test
    void testRepaintAndFocusOperations() {
        assertDoesNotThrow(() -> {
            view.repaint();
            view.setPanelFocusable(true);
            view.requestPanelFocus();
        });
    }
}
