package it.unibo.uniboparty.model.minigames.dinosaurgame.impl;

/**
 * Represents the possible states of the game.
 */
public enum GameState {
    /** The game is running normally. */
    RUNNING,

    /** The player won by surviving long enough. */
    WIN,

    /** The game has ended. */
    GAME_OVER
}
