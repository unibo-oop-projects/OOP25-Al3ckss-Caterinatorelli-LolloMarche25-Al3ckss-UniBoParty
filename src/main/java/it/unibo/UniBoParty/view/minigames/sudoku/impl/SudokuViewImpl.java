package it.unibo.UniBoParty.view.minigames.sudoku.impl;

import it.unibo.UniBoParty.view.minigames.sudoku.api.ISudokuView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.net.URL;
import java.awt.Image;
import java.awt.Font;
import javax.swing.Box;
import javax.swing.BoxLayout;
import java.awt.Component;
import java.awt.Dimension;

/**
 * Concrete implementation of the Sudoku View using Java Swing.
 * <p>
 * This class manages the main application window, the 9x9 game grid,
 * the number selection buttons, and the end-game dialogs (Win/Loss).
 * It loads graphical resources (icons, backgrounds) and handles the layout.
 * </p>
 */
public class SudokuViewImpl implements ISudokuView {

    private final JFrame frame = new JFrame("Sudoku");
    private final JLabel textlabel = new JLabel();
    private final JPanel textpanel = new JPanel();
    private final JPanel boardpanel = new JPanel();
    private final JPanel buttonspanel = new JPanel();

    private final Tile[][] tiles = new Tile[9][9];
    private final JToggleButton[] numberButtons = new JToggleButton[9];

    private ImageIcon winIcon;
    private ImageIcon loseIcon;
    private Image Background;

    /**
     * Constructs the Sudoku View.
     * <p>
     * Initializes the main frame settings, loads the icons/images,
     * and sets up the layout for the title, the game board, and the buttons.
     * Finally, it makes the frame visible.
     * </p>
     */
    public SudokuViewImpl() {
        frame.setSize(600, 650);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        loadIcons();

        textlabel.setFont(new Font("Arial", Font.BOLD, 30));
        textlabel.setHorizontalAlignment(JLabel.CENTER);
        textlabel.setText("Sudoku");
        textpanel.add(textlabel);
        frame.add(textpanel, BorderLayout.NORTH);

        boardpanel.setLayout(new GridLayout(9, 9));
        setupTile();
        frame.add(boardpanel, BorderLayout.CENTER);

        setupButtons();
        frame.add(buttonspanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addNumberButtonListener(ActionListener listener, int number) {
        numberButtons[number - 1].addActionListener(listener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addTileListener(ActionListener listener, int r, int c) {
        tiles[r][c].addActionListener(listener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTileText(int r, int c, String text) {
        tiles[r][c].setText(text);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTileFixed(int r, int c, String text) {
        Tile tile = tiles[r][c];
        tile.setText(text);
        tile.setBackground(Color.lightGray);
        tile.setFont(new Font("Arial", Font.BOLD, 20));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateErrorLabel(int errors) {
        textlabel.setText("Errori: " + errors);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showGameWon() {
        ImagePanel panel = new ImagePanel(Background);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(300, 200));

        JLabel iconLabel = new JLabel(winIcon);
        JLabel textLabel = new JLabel("Congratulazioni!\n Hai vinto!");
        textLabel.setFont(new Font("Arial", Font.BOLD, 20));
        textLabel.setForeground(Color.BLACK);

        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        textLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createVerticalGlue());
        panel.add(iconLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(textLabel);
        panel.add(Box.createVerticalGlue());

        disableBoard();

        JOptionPane.showMessageDialog(
                frame,
                panel,
                "Vittoria!",
                JOptionPane.PLAIN_MESSAGE,
                null
        );
        System.exit(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showGameOver() {
        ImagePanel panel = new ImagePanel(Background);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(500, 300));

        JLabel iconLabel = new JLabel(loseIcon);
        JLabel textLabel = new JLabel("Hai commesso 3 errori. Hai perso!");
        textLabel.setFont(new Font("Arial", Font.BOLD, 20));
        textLabel.setForeground(Color.BLACK);

        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        textLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createVerticalGlue());
        panel.add(iconLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(textLabel);
        panel.add(Box.createVerticalGlue());

        disableBoard();

        JOptionPane.showMessageDialog(
                frame,
                panel,
                "Game Over",
                JOptionPane.PLAIN_MESSAGE,
                null
        );
        System.exit(0);
    }

    /**
     * Initializes the keypad buttons(1-9).
     */
    private void setupButtons() {
        buttonspanel.setLayout(new GridLayout(1, 9));
        ButtonGroup numberGroup = new ButtonGroup();

        for (int i = 0; i < 9; i++) {
            JToggleButton btn = new JToggleButton(String.valueOf(i + 1));
            btn.setFont(new Font("Arial", Font.BOLD, 20));
            btn.setFocusable(false);
            numberGroup.add(btn);
            numberButtons[i] = btn;
            buttonspanel.add(btn);
        }
    }

    /**
     * Initializes the grid tiles and applies the specific border styling
     * to visualize 3x3 subgrids.
     */
    private void setupTile() {
        for (int rownum = 0; rownum < 9; rownum++) {
            for (int colnum = 0; colnum < 9; colnum++) {
                Tile tile = new Tile(rownum, colnum);
                tile.setFont(new Font("Arial", Font.PLAIN, 20));
                tile.setBackground(Color.white);
                tile.setFocusable(false);

                if ((rownum == 2 && colnum == 2) || (rownum == 2 && colnum == 5) || (rownum == 5 && colnum == 2) || (rownum == 5 && colnum == 5)) {
                    tile.setBorder(BorderFactory.createMatteBorder(1, 1, 5, 5, Color.black));
                } else if (rownum == 2 || rownum == 5) {
                    tile.setBorder(BorderFactory.createMatteBorder(1, 1, 5, 1, Color.black));
                } else if (colnum == 2 || colnum == 5) {
                    tile.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 5, Color.black));
                } else {
                    tile.setBorder(BorderFactory.createLineBorder(Color.black));
                }

                tiles[rownum][colnum] = tile;
                boardpanel.add(tile);
            }
        }
    }

    /**
     * Loads and scales the icons and background images from the resources.
     */
    private void loadIcons() {
        try {
            URL winUrl = getClass().getClassLoader().getResource("sudoku_icons/win.png");
            URL loseUrl = getClass().getClassLoader().getResource("sudoku_icons/lose.png");
            URL Bg = getClass().getClassLoader().getResource("resources/background.png");
            if (Bg != null) {
                Background = new ImageIcon(Bg).getImage();
            } else {
                System.err.println("Sfondo 'Background.png' non trovato!");
            }
            if (winUrl != null) {
                ImageIcon originalIcon = new ImageIcon(winUrl);
                Image scaledImage = originalIcon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
                winIcon = new ImageIcon(scaledImage);
            } else {
                System.err.println("Icona 'win.png' non trovata!");
            }

            if (loseUrl != null) {
                ImageIcon originalIcon = new ImageIcon(loseUrl);
                Image scaledImage = originalIcon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
                loseIcon = new ImageIcon(scaledImage);
            } else {
                System.err.println("Icona 'lose.png' non trovata!");
            }

        } catch (Exception e) {
            System.err.println("Errore durante il caricamento delle icone.");
            e.printStackTrace();
        }
    }

    /**
     * Disables all interactive components on the board (tiles and buttons).
     */
    private void disableBoard() {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                tiles[r][c].setEnabled(false);
            }
        }
        for (JToggleButton btn : numberButtons) {
            btn.setEnabled(false);
        }
    }
}