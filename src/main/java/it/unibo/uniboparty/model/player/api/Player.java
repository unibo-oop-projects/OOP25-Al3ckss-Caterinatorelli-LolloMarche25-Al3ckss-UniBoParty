package it.unibo.uniboparty.model.player.api;

public final class Player {

    private final String name;

    public Player(final String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
