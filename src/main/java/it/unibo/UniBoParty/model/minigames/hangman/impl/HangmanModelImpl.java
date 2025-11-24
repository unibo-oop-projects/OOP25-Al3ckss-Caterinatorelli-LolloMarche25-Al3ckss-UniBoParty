package it.unibo.UniBoParty.model.minigames.hangman.impl;

import it.unibo.UniBoParty.model.minigames.hangman.api.HangmanModel;
import java.util.*;

/**
 * Concrete implementation of the logic behind the game.
 * It uses a static array filled with words and a Set to keep track of guessed letters.
 */
public class HangmanModelImpl implements HangmanModel {

    private static final String[] WORDS = {
            "INFORMATICA", "SVILUPPATORE", "JAVA", "INTERFACCIA",
            "ALGORITMO", "UNIVERSITA", "PROGRAMMAZIONE", "IMPICCATO"
    };

    private String secretWord;
    /**
     * Uses a Set to guarantee a quick search 0(1) and avoid duplicates.
     */
    private Set<Character> guessedLetters;
    private int errors;
    private final int MAX_ERRORS = 6;

    public HangmanModelImpl() {
        startNewGame();
    }

    @Override
    public void startNewGame() {
        Random rand = new Random();
        secretWord = WORDS[rand.nextInt(WORDS.length)];
        guessedLetters = new HashSet<>();
        errors = 0;
    }

    @Override
    public boolean guessLetter(char letter) {
        letter = Character.toUpperCase(letter);
        if (guessedLetters.contains(letter)) return true; // Già indovinata

        guessedLetters.add(letter);
        if (!secretWord.contains(String.valueOf(letter))) {
            errors++;
            return false;
        }
        return true;
    }

    @Override
    public boolean guessWord(String word) {
        if (word.equalsIgnoreCase(secretWord)) {
            for (char c : secretWord.toCharArray()) {
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
        StringBuilder sb = new StringBuilder();
        for (char c : secretWord.toCharArray()) {
            if (guessedLetters.contains(c)) {
                sb.append(c).append(" ");
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
        for (char c : secretWord.toCharArray()) {
            if (!guessedLetters.contains(c)) return false;
        }
        return true;
    }

    @Override
    public boolean isGameOver() {
        return errors >= MAX_ERRORS;
    }
}