package it.unibo.uniboparty.utilities;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Dimension;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Generic intro panel used by each minigame before starting the actual match.
 *
 * <p>
 * This panel displays:
 * </p>
 * 
 * <ul>
 *   <li>a background image (optional),</li>
 *   <li>the title of the minigame,</li>
 *   <li>a "Play" button to start the game,</li>
 *   <li>a "How to play" button showing the rules in a dialog.</li>
 * </ul>
 */
public final class MinigameIntroPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private static final int WINDOWWIDTH = 600;
    private static final int WINDOWHEIGHT = 400;

    /** Font size of the title label. */
    private static final float TITLE_FONT_SIZE = 26f;

    /** Background image used for the intro screen. May be {@code null} if not found. */
    private final transient Image backgroundImage;

    /**
     * Creates a new intro panel for a minigame.
     *
     * @param titleText  the title shown at the top of the panel
     * @param rulesText  the full text describing the rules of the minigame
     * @param onStart    a callback invoked when the player presses the "Play" button
     */
    public MinigameIntroPanel(
        final String titleText,
        final String rulesText,
        final Runnable onStart
    ) {
        super(new BorderLayout());

        this.setPreferredSize(new Dimension(WINDOWWIDTH, WINDOWHEIGHT));

        final URL url = this.getClass().getResource("/background.png");
        this.backgroundImage = url != null ? new ImageIcon(url).getImage() : null;

        final JLabel title = new JLabel(titleText, JLabel.CENTER);
        title.setFont(title.getFont().deriveFont(Font.BOLD, TITLE_FONT_SIZE));
        title.setOpaque(false);
        this.add(title, BorderLayout.NORTH);

        final JButton startButton = new JButton("Play");
        final JButton rulesButton = new JButton("How to play");

        final JPanel buttons = new JPanel();
        buttons.setOpaque(false);
        buttons.add(startButton);
        buttons.add(rulesButton);
        this.add(buttons, BorderLayout.CENTER);

        startButton.addActionListener(e -> {
            if (onStart != null) {
                onStart.run();
            }
        });

        rulesButton.addActionListener(e ->
            JOptionPane.showMessageDialog(
                this,
                rulesText,
                "Game Rules",
                JOptionPane.INFORMATION_MESSAGE
            )
        );
    }

    /**
     * Paints the background image (if present) stretched to the full size
     * of the panel before painting the Swing components.
     *
     * @param g the graphics context used for painting
     */
    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        if (this.backgroundImage != null) {
            g.drawImage(this.backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        }
    }
}
