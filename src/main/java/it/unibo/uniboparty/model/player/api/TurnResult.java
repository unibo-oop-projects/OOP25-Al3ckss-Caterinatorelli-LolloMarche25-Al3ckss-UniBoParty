package it.unibo.uniboparty.model.player.api;

import it.unibo.uniboparty.utilities.MinigameId;

/**
 * Represents the result of a player's turn.
 *
 * @param newPosition the board position reached by the player after the turn
 * @param minigameToStart the minigame that should start after moving, or null if none
 * @param gameEnded true if the game has ended after this turn
 */
public record TurnResult(
        int newPosition,
        MinigameId minigameToStart,
        boolean gameEnded
) { }

