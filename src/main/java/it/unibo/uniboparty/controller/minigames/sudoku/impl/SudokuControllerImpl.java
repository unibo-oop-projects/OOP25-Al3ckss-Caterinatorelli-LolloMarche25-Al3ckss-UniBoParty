package it.unibo.uniboparty.controller.minigames.sudoku.impl;

import it.unibo.uniboparty.controller.minigames.sudoku.api.ISudokuController;
import it.unibo.uniboparty.model.minigames.sudoku.api.ISudokuModel;
import it.unibo.uniboparty.view.minigames.sudoku.api.ISudokuView;
import it.unibo.uniboparty.model.minigames.sudoku.impl.SudokuModelImpl;
import it.unibo.uniboparty.view.minigames.sudoku.impl.SudokuViewImpl;

import javax.swing.JToggleButton;

/**
 * Concrete implementation of the Sudoku Controller.
 *
 * <p>
 * It initializes the game state by querying the model and setting up the view.
 * It also manages event listeners to handle user inputs (number selection and tile clicks).
 * </p>
 */
public class SudokuControllerImpl implements ISudokuController {

    private static final int GRID_SIZE = 9;

    private final ISudokuModel model;
    private final ISudokuView view;

    private int selectedNumber = -1;

    /**
     * Constructs the controller and starts the game initialization.
     */
    public SudokuControllerImpl() {
        // FIX PER SPOTBUGS (EI2):
        // Creiamo gli oggetti qui dentro. Così nessuno fuori ha il riferimento
        // e SpotBugs sa che sono "al sicuro" dentro il Controller.
        this.model = new SudokuModelImpl();
        this.view = new SudokuViewImpl();

        initController();
        initView();
    }

    /**
     * Populates the View with the initial puzzle data from the Model.
     */
    private void initView() {
        for (int r = 0; r < GRID_SIZE; r++) {
            for (int c = 0; c < GRID_SIZE; c++) {
                final String val = model.getInitialPuzzleAt(r, c);
                if (!"-".equals(val)) {
                    view.setTileFixed(r, c, val);
                }
            }
        }
        view.updateErrorLabel(0);
    }

    /**
     * Registers all necessary listeners on the View components.
     */
    private void initController() {
        for (int i = 1; i <= GRID_SIZE; i++) {
            final int number = i;
            view.addNumberButtonListener(e -> {
                final JToggleButton btn = (JToggleButton) e.getSource();
                if (btn.isSelected()) {
                    selectedNumber = number;
                } else {
                    selectedNumber = -1;
                }
            }, number);
        }

        for (int r = 0; r < GRID_SIZE; r++) {
            for (int c = 0; c < GRID_SIZE; c++) {
                final int row = r;
                final int col = c;
                view.addTileListener(e -> onTileClick(row, col), r, c);
            }
        }
    }

    /**
     * Main game's Logic, called by a listener from the View.
     *
     * @param r rows.
     * @param c columns.
     */
    private void onTileClick(final int r, final int c) {
        if (selectedNumber == -1) {
            return;
        }

        if (model.isCellFixed(r, c)) {
            return;
        }

        final boolean isCorrect = model.isMoveCorrect(r, c, selectedNumber);

        if (isCorrect) {
            view.setTileText(r, c, String.valueOf(selectedNumber));
            if (model.isGameWon()) {
                view.showGameWon();
            }
        } else {
            view.updateErrorLabel(model.getErrorCount());
            if (model.getErrorCount() >= 3) {
                view.showGameOver();
            }
        }
    }
}
