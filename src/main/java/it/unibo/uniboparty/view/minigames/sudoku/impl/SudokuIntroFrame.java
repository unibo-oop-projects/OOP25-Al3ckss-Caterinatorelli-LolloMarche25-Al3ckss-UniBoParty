package it.unibo.uniboparty.view.minigames.sudoku.impl;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import it.unibo.uniboparty.controller.minigames.sudoku.impl.SudokuControllerImpl;
import it.unibo.uniboparty.utilities.AbstractMinigameIntroFrame;
import it.unibo.uniboparty.utilities.MinigameResultCallback;

/**
 * Intro window for the Sudoku minigame.
 *
 * <p>
 * Shows the title, the rules and a "Play" button.
 * When the game window is closed, an optional callback is notified
 * with the result code:
 * <ul>
 *   <li>2 = game in progress,</li>
 *   <li>1 = game won,</li>
 *   <li>0 = game lost.</li>
 * </ul>
 * </p>
 */
public final class SudokuIntroFrame extends AbstractMinigameIntroFrame {

    private static final long serialVersionUID = 1L;

    /**
     * Optional callback used to notify the board when the minigame ends.
     */
    private final transient MinigameResultCallback resultCallback;

    /**
     * Creates the intro window without a callback.
     */
    public SudokuIntroFrame() {
        this(null);
    }

    /**
     * Creates the intro frame with a callback.
     *
     * @param resultCallback callback that will receive the result code
     *                       when the game window is closed
     */
    public SudokuIntroFrame(final MinigameResultCallback resultCallback) {
        super();
        this.resultCallback = resultCallback;
        initIntroFrame();
    }

    @Override
    protected String getMinigameTitle() {
        return "Sudoku";
    }

    @Override
    protected String getRulesText() {
        return "How to play:\n"
                + "- You have to fill every 3x3 grid with numbers from 1 to 9 with no repetitions.\n"
                + "- Every row and column also has to be filled with numbers from 1 to 9 with no repetitions.\n"
                + "- You win when the whole grid is filled.\n"
                + "- You have 3 errors available before losing the game.";
    }

    @Override
    protected JFrame createGameFrame() {
        final SudokuControllerImpl controller = new SudokuControllerImpl();

        final JFrame gameFrame = controller.getGameFrame();
        gameFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        gameFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(final WindowEvent e) {
                if (resultCallback != null) {
                    final int resultCode = controller.getResultCode();
                    resultCallback.onResult(resultCode);
                }
            }
        });

        return gameFrame;
    }
}
