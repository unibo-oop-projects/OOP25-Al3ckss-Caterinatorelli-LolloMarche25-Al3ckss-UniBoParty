package it.unibo.uniboparty.application;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import it.unibo.uniboparty.view.board.impl.BoardViewImpl;

/**
 * Swing application entry point for the UniBoParty main board.
 * Opens a window showing the board.
 */
public final class BoardApp {

    /**
     * Private constructor to prevent instantiation.
     */
    private BoardApp() {
        // Utility class: no instances allowed
    }

    /**
     * Main entry point.
     *
     * @param args command line arguments
     */
    public static void main(final String[] args) {
        // Always start Swing applications on the Event Dispatch Thread
        SwingUtilities.invokeLater(BoardApp::createAndShowWindow);
    }

    /**
     * Creates the main window and displays the BoardView inside it.
     */
    private static void createAndShowWindow() {
        final JFrame frame = new JFrame("UniBoParty - Board");

        // Add the BoardView (Swing version)
        frame.setContentPane(new BoardViewImpl());

        frame.pack();                  // Calculate size based on content
        frame.setLocationRelativeTo(null); // Center on screen
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
