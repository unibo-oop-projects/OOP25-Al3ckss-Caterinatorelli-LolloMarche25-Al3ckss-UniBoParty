package it.unibo.uniboparty.model.minigames.hangman.api;

/**
 * Model that start a game and manages its status.
 */
public interface HangmanModel {

    /**
     * Starts a new game.
     * Chooses a new random word, resets errors and clears all the letters guessed before.
     */
    void startNewGame();

    /**
     * Tries to guess a single letters.
     *
     * @param letter The letter chosen by the player.
     * @return true if the letter is in the secret word, false if it's not (error).
     */
    boolean guessLetter(char letter);

    /**
     * Tries to guess the whole word in one shot.
     *
     * <p>
     * If it's correct the player wins the game directly.
     * If it's wrong the player gets  the maximum penatly and loses.
     *
     * @param word Player's guess.
     * @return true if the guess corresponds to the secret word, otherwise false.
     */
    boolean guessWord(String word);

    /**
     * Returns the "masked" secret word.
     * Guessed letters will be unveiled as the player guess, the missing ones will be replaced with an undescore "_"
     *
     * @return Format of the string (ex. "C _ A _").
     */
    String getMaskedWord();

    /**
     * @return the whole secret word (in case of lose or win).
     */
    String getSecretWord();

    /**
     * Return the number of errors in real time.
     *
     * @return an integer count of the errors.
     */
    int getErrorCount();

    /**
     * Returns the maximum ammount of errors given to the player before ending the game.
     *
     * @return Max. number of errors.
     */
    int getMaxErrors();

    /**
     * Verifies if the player won.
     *
     * @return true if all the letters are guessed right, false otherwise.
     */
    boolean isGameWon();

    /**
     * Verifies if the player lost.
     *
     * @return true if the player reached the maximum ammount of errors possible.
     */
    boolean isGameOver();
}
