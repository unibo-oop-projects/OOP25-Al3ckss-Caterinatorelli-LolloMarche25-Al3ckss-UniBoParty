package it.unibo.uniboparty.view.minigames.typeracergame.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Test class for TypeRacer Game ViewImpl.
 */
class ViewImplTest {

    private ViewImpl view;

    /**
     * Set up view before each test.
     */
    @BeforeEach
    void setUp() {
        view = new ViewImpl();
    }

    /**
     * Test that the view initializes successfully.
     */
    @Test
    void testViewInitializes() {
        assertNotNull(view);
    }

    /**
     * Test that UI components are accessible.
     */
    @Test
    void testComponentsAreAccessible() {
        assertNotNull(view.getTextField());
        assertNotNull(view.getLabel1());
    }

    /**
     * Test that setLabel1 works without throwing.
     */
    @Test
    void testSetLabel1() {
        assertDoesNotThrow(() -> view.setLabel1("Test Word"));
    }

    /**
     * Test that updateTimeLabel works without throwing.
     */
    @Test
    void testUpdateTimeLabel() {
        final int time1 = 15;
        final int time2 = 10;
        final int time3 = 0;

        assertDoesNotThrow(() -> {
            view.updateTimeLabel(time1);
            view.updateTimeLabel(time2);
            view.updateTimeLabel(time3);
        });
    }
}
