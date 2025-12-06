package it.unibo.uniboparty.model.player.api;

/**
 * Represents a player in the game.
 */
public final class Player {

    private final String name;
    private int position;

    /**
     * Creates a player with the given name.
     *
     * @param name the player's name
     */
    public Player(final String name) {
        this.name = name;
        this.position = 0;
    }

    /**
     * @return the player's name
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return the player's current position on the board
     */
    public int getPosition() {
        return this.position;
    }

    /**
     * Sets the player's position on the board.
     *
     * @param position the new position
     */
    public void setPosition(final int position) {
        this.position = position;
    }
}
