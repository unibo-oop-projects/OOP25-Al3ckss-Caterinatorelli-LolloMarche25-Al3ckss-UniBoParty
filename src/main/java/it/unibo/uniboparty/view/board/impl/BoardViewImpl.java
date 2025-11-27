package it.unibo.uniboparty.view.board.impl;

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

import it.unibo.uniboparty.controller.board.api.BoardController;
import it.unibo.uniboparty.controller.board.impl.BoardControllerImpl;
import it.unibo.uniboparty.model.board.CellType;
import it.unibo.uniboparty.model.board.api.BoardModel;
import it.unibo.uniboparty.model.board.impl.BoardModelImpl;
import it.unibo.uniboparty.view.board.api.BoardView;

/**
 * Swing view of the main board.
 *
 * <p>
 * Displays the board as a grid arranged in a snake-like pattern.
 * The view highlights the start cell, the finish cell, minigame cells,
 * and the cell where the player currently stands.
 * </p>
 */
public final class BoardViewImpl extends JPanel implements BoardView {

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
     * Returns the text to display inside the cell.
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
            return "MG";
        }
        return "";
    }

    /**
     * Computes the background color for the given cell index.
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
        return COLOR_NORMAL;
    }

    @Override
    public void setPlayerPosition(final int position) {
        // semplice guard-rail, così se qualcuno passa un indice sbagliato non esplode tutto
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
