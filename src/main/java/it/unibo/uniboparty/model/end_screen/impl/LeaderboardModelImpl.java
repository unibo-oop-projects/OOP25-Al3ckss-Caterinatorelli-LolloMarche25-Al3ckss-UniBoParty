package it.unibo.uniboparty.model.end_screen.impl;

import it.unibo.uniboparty.model.end_screen.api.LeaderboardModel;
import it.unibo.uniboparty.model.end_screen.api.Player;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Concrete implementation of the {@link LeaderboardModel} interface.
 *
 * <p>
 * This class provides the data logic for the end-game leaderboard.
 * Currently, it uses mock data (hardcoded players and scores) to simulate
 * a game session result, sorting them to determine the winners.
 */
public final class LeaderboardModelImpl implements LeaderboardModel {

    /**
     * Retrieves the top ranked players.
     *
     * <p>
     * This method generates a list of dummy players with pre-defined scores.
     * It sorts the list in descending order (highest score first) and returns
     * a sublist containing up to the top 3 players (the podium).
     *
     * @return a {@link List} of {@link Player} objects representing the top 3 players.
     */
    @Override
    public List<Player> getTopPlayers() {
        final int playerOne = 150;
        final int playerTwo = 120;
        final int playerThree = 200;
        final int playerFour = 80;
        final int playerFive = 180;
        final List<Player> allPlayers = new ArrayList<>();
        allPlayers.add(new Player("Mario", playerOne));
        allPlayers.add(new Player("Carlo", playerTwo));
        allPlayers.add(new Player("Gaia", playerThree));
        allPlayers.add(new Player("Rachele", playerFour));
        allPlayers.add(new Player("Anna", playerFive));

        allPlayers.sort(Comparator.comparingInt(Player::getScore).reversed());

        return allPlayers.subList(0, Math.min(allPlayers.size(), 3));
    }
}
