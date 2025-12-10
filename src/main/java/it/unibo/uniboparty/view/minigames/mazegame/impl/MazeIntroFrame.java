package it.unibo.uniboparty.view.minigames.mazegame.impl;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import it.unibo.uniboparty.controller.minigames.mazegame.api.MazeController;
import it.unibo.uniboparty.controller.minigames.mazegame.impl.MazeControllerImpl;
import it.unibo.uniboparty.utilities.AbstractMinigameIntroFrame;
import it.unibo.uniboparty.utilities.MinigameResultCallback;

/**
 * Intro window for the Maze Game minigame.
 */
public final class MazeIntroFrame extends AbstractMinigameIntroFrame {

    private static final long serialVersionUID = 1L;

    /**
     * Optional callback used to notify the board when the minigame ends.
     */
    private final transient MinigameResultCallback resultCallback;

    /**
     * Creates the intro frame without a callback.
     */
    public MazeIntroFrame() {
        this(null);
    }

    /**
     * Creates the intro frame with a callback.
     *
     * @param resultCallback callback that will receive the result code
     *                       when the game window is closed
     */
    public MazeIntroFrame(final MinigameResultCallback resultCallback) {
        super();
        this.resultCallback = resultCallback;
        this.initIntroFrame();
    }

    @Override
    protected String getMinigameTitle() {
        return "Maze Game";
    }

    @Override
    protected String getRulesText() {
        return
              "How to play:\n"
            + "- Navigate through the maze to reach the exit.\n"
            + "- Avoid dead ends and find the shortest path.\n"
            + "- Use arrow keys to move your character.\n"
            + "- Reach the exit in the shortest time possible to win!\n"
            + "- If you reach the maximum time or moves you lose.";
    }

    @Override
    protected JFrame createGameFrame() {
        final MazeController controller = new MazeControllerImpl();
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
