package it.unibo.uniboparty.model.startgamemenu.api;

import java.util.List;

/**
 * Interface that defines the necessary logic for managing the game start menu.
 * It includes methods to set and retrieve players, check the possibility of
 * starting the game, and obtain a status message for the user.
 */
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
}
