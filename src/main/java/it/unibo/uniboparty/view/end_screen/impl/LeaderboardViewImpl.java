package it.unibo.uniboparty.view.end_screen.impl;

import it.unibo.uniboparty.model.end_screen.api.Player;
import it.unibo.uniboparty.view.end_screen.api.LeaderboardView;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

/**
 * Concrete implementation of the {@link LeaderboardView} interface.
 *
 * <p>
 * This class manages the main window for the game's final leaderboard.
 * It sets up a frame with a central area for the podium display and a
 * bottom navigation bar to return to the main menu.
 */
public class LeaderboardViewImpl implements LeaderboardView {

    private static final int BUTTON_WIDTH = 200;
    private static final int BUTTON_HEIGHT = 50;
    private static final int WIDTH = 600;
    private static final int HEIGHT = 500;
    private final JFrame frame;
    private final JButton backButton;

    /**
     * Constructs the Leaderboard View.
     *
     * <p>
     * Initializes the main {@link JFrame}, sets its dimensions and layout,
     * and prepares the bottom panel containing the "Back to Menu" button.
     */
    public LeaderboardViewImpl() {
        frame = new JFrame("Classifica - Podio");
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        backButton = new JButton("Torna al Menu");
        backButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));

        // Pannello pulsante in basso
        final JPanel bottomPanel = new JPanel();
        bottomPanel.add(backButton);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    /**
     * Displays the podium with the top players.
     *
     * <p>
     * It creates a new {@link PodiumPanel}, adds it to the center of the frame,
     * and forces a UI refresh to ensure the graphics are rendered correctly.
     *
     * @param players The list of {@link Player} objects to be displayed on the podium.
     */
    @Override
    public void showPodium(final List<Player> players) {
        final PodiumPanel podium = new PodiumPanel(players);
        frame.add(podium, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }

    /**
     * Registers a listener for the "Back" button.
     *
     * @param listener The {@link java.awt.event.ActionListener} to handle the button click.
     */
    @Override
    public void addBackListener(final java.awt.event.ActionListener listener) {
        backButton.addActionListener(listener);
    }

    /**
     * Closes the leaderboard window and releases its resources.
     */
    @Override
    public void close() {
        frame.dispose();
    }
}
