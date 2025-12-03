package it.unibo.uniboparty.view.dice.api;

import java.awt.event.ActionListener;

/**
 * Interface defining the View for the Dice Rolling minigame.
 */
public interface DiceView {

    /**
     * Updates the visual representation of the dice.
     *
     * @param d1 Value of the first die (1-6).
     * @param d2 Value of the second die (1-6).
     */
    void setDiceValues(int d1, int d2);

    /**
     * Updates the text label showing the total sum.
     *
     * @param text The text to display (e.g. "Total: 7").
     */
    void setTotalText(String text);

    /**
     * Registers a listener for the "ROLL" button.
     *
     * @param listener The controller action to perform.
     */
    void addRollListener(ActionListener listener);

    /**
     * Closes the dice window.
     */
    void close();
}
