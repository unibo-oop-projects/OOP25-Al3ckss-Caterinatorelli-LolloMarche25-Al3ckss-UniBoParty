package it.unibo.uniboparty.application;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import it.unibo.uniboparty.controller.minigames.memory.api.MemoryGameController;
import it.unibo.uniboparty.controller.minigames.memory.impl.MemoryGameControllerImpl;
import it.unibo.uniboparty.view.minigames.memory.api.MemoryGameView;
import it.unibo.uniboparty.view.minigames.memory.impl.MemoryGameViewImpl;

/**
 * Simple Swing application that launches the Memory minigame.
 */
public final class MemoryGameApp {

    private static final int FRAME_WIDTH = 600;
    private static final int FRAME_HEIGHT = 700;

    private MemoryGameApp() {
        // not called
    }

    /**
     * Launches the application.
     *
     * @param args ignored
     */
    public static void main(final String[] args) {
        SwingUtilities.invokeLater(MemoryGameApp::createAndShowWindow);
    }

    /**
     * Creates the main Swing window and shows the Memory game.
     */
    private static void createAndShowWindow() {
        final JFrame frame = new JFrame("Memory Game");

        // Create the view
        final MemoryGameView view = new MemoryGameViewImpl();

        // Create the controller (model is created inside the controller)
        new MemoryGameControllerImpl(view);

        // Add the view to the frame
        frame.setContentPane((MemoryGameViewImpl) view);

        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setLocationRelativeTo(null); // center on screen
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
