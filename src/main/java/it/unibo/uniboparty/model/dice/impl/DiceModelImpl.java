package it.unibo.uniboparty.model.dice.impl;

import it.unibo.uniboparty.model.dice.api.DiceModel;
import java.util.Random;

/**
 * Concrete implementation of the {@link DiceModel} interface.
 *
 * <p>
 * This class represents the model for a pair of dice. It is responsible for
 * generating random values (rolling) and maintaining the state of the current
 * roll (values of individual dice and the total sum).
 */
public final class DiceModelImpl implements DiceModel {

    /**
     * The maximum value for a standard 6-sided die.
     */
    private static final int MAX_DIE_VALUE = 6;

    /**
     * Shared Random instance to generate values, preventing resource overhead.
     */
    private static final Random RANDOM = new Random();

    private int die1;
    private int die2;

    /**
     * Constructs a new {@code DiceModelImpl}.
     *
     * <p>
     * The constructor immediately rolls the dice to ensure the model is initialized
     * with valid values (1-6) rather than the default integer value of 0.
     */
    public DiceModelImpl() {
        roll();
    }

    /**
     * Generates new random values for both dice.
     *
     * <p>
     * Uses {@link Random#nextInt(int)} to generate a number between 0 (inclusive)
     * and {@link #MAX_DIE_VALUE} (exclusive), then adds 1 to shift the range to [1, 6].
     */
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
