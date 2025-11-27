package it.unibo.uniboparty.controller.minigames.dinosaurgame.impl;

import it.unibo.uniboparty.model.minigames.dinosaurgame.impl.ModelImpl;
import it.unibo.uniboparty.view.minigames.dinosaurgame.impl.ViewImpl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for Dinosaur Game ControllerImpl.
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
        view = new ViewImpl(model);
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
     * Test that start and stop methods can be called.
     */
    @Test
    void testStartAndStop() {
        assertDoesNotThrow(controller::start);
        assertDoesNotThrow(controller::stop);
    }

    /**
     * Test that multiple start/stop cycles work without errors.
     */
    @Test
    void testMultipleStartStopCycles() {
        assertDoesNotThrow(() -> {
            for (int i = 0; i < 3; i++) {
                controller.start();
                controller.stop();
            }
        });
    }
}
