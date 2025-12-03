package it.unibo.uniboparty.model.player.api;

import it.unibo.uniboparty.utilities.MinigameId;

/**
 * Represents the result of a player's turn.
 *
 * Contains:
 * - the new board position reached by the player,
 * - the minigame to start (if any),
 * - whether the game has ended.
 */
public record TurnResult(
        int newPosition,
        MinigameId minigameToStart,
        boolean gameEnded
) {}
