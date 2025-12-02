package it.unibo.uniboparty.utilities;

import javax.swing.JFrame;

/**
 * Abstract base frame used as an intro screen for any minigame.
 *
 * <p>This class displays a {@link MinigameIntroPanel} containing:</p>
 * <ul>
 *   <li>the minigame title,</li>
 *   <li>a button to show the rules,</li>
 *   <li>a button to start the game.</li>
 * </ul>
 *
 * <p>Subclasses only need to provide:</p>
 * <ul>
 *   <li>{@link #getMinigameTitle()} – a short title,</li>
 *   <li>{@link #getRulesText()} – the text describing the rules,</li>
 *   <li>{@link #createGameFrame()} – the frame that runs the actual minigame.</li>
 * </ul>
 */
public abstract class AbstractMinigameIntroFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    /**
     * Creates the intro window for a minigame.
     * The window becomes visible immediately.
     */
    public AbstractMinigameIntroFrame() {
        super();

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
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
