package it.unibo.uniboparty.view.minigames.hangman.api;

import java.awt.event.ActionListener;

/**
 * Interface that defines the View for the game.
 * Methods to update the interface and sets listeners for events.
 */

public interface HangmanView {

    /**
     * Updates the label that show the masked word.
     *
     * @param text Formatted string (ex. "C _ A _").
     */
    void updateMaskedWord(String text);

    /**
     * Updates the Hangman graphics every time the player chooses wrong.
     *
     * @param errors number of current errors.
     */
    void updateMan(int errors);

    /**
     * Disables a letter button on the UI.
     * Method used after the player chose a letter on the tile corresponding to that letter.
     *
     * @param letter The letter corresponds to the button that needs to be disabled.
     */
    void disableLetterButton(char letter);

    /**
     * Shows a victory pop-up and ends the game.
     *
     * @param secretWord The guessed word.
     */
    void showVictory(String secretWord);

    /**
     * Shows a defeat pop-up and ends the game.
     *
     * @param secretWord The word the player had to guess.
     */
    void showDefeat(String secretWord);

    /**
     * Register a listener that manages clicks on the letters buttons (A-Z).
     *
     * @param listener The action to execute. (Described in the Controller).
     */
    void addLetterListener(ActionListener listener);

    /**
     * Register a listener that manages clicks on the "Guess the word" button.
     *
     * @param listener The action to execute. (Described in the Controller).
     */
    void addGuessWordListener(ActionListener listener);

    /**
     * Gets the guess of the player inserted in the "Guess the word" field.
     *
     * @return String inserted by the player.
     */
    String getGuessWordInput();
}
