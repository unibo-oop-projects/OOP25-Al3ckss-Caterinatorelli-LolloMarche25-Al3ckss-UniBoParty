package it.unibo.uniboparty.model.minigames.hangman.impl;

import it.unibo.uniboparty.model.minigames.hangman.api.HangmanModel;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Concrete implementation of the {@link HangmanModel} interface.
 *
 * <p>
 * This class manages the core logic of the Hangman game. It handles the selection
 * of the secret word from a predefined static pool, tracks the player's guessed
 * letters, and determines the win/loss state based on the error count.
 */
public final class HangmanModelImpl implements HangmanModel {

    private static final Random RANDOM = new Random();

    /**
     * A predefined pool of words for the game.
     */
    private static final String[] WORDS = {
            "INFORMATICA", "SVILUPPATORE", "JAVA", "INTERFACCIA",
            "ALGORITMO", "UNIVERSITA", "PROGRAMMAZIONE", "IMPICCATO",
    };

    private static final int MAX_ERRORS = 6;

    private String secretWord;

    /**
     * Stores the characters guessed by the player so far.
     *
     * <p>
     * A {@link Set} is used here to guarantee O(1) lookup time when checking
     * if a letter has already been guessed and to automatically prevent duplicate entries.
     */
    private Set<Character> guessedLetters;
    private int errors;

    /**
     * Constructs the model and immediately starts a new game session.
     */
    public HangmanModelImpl() {
        startNewGame();
    }

    /**
     * Resets the game state.
     *
     * <p>
     * Selects a new random secret word from the {@code WORDS} array, clears
     * the history of guessed letters, and resets the error counter to zero.
     */
    @Override
    public void startNewGame() {
        secretWord = WORDS[RANDOM.nextInt(WORDS.length)];
        guessedLetters = new HashSet<>();
        errors = 0;
    }

    /**
     * Processes a single letter guess.
     *
     * <p>
     * The input is converted to uppercase. If the letter is not in the secret word,
     * the error count is incremented.
     *
     * @param letter the character guessed by the player.
     * @return {@code true} if the letter is in the word or was already guessed, {@code false} otherwise.
     */
    @Override
    public boolean guessLetter(final char letter) {

        final char upperLetter = Character.toUpperCase(letter);
        if (guessedLetters.contains(upperLetter)) {
            return true;
        }

        guessedLetters.add(upperLetter);
        if (!secretWord.contains(String.valueOf(upperLetter))) {
            errors++;
            return false;
        }
        return true;
    }

    /**
     * Attempts to guess the entire secret word at once.
     *
     * <p>
     * If the guess is correct, all letters are revealed (added to the set),
     * effectively resulting in a win. If the guess is incorrect, the error count
     * is set to the maximum, resulting in an immediate game over.
     *
     * @param word the full word string guessed by the player.
     * @return {@code true} if the word matches the secret word; {@code false} otherwise.
     */
    @Override
    public boolean guessWord(final String word) {
        if (word.equalsIgnoreCase(secretWord)) {
            for (final char c : secretWord.toCharArray()) {
                guessedLetters.add(c);
            }
            return true;
        } else {
            errors = MAX_ERRORS;
            return false;
        }
    }

    /**
     * Generates the current display string of the secret word.
     *
     * <p>
     * Letters that have been guessed are revealed, while unguessed letters
     * are replaced by underscores (e.g., "J _ V _").
     *
     * @return the formatted string representing the masked word.
     */
    @Override
    public String getMaskedWord() {
        final StringBuilder sb = new StringBuilder();
        for (final char c : secretWord.toCharArray()) {
            if (guessedLetters.contains(c)) {
                sb.append(c).append(' ');
            } else {
                sb.append("_ ");
            }
        }
        return sb.toString().trim();
    }

    @Override
    public String getSecretWord() {
        return secretWord;
    }

    @Override
    public int getErrorCount() {
        return errors;
    }

    @Override
    public int getMaxErrors() {
        return MAX_ERRORS;
    }

    /**
     * Checks if the game has been won.
     *
     * <p>
     * Iterates through the secret word to verify if every character
     * is present in the set of guessed letters.
     *
     * @return {@code true} if all letters have been revealed; {@code false} otherwise.
     */
    @Override
    public boolean isGameWon() {
        for (final char c : secretWord.toCharArray()) {

            if (!guessedLetters.contains(c)) {
                return false;
            }
        }
        return true;

    }

    /**
     * Checks if the game is over due to excessive errors.
     *
     * @return {@code true} if the error count has reached or exceeded the maximum limit.
     */
    @Override
    public boolean isGameOver() {
        return errors >= MAX_ERRORS;
    }
}
