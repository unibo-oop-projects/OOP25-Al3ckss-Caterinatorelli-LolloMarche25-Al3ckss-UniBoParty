package it.unibo.uniboparty.model.minigames.sudoku.impl;

import it.unibo.uniboparty.model.minigames.sudoku.api.ISudokuModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Concrete implementation of the Sudoku Model.
 *
 * <p>
 * It loads puzzles from a text file resources and manages the game logic,
 * including move validation and victory conditions.
 * </p>
 */
public final class SudokuModelImpl implements ISudokuModel {

    private static final int GRID_SIZE = 9;
    private static final Random RANDOM = new Random();
    private static final Logger LOGGER = Logger.getLogger(SudokuModelImpl.class.getName());
    private String[] puzzle;
    private String[] solution;
    private int errors;
    private final String[] userGrid;

    /**
     * Constructs a new SudokuModel.
     * Initializes the error count and loads a random puzzle from the resources.
     */
    public SudokuModelImpl() {
        this.errors = 0;
        loadRandomPuzzle();

        this.userGrid = new String[GRID_SIZE];
        System.arraycopy(puzzle, 0, userGrid, 0, GRID_SIZE);
    }

    @Override
    public String getInitialPuzzleAt(final int r, final int c) {
        return String.valueOf(puzzle[r].charAt(c));
    }

    @Override
    public int getErrorCount() {
        return this.errors;
    }

    @Override
    public boolean isMoveCorrect(final int r, final int c, final int number) {
        final String numStr = String.valueOf(number);
        final String solutionChar = String.valueOf(solution[r].charAt(c));

        if (solutionChar.equals(numStr)) {
            userGrid[r] = userGrid[r].substring(0, c) + numStr + userGrid[r].substring(c + 1);
            return true;
        } else {
            this.errors++;
            return false;
        }
    }

    @Override
    public boolean isCellFixed(final int r, final int c) {
        return puzzle[r].charAt(c) != '-';
    }

    @Override
    public boolean isGameWon() {
        for (int i = 0; i < GRID_SIZE; i++) {
            if (!userGrid[i].equals(solution[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * Tries to load puzzles from the file 'puzzles.txt' in the resources.
     * If failed, uses a fallback puzzle.
     */
    private void loadRandomPuzzle() {
        final List<String[]> puzzles = new ArrayList<>();

        try (InputStream is = getClass().getClassLoader().getResourceAsStream("puzzles.txt")) {

            if (is == null) {
                loadFallbackPuzzle();
                return;
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {

                String line = br.readLine();

                while (line != null) {

                    if (line.isBlank()) {
                        line = br.readLine();
                        continue;
                    }

                    final String[] puzzleLines = line.split(",");

                    final String solutionLine = br.readLine();
                    if (solutionLine != null) {
                        final String[] solutionLines = solutionLine.split(",");
                        puzzles.add(new String[]{String.join(",", puzzleLines), String.join(",", solutionLines)});
                    }

                    line = br.readLine();
                }
            }

        } catch (final IOException e) {
            LOGGER.log(Level.SEVERE, "READING ERROR 'puzzles.txt'! Using fallback puzzle.", e);

            loadFallbackPuzzle();
            return;
        }

        if (puzzles.isEmpty()) {
            // CORREGGE RIGA 114:
            LOGGER.log(Level.WARNING, "'puzzles.txt' is empty! Using fallback puzzle.");

            loadFallbackPuzzle();
            return;
        }

        LOGGER.log(Level.INFO, "Import from 'puzzles.txt' successful.");

        final int index = RANDOM.nextInt(puzzles.size());
        puzzle = puzzles.get(index)[0].split(",");
        solution = puzzles.get(index)[1].split(",");
    }

    /**
     * Load Fallback puzzle only if importing from the file fails.
     */
    private void loadFallbackPuzzle() {
        puzzle = new String[]{
                "--74916-5", "2---6-3-9", "-----7-1-",
                "-586----4", "--3----9-", "--62--187",
                "9-4-7---2", "67-83----", "81--45---",
        };
        solution = new String[]{
                "387491625", "241568379", "569327418",
                "758619234", "123784596", "496253187",
                "934176852", "675832941", "812945763",
        };
    }
}
