package it.unibo.uniboparty.utilities;

import javax.swing.JFrame;

/**
 * Base frame used as an intro screen for a minigame.
 *
 * <p>The actual window is set up by calling {@link #initIntroFrame()},
 * which uses the values returned by the abstract methods:</p>
 * <ul>
 *   <li>{@link #getMinigameTitle()}</li>
 *   <li>{@link #getRulesText()}</li>
 *   <li>{@link #createGameFrame()}</li>
 * </ul>
 */
public abstract class AbstractMinigameIntroFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    /**
     * Empty constructor. The real UI setup is done in {@link #initIntroFrame()}.
     */
    protected AbstractMinigameIntroFrame() {
        super();
    }

    /**
     * Initializes the intro window and makes it visible.
     *
     * <p>This method builds a {@link MinigameIntroPanel} using the
     * title and rules provided by the subclass and connects the
     * "Play" button to the actual game frame.</p>
     */
    protected final void initIntroFrame() {
        final MinigameIntroPanel panel = new MinigameIntroPanel(
            getMinigameTitle(),
            getRulesText(),
            () -> {
                final JFrame gameFrame = createGameFrame();
                gameFrame.setLocationRelativeTo(null);
                gameFrame.setVisible(true);
                this.dispose();
            }
        );

        this.setTitle(getMinigameTitle());
        this.setContentPane(panel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    /**
     * @return the short title of the minigame (used in UI and window title)
     */
    protected abstract String getMinigameTitle();

    /**
     * @return the textual description of the rules, shown when clicking "How to play"
     */
    protected abstract String getRulesText();

    /**
     * @return the JFrame to display when the user presses "Play"
     */
    protected abstract JFrame createGameFrame();
}
