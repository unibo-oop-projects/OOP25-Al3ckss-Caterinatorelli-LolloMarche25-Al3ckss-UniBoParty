package it.unibo.uniboparty.application;

import javax.swing.SwingUtilities;
import it.unibo.uniboparty.view.startgamemenu.StartGameGui;

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
        SwingUtilities.invokeLater(() -> new StartGameGui().setVisible(true));
    }
}
