package unibo.party.board;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import unibo.party.board.view.BoardView;

/**
 * Swing application entry point for the UniBoParty main board.
 * Opens a window showing the board.
 */
public final class BoardApp {

    /** Width of the application window. */
    private static final int WINDOW_WIDTH = 650;

    /** Height of the application window. */
    private static final int WINDOW_HEIGHT = 250;

    /**
     * Main entry point.
     *
     * @param args command line arguments
     */
    public static void main(final String[] args) {
        // Always start Swing applications on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            createAndShowWindow();
        });
    }

    /**
     * Creates the main window and displays the BoardView inside it.
     */
    private static void createAndShowWindow() {
        final JFrame frame = new JFrame("UniBoParty - Board");

        // Add the BoardView (Swing version)
        frame.setContentPane(new BoardView());

        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setLocationRelativeTo(null); // Center on screen
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
