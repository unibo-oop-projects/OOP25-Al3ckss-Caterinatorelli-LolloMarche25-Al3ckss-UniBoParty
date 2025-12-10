package it.unibo.uniboparty.view.minigames.tetris.impl;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import it.unibo.uniboparty.controller.minigames.tetris.api.TetrisController;
import it.unibo.uniboparty.controller.minigames.tetris.impl.TetrisControllerImpl;
import it.unibo.uniboparty.utilities.AbstractMinigameIntroFrame;
import it.unibo.uniboparty.utilities.MinigameResultCallback;

/**
 * Intro window for the Tetris minigame.
 */
public final class TetrisIntroFrame extends AbstractMinigameIntroFrame {

    private static final long serialVersionUID = 1L;

    /**
     * Optional callback used to notify the board when the minigame ends.
     */
    private final transient MinigameResultCallback resultCallback;

    /**
     * Creates the intro frame without a callback.
     */
    public TetrisIntroFrame() {
        this(null);
    }

    /**
     * Creates the intro frame with a callback.
     *
     * @param resultCallback callback that will receive the result code
     *                       when the game window is closed
     */
    public TetrisIntroFrame(final MinigameResultCallback resultCallback) {
        super();
        this.resultCallback = resultCallback;
        this.initIntroFrame();
    }

    @Override
    protected String getMinigameTitle() {
        return "Tetris";
    }

    @Override
    protected String getRulesText() {
        return
              "How to play:\n"
            + "- Click and drag the piece into the grid.\n"
            + "- Bigger pieces give you more points.\n"
            + "- Each completed row adds extra points.\n"
            + "- Reach 100 points to win. If you run out of moves, you lose.";
    }

    @Override
    protected JFrame createGameFrame() {
        final TetrisController controller = new TetrisControllerImpl();
        final JFrame gameFrame = controller.getView();

        gameFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        gameFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(final WindowEvent e) {
                if (resultCallback != null) {
                    final int resultCode = controller.getState();
                    resultCallback.onResult(resultCode);
                }
            }
        });

        return gameFrame;
    }
}
