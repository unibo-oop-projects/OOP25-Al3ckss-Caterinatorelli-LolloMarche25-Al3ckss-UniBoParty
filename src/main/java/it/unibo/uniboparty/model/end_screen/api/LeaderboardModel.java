package it.unibo.uniboparty.model.end_screen.api;

import java.util.List;

/**
 * Interface for the Leaderboard Model.
 */
@FunctionalInterface
public interface LeaderboardModel {
    /**
     * Retrieves the top 3 players sorted by score.
     *
     * @return A list of the top players.
     */
    List<Player> getTopPlayers();
}
