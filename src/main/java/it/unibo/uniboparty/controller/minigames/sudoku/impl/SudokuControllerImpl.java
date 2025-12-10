package it.unibo.uniboparty.controller.minigames.sudoku.impl;

import it.unibo.uniboparty.controller.minigames.sudoku.api.ISudokuController;
import it.unibo.uniboparty.model.minigames.sudoku.api.ISudokuModel;
import it.unibo.uniboparty.view.minigames.sudoku.api.ISudokuView;
import it.unibo.uniboparty.model.minigames.sudoku.impl.SudokuModelImpl;
import it.unibo.uniboparty.view.minigames.sudoku.impl.SudokuViewImpl;

import javax.swing.JToggleButton;
import javax.swing.JFrame;

/**
 * Concrete implementation of the {@link ISudokuController} interface.
 *
 * <p>
 * This class manages the flow of the Sudoku minigame. It handles the initialization
 * of the grid based on the model's data, manages the currently selected number
 * from the input palette, and processes user interactions with the game board
 * (validating moves and checking for game-over conditions).
 * </p>
 */
public final class SudokuControllerImpl implements ISudokuController {

    private static final int GRID_SIZE = 9;

    /**
     * Result codes used by the main board:
     * 2 = in progress, 1 = won, 0 = lost.
     */
    private static final int RESULT_LOST = 0;
    private static final int RESULT_WON = 1;
    private static final int RESULT_IN_PROGRESS = 2;

    private final ISudokuModel model;
    private final ISudokuView view;

    /**
     * Stores the number currently selected by the user from the side panel.
     * Default is -1 (no selection).
     */
    private int selectedNumber = -1;

    /**
     * Encoded game result, used by the main UniBoParty board.
     */
    private int resultCode;

    /**
     * Constructs the controller and immediately initializes the game components.
     *
     * <p>
     * It internally instantiates the {@link SudokuModelImpl} and {@link SudokuViewImpl}
     * to satisfy strict MVC encapsulation and static analysis requirements.
     * </p>
     */
    public SudokuControllerImpl() {
        this.model = new SudokuModelImpl();
        this.view = new SudokuViewImpl();
        this.resultCode = RESULT_IN_PROGRESS;

        initController();
        initView();
    }

    /**
     * Populates the View with the initial puzzle data retrieved from the Model.
     *
     * <p>
     * It iterates through the grid and marks non-empty cells as "fixed" in the view,
     * ensuring the user cannot modify the starting numbers.
     * </p>
     */
    private void initView() {
        for (int r = 0; r < GRID_SIZE; r++) {
            for (int c = 0; c < GRID_SIZE; c++) {
                final String val = this.model.getInitialPuzzleAt(r, c);
                if (!"-".equals(val)) {
                    this.view.setTileFixed(r, c, val);
                }
            }
        }
        this.view.updateErrorLabel(0);
    }

    /**
     * Registers all necessary listeners on the View components.
     */
    private void initController() {
        for (int i = 1; i <= GRID_SIZE; i++) {
            final int number = i;
            this.view.addNumberButtonListener(e -> {
                final JToggleButton btn = (JToggleButton) e.getSource();
                if (btn.isSelected()) {
                    this.selectedNumber = number;
                } else {
                    this.selectedNumber = -1;
                }
            }, number);
        }

        for (int r = 0; r < GRID_SIZE; r++) {
            for (int c = 0; c < GRID_SIZE; c++) {
                final int row = r;
                final int col = c;
                this.view.addTileListener(e -> onTileClick(row, col), r, c);
            }
        }
    }

    /**
     * Main game logic, called by a listener from the View.
     *
     * @param r row index
     * @param c column index
     */
    private void onTileClick(final int r, final int c) {
        if (this.selectedNumber == -1) {
            return;
        }

        if (this.model.isCellFixed(r, c)) {
            return;
        }

        final boolean isCorrect = this.model.isMoveCorrect(r, c, this.selectedNumber);

        if (isCorrect) {
            this.view.setTileText(r, c, String.valueOf(this.selectedNumber));
            if (this.model.isGameWon()) {
                this.resultCode = RESULT_WON;
                this.view.showGameWon();
            }
        } else {
            this.view.updateErrorLabel(this.model.getErrorCount());
            if (this.model.getErrorCount() >= 3) {
                this.resultCode = RESULT_LOST;
                this.view.showGameOver();
            }
        }
    }

    /**
     * Returns the encoded result of the current match.
     *
     * @return the result code
     */
    public int getResultCode() {
        return this.resultCode;
    }

    /**
     * Returns the Swing frame used to show the Sudoku game window.
     *
     * @return the underlying {@link JFrame}
     */
    public JFrame getGameFrame() {
        return ((SudokuViewImpl) this.view).getFrame();
    }
}
