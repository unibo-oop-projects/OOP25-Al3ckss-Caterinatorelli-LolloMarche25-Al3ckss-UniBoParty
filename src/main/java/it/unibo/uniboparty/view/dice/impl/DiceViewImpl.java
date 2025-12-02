package it.unibo.uniboparty.view.dice.impl;

import it.unibo.uniboparty.view.dice.api.DiceView;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Concrete implementation of the {@link DiceView} interface using Java Swing.
 *
 * <p>
 * This class handles the graphical user interface for the Dice minigame.
 * It arranges two custom {@link DieGraphicsPanel} components to visualize the dice faces
 * and provides a control panel for the user to interact with the game (roll button and score display).
 */
public final class DiceViewImpl implements DiceView {

    private static final int FRAME_WIDTH = 500;
    private static final int FRAME_HEIGHT = 400;
    private static final int PANEL_GAP = 20;
    private static final int DIE_SIZE = 150;
    private static final int FONT_SIZE_TOTAL = 24;
    private static final int FONT_SIZE_BTN = 18;
    private static final int ROLL_BUTTON_WIDTH = 100;
    private static final int ROLL_BUTTON_HEIGHT = 60;
    private static final Color TABLE_COLOR = new Color(30, 100, 30);
    private static final int BOTTOM_PADDING = 10;
    private static final String FONT_NAME = "Arial";

    private final JFrame frame;
    private final DieGraphicsPanel diePanel1;
    private final DieGraphicsPanel diePanel2;
    private final JLabel totalLabel;
    private final JButton rollButton;

    /**
     * Constructs the Dice View and initializes the GUI layout.
     *
     * <p>
     * It sets up the main {@link JFrame}, creates the central container with a green table-like
     * background, and initializes the control panel containing the total score label
     * and the "Roll" button. The frame is made visible immediately upon creation.
     */
    public DiceViewImpl() {
        frame = new JFrame("Lancio Dadi - UniBoParty");
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        final JPanel diceContainer = new JPanel(new GridLayout(1, 2, PANEL_GAP, PANEL_GAP));
        diceContainer.setBackground(TABLE_COLOR);
        diceContainer.setBorder(BorderFactory.createEmptyBorder(PANEL_GAP, PANEL_GAP, PANEL_GAP, PANEL_GAP));

        diePanel1 = new DieGraphicsPanel();
        diePanel2 = new DieGraphicsPanel();

        diePanel1.setPreferredSize(new Dimension(DIE_SIZE, DIE_SIZE));
        diePanel2.setPreferredSize(new Dimension(DIE_SIZE, DIE_SIZE));

        diceContainer.add(diePanel1);
        diceContainer.add(diePanel2);
        frame.add(diceContainer, BorderLayout.CENTER);

        final JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(BOTTOM_PADDING, BOTTOM_PADDING, BOTTOM_PADDING, BOTTOM_PADDING));

        totalLabel = new JLabel("Totale: -", SwingConstants.CENTER);
        totalLabel.setFont(new Font(FONT_NAME, Font.BOLD, FONT_SIZE_TOTAL));

        rollButton = new JButton("LANCIA I DADI!");
        rollButton.setFont(new Font(FONT_NAME, Font.BOLD, FONT_SIZE_BTN));
        rollButton.setPreferredSize(new Dimension(ROLL_BUTTON_WIDTH, ROLL_BUTTON_HEIGHT));

        bottomPanel.add(totalLabel, BorderLayout.NORTH);
        bottomPanel.add(rollButton, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    /**
     * Updates the visual representation of the dice faces.
     *
     * <p>
     * Delegates the drawing logic to the contained {@link DieGraphicsPanel} components.
     *
     * @param d1 the value to display on the first die (1-6).
     * @param d2 the value to display on the second die (1-6).
     */
    @Override
    public void setDiceValues(final int d1, final int d2) {
        diePanel1.setValue(d1);
        diePanel2.setValue(d2);
    }

    /**
     * Updates the text label displaying the sum of the dice.
     *
     * @param text the string to be displayed (e.g., "Total: 12").
     */
    @Override
    public void setTotalText(final String text) {
        totalLabel.setText(text);
    }

    /**
     * Registers an {@link ActionListener} to the roll button.
     *
     * <p>
     * This allows the external controller to handle the click event
     * and trigger the model update.
     *
     * @param listener the listener to attach to the button.
     */
    @Override
    public void addRollListener(final ActionListener listener) {
        rollButton.addActionListener(listener);
    }

    /**
     * Closes the window and releases the associated resources.
     */
    @Override
    public void close() {
        frame.dispose();
    }
}
