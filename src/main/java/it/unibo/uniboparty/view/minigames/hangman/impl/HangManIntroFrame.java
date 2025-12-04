package it.unibo.uniboparty.view.minigames.hangman.impl;

import javax.swing.JFrame;
import it.unibo.uniboparty.utilities.AbstractMinigameIntroFrame;

/**
 * Intro window for the Hangman game.
 */
public final class HangManIntroFrame extends AbstractMinigameIntroFrame {

    private static final long serialVersionUID = 1L;

    /**
     * Create the intro window for the Hangman minigame.
     */
    public HangManIntroFrame() {
        super();
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
              + "Guess the secret word find it letter by letter.\n"
              + "You can also try and guess the whole word but you only have 1 try so be careful.\n"
              + "You win when the word is guessed right.\n"
              + "Every error will add a piece of the hangman, once it's complete it will be Game Over.";
    }

    @Override
    protected JFrame createGameFrame() {
        final JFrame gameFrame = new JFrame("Hangman - Game");
        gameFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        gameFrame.setContentPane(new HangManIntroFrame());
        gameFrame.pack();
        return gameFrame;
    }
}
