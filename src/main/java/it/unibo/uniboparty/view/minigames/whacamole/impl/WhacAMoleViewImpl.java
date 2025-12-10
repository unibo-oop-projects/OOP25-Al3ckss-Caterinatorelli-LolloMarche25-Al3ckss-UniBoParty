package it.unibo.uniboparty.view.minigames.whacamole.impl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.net.URL;

import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import it.unibo.uniboparty.controller.minigames.whacamole.api.WhacAMoleController;
import it.unibo.uniboparty.controller.minigames.whacamole.impl.WhacAMoleControllerImpl;
import it.unibo.uniboparty.model.minigames.whacamole.WhacAMoleGameState;
import it.unibo.uniboparty.view.minigames.whacamole.api.WhacAMoleView;

/**
 * Swing implementation of the Whac-A-Mole game screen.
 *
 * <p>
 * This panel shows:
 * <ul>
 *   <li>the 3x3 grid of holes,</li>
 *   <li>the current score,</li>
 *   <li>the remaining time,</li>
 *   <li>the Start button and the final message.</li>
 * </ul>
 * It talks to the Controller to send user clicks and to read
 * the current game state.
 * </p>
 */
public final class WhacAMoleViewImpl extends JPanel implements WhacAMoleView {

    private static final long serialVersionUID = 1L;

    private static final int GRID_ROWS = 3;
    private static final int GRID_COLS = 3;
    private static final int TOTAL_HOLES = GRID_ROWS * GRID_COLS;

    private static final int HOLE_SIZE = 80;
    private static final int UI_REFRESH_MILLIS = 100;

    private static final int TOP_ROWS = 2;
    private static final int TOP_COLS = 1;
    private static final int TOP_GAP = 5;

    private static final int BOTTOM_ROWS = 2;
    private static final int BOTTOM_COLS = 1;
    private static final int BOTTOM_GAP = 5;

    private static final int HOLE_GRID_H_GAP = 10;
    private static final int HOLE_GRID_V_GAP = 10;

    private static final float GAME_OVER_FONT_SIZE = 18f;

    private static final int ICON_SIZE = 60;

    private static final Color MOLE_COLOR = new Color(222, 184, 135);

    private final transient WhacAMoleController controller;

    private final JLabel scoreLabel;
    private final JLabel timerLabel;
    private final JLabel gameOverLabel;
    private final JButton startButton;

    private final JButton[] holeButtons;

    private final ImageIcon moleIcon;
    private final ImageIcon bombIcon;

    private Timer uiRefreshLoop;
    private boolean gameStarted;
    private boolean endDialogShown;

    /**
     * Creates the Swing GUI for the Whac-A-Mole game.
     */
    public WhacAMoleViewImpl() {
        super(new BorderLayout());

        this.controller = new WhacAMoleControllerImpl();

        this.scoreLabel = new JLabel("Score: 0", JLabel.CENTER);
        this.timerLabel = new JLabel("Time: 30s", JLabel.CENTER);

        this.startButton = new JButton("Start Game");

        this.gameOverLabel = new JLabel("", JLabel.CENTER);
        this.gameOverLabel.setFont(
            this.gameOverLabel.getFont().deriveFont(Font.BOLD, GAME_OVER_FONT_SIZE));
        this.gameOverLabel.setForeground(Color.RED);
        this.gameOverLabel.setVisible(false);

        this.holeButtons = new JButton[TOTAL_HOLES];
        this.gameStarted = false;
        this.endDialogShown = false;

        this.moleIcon = loadScaledIcon("/images/whacamole/mole.png", ICON_SIZE, ICON_SIZE);
        this.bombIcon = loadScaledIcon("/images/whacamole/bomb.png", ICON_SIZE, ICON_SIZE);

        final JPanel topPanel = buildTopBar();
        final JPanel gridPanel = buildGrid();
        final JPanel bottomPanel = buildBottomBar();

        this.add(topPanel, BorderLayout.NORTH);
        this.add(gridPanel, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);

        wireActions();
        setupUIRefreshLoop();
        refreshUI();
    }

    /**
     * Loads an image from the resources folder and resizes it.
     *
     * @param path   resource path of the image
     * @param width  desired width in pixels
     * @param height desired height in pixels
     * @return a scaled {@link ImageIcon}, or {@code null} if the resource is not found
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

    private JPanel buildTopBar() {
        final JPanel box = new JPanel(new GridLayout(TOP_ROWS, TOP_COLS, TOP_GAP, TOP_GAP));
        box.add(this.scoreLabel);
        box.add(this.timerLabel);
        return box;
    }

    private JPanel buildGrid() {
        final JPanel grid = new JPanel(
            new GridLayout(GRID_ROWS, GRID_COLS, HOLE_GRID_H_GAP, HOLE_GRID_V_GAP));

        int index = 0;
        for (int r = 0; r < GRID_ROWS; r++) {
            for (int c = 0; c < GRID_COLS; c++) {

                final JButton b = new JButton();
                b.setPreferredSize(new Dimension(HOLE_SIZE, HOLE_SIZE));
                b.setFocusPainted(false);
                b.setBackground(Color.GRAY);

                final int holeIndex = index;
                b.addActionListener(e -> {
                    if (!this.gameStarted) {
                        return;
                    }

                    this.controller.onHoleClicked(holeIndex);
                    refreshUI();
                });

                this.holeButtons[index] = b;
                grid.add(b);
                index++;
            }
        }

        return grid;
    }

    private JPanel buildBottomBar() {
        final JPanel box = new JPanel(
            new GridLayout(BOTTOM_ROWS, BOTTOM_COLS, BOTTOM_GAP, BOTTOM_GAP));
        box.add(this.startButton);
        box.add(this.gameOverLabel);
        return box;
    }

    private void wireActions() {
        this.startButton.addActionListener(e -> {
            if (this.gameStarted) {
                return;
            }

            this.gameStarted = true;
            this.controller.startGame();
            this.startButton.setEnabled(false);
            this.gameOverLabel.setVisible(false);
            refreshUI();
        });
    }

    private void setupUIRefreshLoop() {
        this.uiRefreshLoop = new Timer(UI_REFRESH_MILLIS, e -> refreshUI());
        this.uiRefreshLoop.start();
    }

    private void refreshUI() {
        final WhacAMoleGameState state = this.controller.getState();

        if (!this.gameStarted) {
            this.scoreLabel.setText("Score: 0");
            this.timerLabel.setText("Time: 30s");
            this.gameOverLabel.setVisible(false);

            for (final JButton b : this.holeButtons) {
                b.setIcon(null);
                b.setText("");
                b.setBackground(Color.GRAY);
                b.setEnabled(true);
            }

            return;
        }

        this.scoreLabel.setText("Score: " + state.getScore());
        final long secondsLeft = state.getTimeLeftMillis() / 1000;
        this.timerLabel.setText("Time: " + secondsLeft + "s");

        final int moleIndex = state.getCurrentMoleIndex();
        final boolean isBomb = this.controller.isCurrentObjectABomb();

        for (int i = 0; i < this.holeButtons.length; i++) {
            final JButton b = this.holeButtons[i];

            if (!state.isGameOver() && i == moleIndex) {

                if (isBomb && this.bombIcon != null) {
                    b.setIcon(this.bombIcon);
                    b.setText("");
                } else if (!isBomb && this.moleIcon != null) {
                    b.setIcon(this.moleIcon);
                    b.setText("");
                } else {
                    b.setIcon(null);
                    b.setText(isBomb ? "B" : "M");
                }

                if (isBomb) {
                    b.setBackground(Color.RED);
                } else {
                    b.setBackground(MOLE_COLOR);
                }

                b.setEnabled(true);

            } else {
                b.setIcon(null);
                b.setText("");
                b.setBackground(Color.GRAY);
                b.setEnabled(!state.isGameOver());
            }
        }

        if (state.isGameOver()) {
            if (this.uiRefreshLoop != null) {
                this.uiRefreshLoop.stop();
            }

            this.controller.stopIfGameOver();

            this.gameOverLabel.setText("TIME'S UP!");
            this.gameOverLabel.setVisible(true);

            if (!this.endDialogShown) {
                this.endDialogShown = true;
                JOptionPane.showMessageDialog(
                    this,
                    "Time's up!\nFinal score: " + state.getScore(),
                    "Whac-A-Mole - Game Over",
                    JOptionPane.INFORMATION_MESSAGE
                );

                closeGameWindow();
            }
        }
    }

    /**
     * Closes the top-level game window that contains this panel.
     */
    private void closeGameWindow() {
        final java.awt.Window window =
            javax.swing.SwingUtilities.getWindowAncestor(this);
        if (window != null) {
            window.dispose();
        }
    }

    @Override
    public int getFinalScore() {
        return this.controller.getState().getScore();
    }

    /**
     * @return the encoded result of the Whac-A-Mole match
     */
    public int getResultCode() {
        return this.controller.getResultCode();
    }
}
