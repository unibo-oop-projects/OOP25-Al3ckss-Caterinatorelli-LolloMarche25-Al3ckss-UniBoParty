package it.unibo.uniboparty.view.minigames.sudoku.impl;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import it.unibo.uniboparty.view.minigames.sudoku.api.ISudokuView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Concrete implementation of the {@link ISudokuView} interface using Java Swing.
 *
 * <p>
 * This class manages the main application window, the 9x9 game grid,
 * the number selection buttons, and the end-game dialogs (Win/Loss).
 * It handles the layout and loading of graphical resources.
 */
public class SudokuViewImpl extends JFrame implements ISudokuView {
    private static final long serialVersionUID = 1L;
    private static final int FRAME_WIDTH = 600;
    private static final int FRAME_HEIGHT = 650;
    private static final int BOLD_NUMS = 30;
    private static final int TILES = 9;
    private static final int TILES_FONT = 20;
    private static final int END_PANEL_WIDTH = 300;
    private static final int END_PANEL_HEIGHT = 200;
    private static final int BOX = 10;
    private static final int THICK_BORDER = 5;
    private static final Logger LOGGER = Logger.getLogger(SudokuViewImpl.class.getName());
    private static final String FONT_NAME = "Arial";
    private final JFrame frame = new JFrame("Sudoku");
    private final JLabel textlabel = new JLabel();
    private final JPanel textpanel = new JPanel();
    private final JPanel boardpanel = new JPanel();
    private final JPanel buttonspanel = new JPanel();

    private final Tile[][] tiles = new Tile[TILES][TILES];
    private final JToggleButton[] numberButtons = new JToggleButton[TILES];

    private ImageIcon winIcon;
    private ImageIcon loseIcon;
    private transient Image background;

    /**
     * Constructs the Sudoku View.
     *
     * <p>
     * It initializes the main frame settings, loads the necessary icons/images from
     * the classpath, and sets up the layout for the title, the game board, and the
     * input buttons. Finally, it makes the frame visible.
     */
    public SudokuViewImpl() {
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        loadIcons();

        textlabel.setFont(new Font(FONT_NAME, Font.BOLD, BOLD_NUMS));
        textlabel.setHorizontalAlignment(JLabel.CENTER);
        textlabel.setText("Sudoku");
        textpanel.add(textlabel);
        frame.add(textpanel, BorderLayout.NORTH);

        boardpanel.setLayout(new GridLayout(TILES, TILES));
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
    public void addNumberButtonListener(final ActionListener listener, final int number) {
        numberButtons[number - 1].addActionListener(listener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addTileListener(final ActionListener listener, final int r, final int c) {
        tiles[r][c].addActionListener(listener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTileText(final int r, final int c, final String text) {
        tiles[r][c].setText(text);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTileFixed(final int r, final int c, final String text) {
        final Tile tile = tiles[r][c];
        tile.setText(text);
        tile.setBackground(Color.lightGray);
        tile.setFont(new Font(FONT_NAME, Font.BOLD, TILES_FONT));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateErrorLabel(final int errors) {
        textlabel.setText("Errors: " + errors);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showGameWon() {
        final ImagePanel panel = new ImagePanel(background);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(END_PANEL_WIDTH, END_PANEL_HEIGHT));

        final JLabel iconLabel = new JLabel(winIcon);
        final JLabel textLabel = new JLabel("Congratulations!\n You Won!");
        textLabel.setFont(new Font(FONT_NAME, Font.BOLD, TILES_FONT));
        textLabel.setForeground(Color.BLACK);

        iconLabel.setAlignmentX(CENTER_ALIGNMENT);
        textLabel.setAlignmentX(CENTER_ALIGNMENT);

        panel.add(Box.createVerticalGlue());
        panel.add(iconLabel);
        panel.add(Box.createRigidArea(new Dimension(0, BOX)));
        panel.add(textLabel);
        panel.add(Box.createVerticalGlue());

        disableBoard();

        JOptionPane.showMessageDialog(
                frame,
                panel,
                "Victory!",
                JOptionPane.PLAIN_MESSAGE,
                null
        );
        frame.dispose();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showGameOver() {
        final ImagePanel panel = new ImagePanel(background);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(END_PANEL_WIDTH, END_PANEL_HEIGHT));

        final JLabel iconLabel = new JLabel(loseIcon);
        final JLabel textLabel = new JLabel("3 errors were made. You Lost!");
        textLabel.setFont(new Font(FONT_NAME, Font.BOLD, TILES_FONT));
        textLabel.setForeground(Color.BLACK);

        iconLabel.setAlignmentX(CENTER_ALIGNMENT);
        textLabel.setAlignmentX(CENTER_ALIGNMENT);

        panel.add(Box.createVerticalGlue());
        panel.add(iconLabel);
        panel.add(Box.createRigidArea(new Dimension(0, BOX)));
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
        frame.dispose();
    }

    /**
     * Initializes the keypad buttons(1-9).
     */
    private void setupButtons() {
        buttonspanel.setLayout(new GridLayout(1, TILES));
        final ButtonGroup numberGroup = new ButtonGroup();

        for (int i = 0; i < TILES; i++) {
            final JToggleButton btn = new JToggleButton(String.valueOf(i + 1));
            btn.setFont(new Font(FONT_NAME, Font.BOLD, TILES_FONT));
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
        for (int rownum = 0; rownum < TILES; rownum++) {
            for (int colnum = 0; colnum < TILES; colnum++) {
                final Tile tile = new Tile(rownum, colnum);
                tile.setFont(new Font(FONT_NAME, Font.PLAIN, TILES_FONT));
                tile.setBackground(Color.white);
                tile.setFocusable(false);

                if (rownum == 2 && colnum == 2 || rownum == 2 && colnum == THICK_BORDER
                        || rownum == THICK_BORDER && colnum == 2 || rownum == THICK_BORDER && colnum == THICK_BORDER) {
                    tile.setBorder(BorderFactory.createMatteBorder(1, 1, THICK_BORDER, THICK_BORDER, Color.black));
                } else if (rownum == 2 || rownum == THICK_BORDER) {
                    tile.setBorder(BorderFactory.createMatteBorder(1, 1, THICK_BORDER, 1, Color.black));
                } else if (colnum == 2 || colnum == THICK_BORDER) {
                    tile.setBorder(BorderFactory.createMatteBorder(1, 1, 1, THICK_BORDER, Color.black));
                } else {
                    tile.setBorder(BorderFactory.createLineBorder(Color.black));
                }

                tiles[rownum][colnum] = tile;
                boardpanel.add(tile);
            }
        }
    }

    /**
     * Returns the main frame used by this view.
     *
     * @return the underlying {@link JFrame}
     */
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification = "The JFrame is the main Sudoku window and needs to be "
                    + "returned so that other components (controllers / intro frame) "
                    + "can show or close it. This is an intentional exposure."
    )
    public JFrame getFrame() {
        return this.frame;
    }

    /**
     * Loads and scales the icons and background images from the resources.
     */
    private void loadIcons() {
        final URL winUrl = getClass().getClassLoader().getResource("sudoku_icons/win.png");
        final URL loseUrl = getClass().getClassLoader().getResource("sudoku_icons/lose.png");
        final URL bgUrl = getClass().getClassLoader().getResource("sudoku_icons/background.jpeg");

        if (bgUrl != null) {
            background = new ImageIcon(bgUrl).getImage();
        } else {
            LOGGER.log(Level.WARNING, "background 'background.jpeg' not found!");
        }

        if (winUrl != null) {
            final ImageIcon original = new ImageIcon(winUrl);
            final Image scaled = original.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
            winIcon = new ImageIcon(scaled);
        } else {
            LOGGER.log(Level.WARNING, "Icon 'win.png' not found!");
        }

        if (loseUrl != null) {
            final ImageIcon original = new ImageIcon(loseUrl);
            final Image scaled = original.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
            loseIcon = new ImageIcon(scaled);
        } else {
            LOGGER.log(Level.WARNING, "Icon 'lose.png' not found!");
        }
    }

    /**
     * Disables all interactive components on the board (tiles and buttons).
     */
    private void disableBoard() {
        for (int r = 0; r < TILES; r++) {
            for (int c = 0; c < TILES; c++) {
                tiles[r][c].setEnabled(false);
            }
        }
        for (final JToggleButton btn : numberButtons) {
            btn.setEnabled(false);
        }
    }
}
