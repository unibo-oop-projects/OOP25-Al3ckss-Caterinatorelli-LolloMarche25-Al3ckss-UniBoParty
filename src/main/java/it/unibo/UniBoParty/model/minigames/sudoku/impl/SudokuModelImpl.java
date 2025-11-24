package it.unibo.UniBoParty.model.minigames.sudoku.impl;

// Importa l'interfaccia dal package aggiornato
import it.unibo.UniBoParty.model.minigames.sudoku.api.ISudokuModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Concrete implementation of the Sudoku Model.
 * <p>
 * It loads puzzles from a text file resources and manages the game logic,
 * including move validation and victory conditions.
 * </p>
 */
public class SudokuModelImpl implements ISudokuModel {

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

        this.userGrid = new String[9];
        System.arraycopy(puzzle, 0, userGrid, 0, 9);
    }

    @Override
    public String getInitialPuzzleAt(int r, int c) {
        return String.valueOf(puzzle[r].charAt(c));
    }

    @Override
    public int getErrorCount() {
        return this.errors;
    }

    @Override
    public boolean isMoveCorrect(int r, int c, int number) {
        String numStr = String.valueOf(number);
        String solutionChar = String.valueOf(solution[r].charAt(c));

        if (solutionChar.equals(numStr)) {
            userGrid[r] = userGrid[r].substring(0, c) + numStr + userGrid[r].substring(c + 1);
            return true;
        } else {
            this.errors++;
            return false;
        }
    }

    @Override
    public boolean isCellFixed(int r, int c) {
        return puzzle[r].charAt(c) != '-';
    }

    @Override
    public boolean isGameWon() {
        for (int i = 0; i < 9; i++) {
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
        List<String[]> puzzles = new ArrayList<>();

        try (InputStream is = getClass().getClassLoader().getResourceAsStream("puzzles.txt")) {

            if (is == null) {
                System.err.println("RISORSA 'puzzles.txt' NON TROVATA! Uso il puzzle di fallback.");
                loadFallbackPuzzle();
                return;
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.trim().isEmpty()) continue;

                    String[] puzzleLines = line.split(",");
                    String[] solutionLines = br.readLine().split(",");
                    puzzles.add(new String[]{String.join(",", puzzleLines), String.join(",", solutionLines)});
                }
            }

        } catch (IOException e) {
            System.err.println("ERRORE DI LETTURA 'puzzles.txt'! Uso il puzzle di fallback.");
            e.printStackTrace();
            loadFallbackPuzzle();
            return;
        }

        if (puzzles.isEmpty()) {
            System.err.println("'puzzles.txt' è vuoto! Uso il puzzle di fallback.");
            loadFallbackPuzzle();
            return;
        }

        System.out.println("Caricamento da 'puzzles.txt' riuscito.");
        Random rand = new Random();
        int index = rand.nextInt(puzzles.size());
        puzzle = puzzles.get(index)[0].split(",");
        solution = puzzles.get(index)[1].split(",");
    }

    /**
     * Carica un puzzle/soluzione predefiniti SOLO SE
     * il caricamento da file fallisce.
     */
    private void loadFallbackPuzzle() {
        puzzle = new String[]{
                "--74916-5", "2---6-3-9", "-----7-1-",
                "-586----4", "--3----9-", "--62--187",
                "9-4-7---2", "67-83----", "81--45---"
        };
        solution = new String[]{
                "387491625", "241568379", "569327418",
                "758619234", "123784596", "496253187",
                "934176852", "675832941", "812945763"
        };
    }
}