package it.unibo.uniboparty.model.dice.impl;

import it.unibo.uniboparty.model.dice.api.DiceModel;
import java.util.Random;

/**
 * Concrete implementation of the dice Model.
 */
public final class DiceModelImpl implements DiceModel {

    private static final int MAX_DIE_VALUE = 6;
    private static final Random RANDOM = new Random();

    private int die1;
    private int die2;

    /**
     * Dice throw launch.
     */
    public DiceModelImpl() {
        // Inizializziamo con un lancio
        roll();
    }

    @Override
    public void roll() {
        // nextInt(6) genera 0-5, quindi aggiungiamo 1
        this.die1 = RANDOM.nextInt(MAX_DIE_VALUE) + 1;
        this.die2 = RANDOM.nextInt(MAX_DIE_VALUE) + 1;
    }

    @Override
    public int getDie1() {
        return die1;
    }

    @Override
    public int getDie2() {
        return die2;
    }

    @Override
    public int getTotal() {
        return die1 + die2;
    }
}
