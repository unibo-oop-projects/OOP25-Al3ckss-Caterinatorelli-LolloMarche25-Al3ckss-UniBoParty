package it.unibo.uniboparty.view.minigames.memory.impl;

import java.util.List;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import it.unibo.uniboparty.controller.minigames.memory.api.MemoryGameController;
import it.unibo.uniboparty.model.minigames.memory.api.CardReadOnly;
import it.unibo.uniboparty.model.minigames.memory.api.MemoryGameReadOnlyState;
import it.unibo.uniboparty.view.minigames.memory.api.MemoryGameView;

/**
 * Swing implementation of the Memory game view.
 *
 * <p>
 * This panel shows:
 * <ul>
 *   <li>the 4x4 grid of cards,</li>
 *   <li>a top info label with time and moves,</li>
 *   <li>a bottom status label with short messages.</li>
 * </ul>
 * It does not contain game logic: it only reads {@link MemoryGameReadOnlyState}
 * and notifies the {@link MemoryGameController} when the user clicks on a card.
 * </p>
 */
public final class MemoryGameViewImpl extends JPanel implements MemoryGameView {

    private static final long serialVersionUID = 1L;

    private static final int ROWS = 4;
    private static final int COLS = 4;

    private static final int INFO_PADDING = 5;
    private static final int STATUS_PADDING = 10;
    private static final float STATUS_FONT_SIZE = 14f;
    private static final int GRID_PADDING = 20;

    /** Graphic size of the card image in pixels. */
    private static final int CARD_IMG_SIZE = 60;

    /** Preferred size of each button (card). */
    private static final int CARD_BUTTON_SIZE = 80;

    private final JPanel gridPanel;
    private final JLabel statusLabel;
    private final JLabel infoLabel;
    private final JButton[][] buttons;

    private final ImageIcon questionIcon;

    private transient MemoryGameController controller;

    /**
     * Creates the Swing UI for the Memory game.
     */
    public MemoryGameViewImpl() {
        super(new BorderLayout());

        this.infoLabel = new JLabel("Time: 0s | Moves: 0", JLabel.CENTER);
        this.infoLabel.setBorder(
            javax.swing.BorderFactory.createEmptyBorder(
                INFO_PADDING, INFO_PADDING, INFO_PADDING, INFO_PADDING));

        this.statusLabel = new JLabel("Welcome! Let's play!", JLabel.CENTER);
        this.statusLabel.setBorder(
            javax.swing.BorderFactory.createEmptyBorder(
                STATUS_PADDING, STATUS_PADDING, STATUS_PADDING, STATUS_PADDING));
        this.statusLabel.setFont(
            this.statusLabel.getFont().deriveFont(Font.PLAIN, STATUS_FONT_SIZE));
        this.statusLabel.setForeground(Color.DARK_GRAY);

        this.gridPanel = new JPanel(new GridLayout(ROWS, COLS, 10, 10));
        this.gridPanel.setBorder(
            javax.swing.BorderFactory.createEmptyBorder(
                GRID_PADDING, GRID_PADDING, GRID_PADDING, GRID_PADDING));

        this.buttons = new JButton[ROWS][COLS];

        this.questionIcon = loadScaledIcon("/images/memory/question.png",
                CARD_IMG_SIZE, CARD_IMG_SIZE);

        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                final JButton cardButton = new JButton("?");
                cardButton.setPreferredSize(
                    new Dimension(CARD_BUTTON_SIZE, CARD_BUTTON_SIZE));
                cardButton.setFocusPainted(false);
                cardButton.setBackground(Color.LIGHT_GRAY);

                final int rr = r;
                final int cc = c;

                cardButton.addActionListener(e -> {
                    if (this.controller != null) {
                        this.controller.onCardClicked(rr, cc);
                    }
                });

                this.buttons[r][c] = cardButton;
                this.gridPanel.add(cardButton);
            }
        }

        this.add(this.infoLabel, BorderLayout.NORTH);
        this.add(this.gridPanel, BorderLayout.CENTER);
        this.add(this.statusLabel, BorderLayout.SOUTH);
    }

    /**
     * Loads an image from the resources and scales it.
     *
     * @param path   resource path of the image
     * @param width  desired width
     * @param height desired height
     * @return a scaled {@link ImageIcon}, or {@code null} if not found
     */
    private ImageIcon loadScaledIcon(final String path, final int width, final int height) {
        final URL url = getClass().getResource(path);
        if (url == null) {
            return null;
        }
        final ImageIcon icon = new ImageIcon(url);
        final Image scaled = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setController(final MemoryGameController controller) {
        this.controller = controller;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render(final MemoryGameReadOnlyState state) {
        if (state.isGameOver()) {
            setStatusMessage("Game over. Close the window to return to the board.");
        } else {
            setStatusMessage(state.getMessage());
        }

        final List<CardReadOnly> cards = state.getCards();

        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                final int index = r * COLS + c;
                final CardReadOnly card = cards.get(index);

                if (card.isRevealed()) {
                    showCardWithImage(r, c, card.getSymbol().toString());
                } else {
                    hideCard(r, c);
                }
            }
        }

        if (state.isGameOver()) {
            setAllButtonsDisabled(true);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateInfoPanel(final int secondsElapsed, final int moves) {
        this.infoLabel.setText("Time: " + secondsElapsed + "s | Moves: " + moves);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAllButtonsDisabled(final boolean disabled) {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                this.buttons[r][c].setEnabled(!disabled);
            }
        }
    }

    /**
     * Shows the real symbol of the card by loading the corresponding image.
     *
     * @param r         row of the card
     * @param c         column of the card
     * @param imageName name of the image file (without extension)
     */
    private void showCardWithImage(final int r, final int c, final String imageName) {
        final JButton b = this.buttons[r][c];

        final String path = "/images/memory/" + imageName + ".png";
        final ImageIcon icon = loadScaledIcon(path, CARD_IMG_SIZE, CARD_IMG_SIZE);

        if (icon != null) {
            b.setIcon(icon);
            b.setText("");
        } else {
            b.setIcon(null);
            b.setText(imageName);
        }

        b.setBackground(Color.WHITE);
    }

    /**
     * Hides the card and shows the default "question mark" image.
     *
     * @param r row of the card
     * @param c column of the card
     */
    private void hideCard(final int r, final int c) {
        final JButton b = this.buttons[r][c];

        if (this.questionIcon != null) {
            b.setIcon(this.questionIcon);
            b.setText("");
        } else {
            b.setIcon(null);
            b.setText("?");
        }

        b.setBackground(Color.LIGHT_GRAY);
    }

    /**
     * Updates the status message at the bottom of the window.
     *
     * @param msg the message to show
     */
    private void setStatusMessage(final String msg) {
        this.statusLabel.setText(msg);
    }
}
