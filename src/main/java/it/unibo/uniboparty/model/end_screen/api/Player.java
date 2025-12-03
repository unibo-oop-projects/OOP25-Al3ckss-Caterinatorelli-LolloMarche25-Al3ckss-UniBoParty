package it.unibo.uniboparty.model.end_screen.api;

import java.io.Serial;
import java.io.Serializable;

/**
 * Simple immutable class representing a Player score.
 */
public final class Player implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private final String name;
    private final int score;

    /**
     * @param name The player's name.
     * @param score The player's score.
     */
    public Player(final String name, final int score) {
        this.name = name;
        this.score = score;
    }

    /**
     * @return The player's name.
     */
    public String getName() {
        return name;
    }

    /**
     * @return The player's score.
     */
    public int getScore() {
        return score;
    }
}
