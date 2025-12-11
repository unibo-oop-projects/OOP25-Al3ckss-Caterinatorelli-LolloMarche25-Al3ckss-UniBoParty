package it.unibo.uniboparty.view.minigames.whacamole.impl;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import it.unibo.uniboparty.utilities.AbstractMinigameIntroFrame;
import it.unibo.uniboparty.utilities.MinigameResultCallback;

/**
 * Intro window for the Whac-A-Mole minigame.
 *
 * <p>
 * This frame shows the title, rules, and a "Play" button.
 * When the actual game window is closed, an optional callback
 * is notified with the final result code (2 = in progress, 1 = win, 0 = loss).
 * </p>
 */
public final class WhacAMoleIntroFrame extends AbstractMinigameIntroFrame {

    private static final long serialVersionUID = 1L;

    /**
     * Optional callback used to notify the board when the minigame ends.
     */
    private final transient MinigameResultCallback resultCallback;

    /**
     * Creates the intro window without any callback.
     *
     * <p>
     * This constructor is kept for compatibility and for running
     * the minigame standalone.
     * </p>
     */
    public WhacAMoleIntroFrame() {
        this(null);
    }

    /**
     * Creates the intro window for the Whac-A-Mole minigame.
     *
     * @param resultCallback callback that receives the result code
     *                       when the game window is closed
     */
    public WhacAMoleIntroFrame(final MinigameResultCallback resultCallback) {
        super();
        this.resultCallback = resultCallback;
        initIntroFrame();
    }

    @Override
    protected String getMinigameTitle() {
        return "Whac-A-Mole";
    }

    @Override
    protected String getRulesText() {
        return
              "How to play:\n"
            + "- Hit the mole when it appears in the grid.\n"
            + "- Avoid bombs: they remove 2 points.\n"
            + "- Each mole is worth 1 point.\n"
            + "- You win if your score is at least 10 at the end of the time.\n"
            + "- You have 30 seconds to score as much as possible.";
    }

    @Override
    protected JFrame createGameFrame() {
        final WhacAMoleViewImpl gameView = new WhacAMoleViewImpl();

        final JFrame gameFrame = new JFrame("Whac-A-Mole - Game");
        gameFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        gameFrame.setContentPane(gameView);
        gameFrame.pack();

        // When the game window is closed, notify the board (if a callback is present)
        gameFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(final WindowEvent e) {
                if (resultCallback != null) {
                    final int resultCode = gameView.getResultCode();
                    resultCallback.onResult(resultCode);
                }
            }
        });

        return gameFrame;
    }
}
