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
 * Concrete implementation of the Dice View.
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
     * Constructor. Sets up the GUI layout.
     */
    public DiceViewImpl() {
        frame = new JFrame("Lancio Dadi - UniBoParty");
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        // Usa DISPOSE per non chiudere tutto il gioco, ma solo questa finestra
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        // --- PANNELLO CENTRALE (I DADI) ---
        final JPanel diceContainer = new JPanel(new GridLayout(1, 2, PANEL_GAP, PANEL_GAP));
        diceContainer.setBackground(TABLE_COLOR); // Verde scuro
        diceContainer.setBorder(BorderFactory.createEmptyBorder(PANEL_GAP, PANEL_GAP, PANEL_GAP, PANEL_GAP));

        diePanel1 = new DieGraphicsPanel();
        diePanel2 = new DieGraphicsPanel();

        // Importante: diamo una dimensione preferita ai dadi per il layout
        diePanel1.setPreferredSize(new Dimension(DIE_SIZE, DIE_SIZE));
        diePanel2.setPreferredSize(new Dimension(DIE_SIZE, DIE_SIZE));

        diceContainer.add(diePanel1);
        diceContainer.add(diePanel2);
        frame.add(diceContainer, BorderLayout.CENTER);

        // --- PANNELLO INFERIORE (BOTTONE E TOTALE) ---
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

    @Override
    public void setDiceValues(final int d1, final int d2) {
        diePanel1.setValue(d1);
        diePanel2.setValue(d2);
    }

    @Override
    public void setTotalText(final String text) {
        totalLabel.setText(text);
    }

    @Override
    public void addRollListener(final ActionListener listener) {
        rollButton.addActionListener(listener);
    }

    @Override
    public void close() {
        frame.dispose();
    }
}
