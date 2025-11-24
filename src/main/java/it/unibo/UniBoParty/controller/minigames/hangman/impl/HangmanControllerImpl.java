package it.unibo.UniBoParty.controller.minigames.hangman.impl;

import it.unibo.UniBoParty.controller.minigames.hangman.api.HangmanController;
import it.unibo.UniBoParty.model.minigames.hangman.api.HangmanModel;
import it.unibo.UniBoParty.view.minigames.hangman.api.HangmanView;

/**
 * Concrete implementation of the game's Controller.
 * Links Model and View and manages input from the player.
 */

public class HangmanControllerImpl implements HangmanController {

    private final HangmanModel model;
    private final HangmanView view;

    /**
     * Creates the Controller and starts the game.
     * @param model Game's Model.
     * @param view Game's View.
     */
    public HangmanControllerImpl(HangmanModel model, HangmanView view) {
        this.model = model;
        this.view = view;
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
            String cmd = e.getActionCommand(); // Contiene "A", "B", ecc.
            if (cmd != null && !cmd.isEmpty()) {
                char letter = cmd.charAt(0);
                handleLetterGuess(letter);
            }
        });

        view.addGuessWordListener(e -> {
            String word = view.getGuessWordInput();
            if (word != null && !word.trim().isEmpty()) {
                handleWordGuess(word.trim());
            }
        });
    }

    private void handleLetterGuess(char letter) {
        view.disableLetterButton(letter);
        model.guessLetter(letter);
        view.updateMaskedWord(model.getMaskedWord());
        view.updateMan(model.getErrorCount());
        checkGameState();
    }

    private void handleWordGuess(String word) {
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