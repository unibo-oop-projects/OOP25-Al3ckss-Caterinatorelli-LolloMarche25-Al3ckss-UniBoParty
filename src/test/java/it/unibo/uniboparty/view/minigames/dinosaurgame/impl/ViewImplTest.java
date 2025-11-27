package it.unibo.uniboparty.view.minigames.dinosaurgame.impl;

import it.unibo.uniboparty.model.minigames.dinosaurgame.impl.ModelImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Dinosaur Game ViewImpl.
 */
class ViewImplTest {

    private ViewImpl view;
    private ModelImpl model;

    /**
     * Set up model and view before each test.
     */
    @BeforeEach
    void setUp() {
        model = new ModelImpl();
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
