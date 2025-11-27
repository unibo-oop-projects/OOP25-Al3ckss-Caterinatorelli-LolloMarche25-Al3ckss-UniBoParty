package it.unibo.uniboparty.controller.minigames.hangman.impl;

import it.unibo.uniboparty.controller.minigames.hangman.api.HangmanController;
import it.unibo.uniboparty.model.minigames.hangman.api.HangmanModel;
import it.unibo.uniboparty.view.minigames.hangman.api.HangmanView;
import it.unibo.uniboparty.model.minigames.hangman.impl.HangmanModelImpl;
import it.unibo.uniboparty.view.minigames.hangman.impl.HangmanViewImpl;

/**
 * Concrete implementation of the game's Controller.
 * Links Model and View and manages input from the player.
 */

public class HangmanControllerImpl implements HangmanController {

    private final HangmanModel model;
    private final HangmanView view;

    /**
     * Creates the Controller and starts the game.
     */
    public HangmanControllerImpl() {
        // FIX PER SPOTBUGS (EI2): Istanziazione interna
        this.model = new HangmanModelImpl();
        this.view = new HangmanViewImpl();

        initGame();
        initListeners();
    }

    /**
     * Initializes the starting point of the View.
     */
    private void initGame() {
        view.updateMaskedWord(model.getMaskedWord());
        view.updateMan(0);
    }

    /**
     * Maps View's events to the Controller methods.
     */
    private void initListeners() {
        view.addLetterListener(e -> {
            final String cmd = e.getActionCommand(); // Contiene "A", "B", ecc.
            if (cmd != null && !cmd.isEmpty()) {
                final char letter = cmd.charAt(0);
                handleLetterGuess(letter);
            }
        });

        view.addGuessWordListener(e -> {
            final String word = view.getGuessWordInput();
            if (word != null && !word.isBlank()) {
                handleWordGuess(word.trim());
            }
        });
    }

    private void handleLetterGuess(final char letter) {
        view.disableLetterButton(letter);
        model.guessLetter(letter);
        view.updateMaskedWord(model.getMaskedWord());
        view.updateMan(model.getErrorCount());
        checkGameState();
    }

    private void handleWordGuess(final String word) {
        model.guessWord(word);
        view.updateMaskedWord(model.getMaskedWord());
        view.updateMan(model.getErrorCount());
        checkGameState();
    }

    private void checkGameState() {
        if (model.isGameWon()) {
            view.showVictory(model.getSecretWord());
        } else if (model.isGameOver()) {
            view.showDefeat(model.getSecretWord());
        }
    }
}
