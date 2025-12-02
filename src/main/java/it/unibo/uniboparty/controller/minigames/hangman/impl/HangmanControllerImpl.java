package it.unibo.uniboparty.controller.minigames.hangman.impl;

import it.unibo.uniboparty.controller.minigames.hangman.api.HangmanController;
import it.unibo.uniboparty.model.minigames.hangman.api.HangmanModel;
import it.unibo.uniboparty.view.minigames.hangman.api.HangmanView;
import it.unibo.uniboparty.model.minigames.hangman.impl.HangmanModelImpl;
import it.unibo.uniboparty.view.minigames.hangman.impl.HangmanViewImpl;

/**
 * Concrete implementation of the {@link HangmanController} interface.
 *
 * <p>
 * This class orchestrates the "Hangman" minigame logic. It bridges the gap between
 * the {@link HangmanModel} (game rules and state) and the {@link HangmanView}
 * (user interface), managing player inputs for both single letter guesses and full word attempts.
 */
public class HangmanControllerImpl implements HangmanController {

    private final HangmanModel model;
    private final HangmanView view;

    /**
     * Constructs a new {@code HangmanControllerImpl}.
     *
     * <p>
     * This constructor internally instantiates the concrete {@link HangmanModelImpl}
     * and {@link HangmanViewImpl} and immediately initializes the game state and
     * event listeners.
     */
    public HangmanControllerImpl() {
        this.model = new HangmanModelImpl();
        this.view = new HangmanViewImpl();

        initGame();
        initListeners();
    }

    /**
     * Sets up the initial visual state of the game.
     *
     * <p>
     * This method resets the view by displaying the initial masked word (e.g., underscores)
     * and setting the hangman graphic to the starting state (zero errors).
     */
    private void initGame() {
        view.updateMaskedWord(model.getMaskedWord());
        view.updateMan(0);
    }

    /**
     * Registers event listeners for user interactions.
     *
     * <p>
     * Specifically, it maps the letter buttons and the "guess word" input field
     * from the view to the corresponding handling methods in this controller.
     */
    private void initListeners() {
        view.addLetterListener(e -> {
            final String cmd = e.getActionCommand();
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

    /**
     * Processes a player's guess of a single letter.
     *
     * <p>
     * This method disables the corresponding button in the UI, updates the model,
     * refreshes the masked word and error count on the view, and checks if the game has ended.
     *
     * @param letter the character guessed by the player.
     */
    private void handleLetterGuess(final char letter) {
        view.disableLetterButton(letter);
        model.guessLetter(letter);
        view.updateMaskedWord(model.getMaskedWord());
        view.updateMan(model.getErrorCount());
        checkGameState();
    }

    /**
     * Processes a player's guess of the entire word.
     *
     * <p>
     * It delegates the word check to the model and updates the view accordingly.
     *
     * @param word the string guessed by the player.
     */
    private void handleWordGuess(final String word) {
        model.guessWord(word);
        view.updateMaskedWord(model.getMaskedWord());
        view.updateMan(model.getErrorCount());
        checkGameState();
    }

    /**
     * Checks the current state of the game (victory or defeat).
     *
     * <p>
     * If the game conditions are met (word guessed or max errors reached),
     * it triggers the appropriate end-game display in the view.
     */
    private void checkGameState() {
        if (model.isGameWon()) {
            view.showVictory(model.getSecretWord());
        } else if (model.isGameOver()) {
            view.showDefeat(model.getSecretWord());
        }
    }
}
