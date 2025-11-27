package it.unibo.uniboparty.model.dice.api;

/**
 * Interface defining the behavior of the Dice Model.
 */
public interface DiceModel {
    /**
     * Rolls the two dice generating random numbers between 1 and 6.
     */
    void roll();

    /**
     * @return The value of the first die (1-6).
     */
    int getDie1();

    /**
     * @return The value of the second die (1-6).
     */
    int getDie2();

    /**
     * @return The sum of the two dice.
     */
    int getTotal();
}
