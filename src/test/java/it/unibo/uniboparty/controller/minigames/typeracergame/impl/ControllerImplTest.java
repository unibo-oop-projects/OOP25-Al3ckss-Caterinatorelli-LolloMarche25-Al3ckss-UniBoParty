package it.unibo.uniboparty.controller.minigames.typeracergame.impl;

import it.unibo.uniboparty.model.minigames.typeracergame.impl.ModelImpl;
import it.unibo.uniboparty.view.minigames.typeracergame.impl.ViewImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for TypeRacer Game ControllerImpl.
 */
class ControllerImplTest {

    private ModelImpl model;
    private ViewImpl view;
    private ControllerImpl controller;

    /**
     * Set up model, view and controller before each test.
     */
    @BeforeEach
    void setUp() {
        model = new ModelImpl();
        view = new ViewImpl();
        controller = new ControllerImpl(model, view);
    }

    /**
     * Test that the controller initializes successfully.
     */
    @Test
    void testControllerInitializes() {
        assertNotNull(controller);
        assertNotNull(model);
        assertNotNull(view);
    }

    /**
     * Test that model is working correctly after init.
     */
    @Test
    void testModelState() {
        assertEquals(0, model.getPoints());
        assertTrue(model.getTime() > 0);
    }

    /**
     * Test that view components exist.
     */
    @Test
    void testViewComponents() {
        assertNotNull(view.getTextField());
        assertNotNull(view.getLabel1());
    }
}
