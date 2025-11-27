package it.unibo.uniboparty.model.startgamemenu.impl;

import java.util.List;
import it.unibo.uniboparty.model.startgamemenu.api.LogicStartGame;
import java.util.ArrayList;

/**
 * Implementation of the game start menu logic.
 * It manages the list of players and determines if the game can be started
 * based on the number of players present (from 3 to 5, inclusive).
 */
public class LogicStartGameImpl implements LogicStartGame {

    private static final int MIN_PLAYERS = 3;
    private static final int MAX_PLAYERS = 5;
    private List<String> players = new ArrayList<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public String getStartMessage() {
        if (canStartGame()) {
            return "Gioco avviato con " + players.size() + " giocatori!";
        } else {
            return "Inserisci da 3 a 5 giocatori per iniziare!";
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getPlayers() {
        return List.copyOf(this.players);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPlayers(final List<String> players) {
        this.players = new ArrayList<>();
        for (final String player : players) {
            if (player != null && !player.trim().isBlank()) {
                this.players.add(player.trim());
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canStartGame() {
        return this.players.size() >= MIN_PLAYERS && this.players.size() <= MAX_PLAYERS;
    }
}
