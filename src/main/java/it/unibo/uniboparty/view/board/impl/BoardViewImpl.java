package it.unibo.uniboparty.view.board.impl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import it.unibo.uniboparty.controller.board.api.BoardController;
import it.unibo.uniboparty.controller.board.impl.BoardControllerImpl;
import it.unibo.uniboparty.model.board.CellType;
import it.unibo.uniboparty.model.board.api.BoardModel;
import it.unibo.uniboparty.model.board.impl.BoardModelImpl;
import it.unibo.uniboparty.view.board.api.BoardView;

/**
 * Swing implementation of the {@link BoardView} interface.
 *
 * <p>
 * This view displays the game board using a snake-like layout. It highlights:
 * <ul>
 *   <li>the start and finish cells,</li>
 *   <li>minigame cells,</li>
 *   <li>special effect cells (move back, swap player),</li>
 *   <li>the cell currently occupied by the player.</li>
 * </ul>
 * </p>
 *
 * <p>
 * When the player moves onto a MINIGAME cell, the view automatically
 * launches the intro screen of the corresponding minigame (e.g., Whac-A-Mole).
 * </p>
 *
 * <p>
 * The view contains no game logic: all decisions are delegated
 * to the {@link BoardController}, and the view only reacts to
 * the information provided by it.
 * </p>
 */
public final class BoardViewImpl extends JPanel implements BoardView {

    private static final long serialVersionUID = 1L;

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

    /** Background color for "go back 2" cells. */
    private static final Color COLOR_BACK_2 = Color.decode("#ffd27f");

    /** Background color for "swap" cells. */
    private static final Color COLOR_SWAP = Color.decode("#c2f0ff");

    private static final String START_PAREN = "(";
    private static final String END_PAREN = ")";

    private final transient BoardController controller;

    private final JPanel boardGrid;

    /**
     * Logical position of the player (cell index).
     * Defaults to zero (start cell).
     */
    private int playerPosition;

    /**
     * Creates the Swing view of the main board.
     *
     * <p>
     * The view internally builds its own {@link BoardModel} and
     * {@link BoardController}. It renders the board grid and initializes
     * the player's position to the Start cell (index 0).
     * </p>
     */
    public BoardViewImpl() {
        final BoardModel model = new BoardModelImpl();
        this.controller = new BoardControllerImpl(model);

        this.setLayout(new BorderLayout());

        this.boardGrid = new JPanel(new GridBagLayout());
        this.boardGrid.setBorder(new EmptyBorder(PADDING, PADDING, PADDING, PADDING));

        this.add(this.boardGrid, BorderLayout.CENTER);

        this.refresh();
    }

    /**
     * Rebuilds the whole board grid according to the current model
     * and player position.
     */
    private void refresh() {
        this.boardGrid.removeAll();

        final int size = this.controller.getBoardSize();
        final int lastIndex = size - 1;

        for (int i = 0; i < size; i++) {
            final CellType type = this.controller.getCellTypeAt(i);
            final String text = this.getCellText(i, lastIndex, type);

            final JLabel cellLabel = new JLabel(text);
            cellLabel.setFont(new Font("Arial", Font.BOLD, 10));
            cellLabel.setPreferredSize(new Dimension(CELL_WIDTH, CELL_HEIGHT));
            cellLabel.setHorizontalAlignment(SwingConstants.CENTER);
            cellLabel.setVerticalAlignment(SwingConstants.CENTER);
            cellLabel.setOpaque(true);
            cellLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            cellLabel.setBackground(this.getBackgroundColor(i, lastIndex, type));

            final int row = i / COLUMNS;
            final int col = this.computeColumn(i, row);

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
     * Computes the column index for a given logical index and row,
     * using a snake-like (serpentine) pattern.
     *
     * @param index the logical cell index
     * @param row   the row index
     * @return the column where the cell should be placed
     */
    private int computeColumn(final int index, final int row) {
        if (row % 2 == 0) {
            // even row: left to right
            return index % COLUMNS;
        }
        // odd row: right to left
        return COLUMNS - 1 - (index % COLUMNS);
    }

    /**
     * Returns the text label to display inside a cell.
     * Special cells (Start, Finish, Minigame, Back-2, Swap) have predefined labels,
     * while normal cells remain empty.
     *
     * @param index     cell index
     * @param lastIndex last cell index
     * @param type      type of the cell
     * @return the text for that cell
     */
    private String getCellText(final int index, final int lastIndex, final CellType type) {
        if (index == 0) {
            return "Start";
        }
        if (index == lastIndex) {
            return "Finish";
        }
        if (type == CellType.MINIGAME) {
            return "MG" + START_PAREN + index + END_PAREN;
        }
        if (type == CellType.BACK_2) {
            return "-2" + START_PAREN + index + END_PAREN;
        }
        if (type == CellType.SWAP) {
            return "SWAP" + START_PAREN + index + END_PAREN;
        }
        return START_PAREN + index + END_PAREN;
    }

    /**
     * Computes the background color for the given cell index.
     *
     * <p>
     * The method highlights:
     * <ul>
     *   <li>the player's current position,</li>
     *   <li>the start and finish cells,</li>
     *   <li>minigame cells,</li>
     *   <li>special effect cells (back-2, swap).</li>
     * </ul>
     * </p>
     *
     * @param index     cell index
     * @param lastIndex last cell index
     * @param type      cell type
     * @return the background color to use
     */
    private Color getBackgroundColor(final int index, final int lastIndex, final CellType type) {
        if (index == this.playerPosition) {
            return COLOR_PLAYER;
        }
        if (index == 0) {
            return COLOR_START;
        }
        if (index == lastIndex) {
            return COLOR_FINISH;
        }
        if (type == CellType.MINIGAME) {
            return COLOR_MINIGAME;
        }
        if (type == CellType.BACK_2) {
            return COLOR_BACK_2;
        }
        if (type == CellType.SWAP) {
            return COLOR_SWAP;
        }
        return COLOR_NORMAL;
    }

    /**
     * Updates the player position on the board and refreshes the UI.
     *
     * <p>
     * This method only moves the pawn and redraws the board.
     * If you want to start a minigame after the move, it must be done
     * by the code that calls this method.
     * </p>
     *
     * @param position index of the destination cell
     * @throws IllegalArgumentException if {@code position} is outside the board bounds
     */
    @Override
    public void setPlayerPosition(final int position) {

        if (position < 0 || position >= this.controller.getBoardSize()) {
            throw new IllegalArgumentException("Invalid player position: " + position);
        }
        this.playerPosition = position;
        this.refresh();
    }

    @Override
    public BoardController getController() {
        return this.controller;
    }
}
