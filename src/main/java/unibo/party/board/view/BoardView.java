package unibo.party.board.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import unibo.party.board.controller.BoardController;
import unibo.party.board.model.BoardModel;
import unibo.party.board.model.CellType;

/**
 * Swing view of the main board.
 *
 * <p>
 * Displays the board as a grid arranged in a snake-like pattern.
 * The view higlights the start cell, the finish cell, minigame cells,
 * and the cell where the player currently stands.
 * </p>
 */
public final class BoardView extends JPanel {

    /** Number of columns of the board. */
    private static final int COLUMNS = 8;

    /** Horizontal and vertical gap between cells (in pixels). */
    private static final int GAP = 5;

    /** Padding around the board grid (in pixels). */
    private static final int PADDING = 10;

    /** Preferred width of each cell. */
    private static final int CELL_WIDTH = 60;

    /** Preferred height of each cell. */
    private static final int CELL_HEIGHT = 40;

    /** Background color for the player cell. */
    private static final Color COLOR_PLAYER = Color.YELLOW;

    /** Background color for the start cell. */
    private static final Color COLOR_START = Color.decode("#a2ff9d");

    /** Background color for the finish cell. */
    private static final Color COLOR_FINISH = Color.decode("#ffb3b3");

    /** Background color for minigame cells. */
    private static final Color COLOR_MINIGAME = Color.decode("#ffcccc");

    /** Background color for normal cells. */
    private static final Color COLOR_NORMAL = Color.decode("#ddddff");

    private final BoardController controller;
    private final JPanel boardGrid;

    /**
     * Logical position of the player (cell index).
     * Defaults to zero (start cell).
     */
    private int playerPosition;

    /**
     * Creates the view for the main board using its own model and controller.
     */
    public BoardView() {
        final BoardModel model = new BoardModel();
        this.controller = new BoardController(model);

        this.setLayout(new BorderLayout());

        this.boardGrid = new JPanel(new GridBagLayout());
        this.boardGrid.setBorder(new EmptyBorder(PADDING, PADDING, PADDING, PADDING));

        this.add(this.boardGrid, BorderLayout.CENTER);

        this.drawBoard();
    }

    /**
     * Redraws the whole board grid according to the current model
     * and player position.
     */
    private void drawBoard() {
        this.boardGrid.removeAll();

        final int size = this.controller.getBoardSize();
        final int lastIndex = size - 1;

        for (int i = 0; i < size; i++) {
            final CellType type = this.controller.getCellTypeAt(i);

            // Text to be displayed in the cell
            final String text;
            if (i == 0) {
                text = "Start";
            } else if (i == lastIndex) {
                text = "Finish";
            } else if (type == CellType.MINIGAME) {
                text = "MG";
            } else {
                text = "";
            }

            final JLabel cellLabel = new JLabel(text);
            cellLabel.setPreferredSize(new Dimension(CELL_WIDTH, CELL_HEIGHT));
            cellLabel.setHorizontalAlignment(SwingConstants.CENTER);
            cellLabel.setVerticalAlignment(SwingConstants.CENTER);
            cellLabel.setOpaque(true);
            cellLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            final Color bg;
            if (i == this.playerPosition) {
                bg = COLOR_PLAYER;
            } else if (i == 0) {
                bg = COLOR_START;
            } else if (i == lastIndex) {
                bg = COLOR_FINISH;
            } else if (type == CellType.MINIGAME) {
                bg = COLOR_MINIGAME;
            } else {
                bg = COLOR_NORMAL;
            }
            cellLabel.setBackground(bg);

            final int row = i / COLUMNS;

            final int col;
            if (row % 2 == 0) {
                col = i % COLUMNS;
            } else {
                col = COLUMNS - 1 - (i % COLUMNS);
            }

            final GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = col;
            gbc.gridy = row;
            gbc.insets = new Insets(GAP, GAP, GAP, GAP);
            gbc.fill = GridBagConstraints.BOTH;

            this.boardGrid.add(cellLabel, gbc);
        }

        this.boardGrid.revalidate();
        this.boardGrid.repaint();
    }

    /**
     * Updates the logical position of the player and redraws the board.
     *
     * @param position the index of the cell where the player is currently located
     */
    public void setPlayerPosition(final int position) {
        this.playerPosition = position;
        this.drawBoard();
    }

    /**
     * Returns the controller used by this view.
     *
     * @return the board controller
     */
    public BoardController getController() {
        return this.controller;
    }
}
