package it.unibo.uniboparty.view.end_screen.api;

import it.unibo.uniboparty.model.end_screen.api.Player;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * View of the leaderboard.
 */
public interface LeaderboardView {

    /**
     * Initializes the podium with player data.
     *
     * @param players The list of top players.
     */
    void showPodium(List<Player> players);

    /**
     * Adds a listener to the "Back to Menu" button.
     *
     * @param listener The action to perform.
     */
    void addBackListener(ActionListener listener);

    /**
     * Closes the window.
     */
    void close();
}
