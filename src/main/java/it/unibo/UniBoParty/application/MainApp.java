package it.unibo.uniboparty.application;

import it.unibo.uniboparty.controller.minigames.tetris.api.TetrisController;
import it.unibo.uniboparty.controller.minigames.tetris.impl.TetrisControllerImpl;

/**
 * Main application class for UniBoParty.
 */
public final class MainApp {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private MainApp() {

    }

    /**
     * Main method to start the application.
     * 
     * @param args command-line arguments
     */
    public static void main(final String[] args) {
        final TetrisController controller = new TetrisControllerImpl();
        controller.startGame();
    }
}
