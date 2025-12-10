package it.unibo.uniboparty.controller.minigames.hangman.impl;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import it.unibo.uniboparty.controller.minigames.hangman.api.HangmanController;
import it.unibo.uniboparty.model.minigames.hangman.api.HangmanModel;
import it.unibo.uniboparty.view.minigames.hangman.api.HangmanView;
import it.unibo.uniboparty.model.minigames.hangman.impl.HangmanModelImpl;
import it.unibo.uniboparty.view.minigames.hangman.impl.HangmanViewImpl;
import it.unibo.uniboparty.utilities.MinigameResultCallback;

/**
 * Concrete implementation of the {@link HangmanController} interface.
 *
 * <p>
 * This class orchestrates the Hangman minigame: it connects the model and the view
 * and handles both single-letter guesses and full-word attempts.
 * </p>
 */
public final class HangmanControllerImpl implements HangmanController {

    private static final int RESULT_LOST = 0;
    private static final int RESULT_WON = 1;
    private static final int RESULT_IN_PROGRESS = 2;

    private final HangmanModel model;
    private final HangmanView view;
    private final transient MinigameResultCallback resultCallback;

    /**
     * Encoded result of the current match.
     * 2 = in progress, 1 = win, 0 = loss.
     */
    private int resultCode;

    /**
     * Default constructor: runs the minigame standalone (no callback).
     */
    public HangmanControllerImpl() {
        this(new HangmanViewImpl(), null);
    }

    /**
     * Creates a controller with the given view and an optional callback.
     *
     * @param view concrete view implementation
     * @param resultCallback callback to notify when the game ends, may be {@code null}
     */
    @SuppressFBWarnings(
        value = "EI_EXPOSE_REP2",
        justification = "The Hangman view is created by the intro frame and then "
        + "passed to the controller. Sharing this reference is intentional "
        + "and required by the MVC design."
    )
    public HangmanControllerImpl(final HangmanView view, final MinigameResultCallback resultCallback) {
        this.model = new HangmanModelImpl();
        this.view = view;
        this.resultCallback = resultCallback;
        this.resultCode = RESULT_IN_PROGRESS;

        initGame();
        initListeners();
    }

    /**
     * Returns the encoded result of the match.
     *
     * @return 2 = in progress, 1 = win, 0 = loss
     */
    public int getResultCode() {
        return this.resultCode;
    }

    /**
     * Sets up the initial visual state of the game.
     */
    private void initGame() {
        view.updateMaskedWord(model.getMaskedWord());
        view.updateMan(0);
    }

    /**
     * Registers event listeners for user interactions.
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
     * @param letter the character guessed by the player
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
     * @param word the string guessed by the player
     */
    private void handleWordGuess(final String word) {
        model.guessWord(word);
        view.updateMaskedWord(model.getMaskedWord());
        view.updateMan(model.getErrorCount());
        checkGameState();
    }

    /**
     * Checks the current state of the game (victory or defeat)
     * and notifies the view and the optional callback.
     */
    private void checkGameState() {
        if (this.resultCode != RESULT_IN_PROGRESS) {
            return;
        }

        if (model.isGameWon()) {
            this.resultCode = RESULT_WON;
            view.showVictory(model.getSecretWord());
            notifyResult();
        } else if (model.isGameOver()) {
            this.resultCode = RESULT_LOST;
            view.showDefeat(model.getSecretWord());
            notifyResult();
        }
    }

    /**
     * Notifies the board (if present) with the final result code.
     */
    private void notifyResult() {
        if (this.resultCallback != null) {
            this.resultCallback.onResult(this.resultCode);
        }
    }
}
