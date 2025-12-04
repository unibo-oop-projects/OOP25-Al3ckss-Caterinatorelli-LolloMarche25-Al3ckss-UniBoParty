package it.unibo.uniboparty.view.minigames.sudoku.impl;

import javax.swing.JFrame;
import it.unibo.uniboparty.utilities.AbstractMinigameIntroFrame;

/**
 * Intro window for the Sudoku minigame.
 */
public final class SudokuIntroFrame extends AbstractMinigameIntroFrame {

    private static final long serialVersionUID = 1L;

    /**
     * Creates the intro window for the Sudoku minigame.
     */
    public SudokuIntroFrame() {
        super();
        initIntroFrame();
    }

    @Override
    protected String getMinigameTitle() {
        return "Sudoku";
    }

    @Override
    protected String getRulesText() {
        return
                "How to play:\n"
              + "- You have to fill every 3x3 grid with number from 1 to 9 with no repetitions.\n"
              + "- Every row and column also has to be filled with number from 1 to 9 with no repetitions.\n"
              + "- You win when the whole grid is filled.\n"
              + "- You have 3 errors available before losing the game.";
    }

    @Override
    protected JFrame createGameFrame() {
        final JFrame gameFrame = new JFrame("Sudoku - Game");
        createGameFrame().setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        createGameFrame().setContentPane(new SudokuViewImpl());
        gameFrame.pack();
        return gameFrame;

    }

}
