package it.unibo.uniboparty.model.startgamemenu.impl;
import java.util.List;

import it.unibo.uniboparty.model.startgamemenu.api.LogicStartGame;

import java.util.ArrayList;

public class LogicStartGameImpl implements LogicStartGame {

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
		return this.players;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setPlayers(List<String> players) {
		this.players = new ArrayList<>();
        for (String player : players) {
            if (player != null && !player.trim().isEmpty()) {
                this.players.add(player.trim());
            }
        }
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean canStartGame() {
		return this.players.size() >= 3 && this.players.size() <= 5;
	}
}
