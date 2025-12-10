package it.unibo.uniboparty.view.minigames.hangman.impl;

import javax.swing.JFrame;

import it.unibo.uniboparty.controller.minigames.hangman.impl.HangmanControllerImpl;
import it.unibo.uniboparty.utilities.AbstractMinigameIntroFrame;
import it.unibo.uniboparty.utilities.MinigameResultCallback;

/**
 * Intro window for the Hangman minigame.
 */
public final class HangManIntroFrame extends AbstractMinigameIntroFrame {

    private static final long serialVersionUID = 1L;

    /**
     * Optional callback used to notify the board when the minigame ends.
     */
    private final transient MinigameResultCallback resultCallback;

    /**
     * Creates the intro window without a callback.
     *
     * <p>
     * Useful to run the minigame standalone, without the main board.
     * </p>
     */
    public HangManIntroFrame() {
        this(null);
    }

    /**
     * Creates the intro window for the Hangman minigame.
     *
     * @param resultCallback callback that will receive the result code
     *                       when the game ends (1 = win, 0 = loss, 2 = in progress)
     */
    public HangManIntroFrame(final MinigameResultCallback resultCallback) {
        super();
        this.resultCallback = resultCallback;
        initIntroFrame();
    }

    @Override
    protected String getMinigameTitle() {
        return "Hangman";
    }

    @Override
    protected String getRulesText() {
        return
                "How to play:\n"
                        + "- Guess the secret word, one letter at a time.\n"
                        + "- You can also try the whole word, but you only have 1 attempt.\n"
                        + "- You win if you guess the word.\n"
                        + "- Every error adds a piece to the hangman. When it is complete, you lose.";
    }

    @Override
    protected JFrame createGameFrame() {
        final HangmanViewImpl view = new HangmanViewImpl();
        new HangmanControllerImpl(view, this.resultCallback);

        return view.getFrame();
    }
}
