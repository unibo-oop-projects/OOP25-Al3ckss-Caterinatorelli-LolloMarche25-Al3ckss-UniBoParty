package it.unibo.uniboparty.view.minigames.memory.impl;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import it.unibo.uniboparty.controller.minigames.memory.impl.MemoryGameControllerImpl;
import it.unibo.uniboparty.utilities.AbstractMinigameIntroFrame;
import it.unibo.uniboparty.utilities.MinigameResultCallback;

/**
 * Intro window for the Memory minigame.
 *
 * <p>
 * Shows the title, the rules and a button to start the game.
 * If a callback is provided, it is notified with the result code
 * when the game window is closed:
 * 2 = in progress, 1 = win, 0 = loss.
 * </p>
 */
public final class MemoryIntroFrame extends AbstractMinigameIntroFrame {

    private static final long serialVersionUID = 1L;

    /**
     * Optional callback used to notify the board when the minigame ends.
     */
    private final transient MinigameResultCallback resultCallback;

    /**
     * Constructs the intro window without a callback.
     */
    public MemoryIntroFrame() {
        this(null);
    }

    /**
     * Creates the intro frame with a callback.
     *
     * @param resultCallback callback that will receive the result code
     *                       when the game window is closed
     */
    public MemoryIntroFrame(final MinigameResultCallback resultCallback) {
        super();
        this.resultCallback = resultCallback;
        initIntroFrame();
    }

    @Override
    protected String getMinigameTitle() {
        return "Memory";
    }

    @Override
    protected String getRulesText() {
        return "How to play:\n"
            + "- Click a card to reveal its symbol.\n"
            + "- Then click a second card: if they match, the pair stays revealed.\n"
            + "- If they do not match, they will be hidden again after a short delay.\n"
            + "- Try to find all pairs using as few moves as possible.\n"
            + "- Be careful: you lose if you exceed the move limit.";
    }

    @Override
    protected JFrame createGameFrame() {
        final MemoryGameControllerImpl controller = MemoryGameControllerImpl.create();

        final JFrame gameFrame = new JFrame("Memory - Game");
        gameFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        gameFrame.setContentPane(controller.createMainPanel());
        gameFrame.pack();
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
