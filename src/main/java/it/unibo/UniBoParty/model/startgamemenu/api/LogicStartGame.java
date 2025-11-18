package it.unibo.uniboparty.model.startgamemenu.api;

import java.util.List;

public interface LogicStartGame {

    /**
     * Sets the list of the game players.
     * 
     * @param players the list of player names;
     */
    void setPlayers(List<String> players);

    /**
     * Returns the name of the players.
     * 
     * @return List containing the player names
     */
    List<String> getPlayers();

    /**
     * Checks if the number of players is valid (min 3, max 5).
     * 
     * @return True if the game can start, False otherwise
     */
    boolean canStartGame();

    /**
     * Returns a message to show at the user.
     * 
     * @return a message to show at the user.
     */
    String getStartMessage();
}
