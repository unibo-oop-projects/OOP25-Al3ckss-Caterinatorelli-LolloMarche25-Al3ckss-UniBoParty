package it.unibo.UniBoParty.controller.minigames.sudoku.impl;

import it.unibo.UniBoParty.controller.minigames.sudoku.api.ISudokuController;
import it.unibo.UniBoParty.model.minigames.sudoku.api.ISudokuModel;
import it.unibo.UniBoParty.view.minigames.sudoku.api.ISudokuView;

import javax.swing.JToggleButton;

/**
 * Concrete implementation of the Sudoku Controller.
 * <p>
 * It initializes the game state by querying the model and setting up the view.
 * It also manages event listeners to handle user inputs (number selection and tile clicks).
 * </p>
 */
public class SudokuControllerImpl implements ISudokuController {

    private final ISudokuModel model;
    private final ISudokuView view;

    private int selectedNumber = -1;

    /**
     * Constructs the controller and starts the game initialization.
     * * @param model The data model instance.
     * @param view  The graphical user interface instance.
     */
    public SudokuControllerImpl(final ISudokuModel model, final ISudokuView view) {
        this.model = model;
        this.view = view;

        // Avvia il setup
        initController();
        initView();
    }

    /**
     * Populates the View with the initial puzzle data from the Model.
     */
    private void initView() {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                String val = model.getInitialPuzzleAt(r, c);
                if (!val.equals("-")) {
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
        for (int i = 1; i <= 9; i++) {
            final int number = i;
            view.addNumberButtonListener(e -> {
                JToggleButton btn = (JToggleButton) e.getSource();
                if (btn.isSelected()) {
                    selectedNumber = number;
                } else {
                    selectedNumber = -1;
                }
            }, number);
        }

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                final int row = r;
                final int col = c;
                view.addTileListener(e -> onTileClick(row, col), r, c);
            }
        }
    }

    /**
     * Main game's Logic, called by a listener from the View.
     */
    private void onTileClick(int r, int c) {
        if (selectedNumber == -1) {
            return;
        }

        if (model.isCellFixed(r, c)) {
            return;
        }

        boolean isCorrect = model.isMoveCorrect(r, c, selectedNumber);

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