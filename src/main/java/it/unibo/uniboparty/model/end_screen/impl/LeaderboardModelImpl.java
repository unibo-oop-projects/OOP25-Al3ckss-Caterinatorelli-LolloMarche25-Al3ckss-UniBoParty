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
 * It can work in two ways:
 * <ul>
 *   <li>using dummy data (no-arg constructor), or</li>
 *   <li>using the real players passed from the main board.</li>
 * </ul>
 * </p>
 */
public final class LeaderboardModelImpl implements LeaderboardModel {

    private final List<Player> players;

    /**
     * Creates a leaderboard with dummy data (test mode).
     */
    public LeaderboardModelImpl() {
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
        this.players = List.copyOf(allPlayers);
    }

    /**
     * Creates a leaderboard from the real players of the match.
     *
     * @param players list of players (name + score)
     */
    public LeaderboardModelImpl(final List<Player> players) {
        final List<Player> copy = new ArrayList<>(players);
        copy.sort(Comparator.comparingInt(Player::getScore).reversed());
        this.players = List.copyOf(copy);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Player> getTopPlayers() {
        final int limit = Math.min(this.players.size(), 3);
        return List.copyOf(this.players.subList(0, limit));
    }
}
