package it.unibo.uniboparty.model.minigames.hangman.impl;

import it.unibo.uniboparty.model.minigames.hangman.api.HangmanModel;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Concrete implementation of the logic behind the game.
 * It uses a static array filled with words and a Set to keep track of guessed letters.
 */
public final class HangmanModelImpl implements HangmanModel {

    private static final Random RANDOM = new Random();

    // ...

    private static final String[] WORDS = {
            "INFORMATICA", "SVILUPPATORE", "JAVA", "INTERFACCIA",
            "ALGORITMO", "UNIVERSITA", "PROGRAMMAZIONE", "IMPICCATO",
    };

    private static final int MAX_ERRORS = 6;

    private String secretWord;

    /**
     * Uses a Set to guarantee a quick search 0(1) and avoid duplicates.
     */

    private Set<Character> guessedLetters;
    private int errors;

    /**
     * Method to start the game.
     */
    public HangmanModelImpl() {
        startNewGame();
    }

    @Override
    public void startNewGame() {
        secretWord = WORDS[RANDOM.nextInt(WORDS.length)];
        guessedLetters = new HashSet<>();
        errors = 0;
    }

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

    @Override
    public boolean isGameWon() {
        for (final char c : secretWord.toCharArray()) {

            if (!guessedLetters.contains(c)) {
                return false;
            }
        }
        return true;

    }

    @Override
    public boolean isGameOver() {
        return errors >= MAX_ERRORS;
    }
}
