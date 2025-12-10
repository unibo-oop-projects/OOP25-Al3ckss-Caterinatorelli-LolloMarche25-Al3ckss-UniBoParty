package it.unibo.uniboparty.application;

import it.unibo.uniboparty.view.minigames.dinosaurgame.impl.DinoGameIntroFrame;
import it.unibo.uniboparty.view.minigames.hangman.impl.HangManIntroFrame;
import it.unibo.uniboparty.view.minigames.mazegame.impl.MazeIntroFrame;
import it.unibo.uniboparty.view.minigames.memory.impl.MemoryIntroFrame;
import it.unibo.uniboparty.view.minigames.sudoku.impl.SudokuIntroFrame;
import it.unibo.uniboparty.view.minigames.tetris.impl.TetrisIntroFrame;
import it.unibo.uniboparty.view.minigames.typeracergame.impl.TyperacerGameIntroFrame;
import it.unibo.uniboparty.view.minigames.whacamole.impl.WhacAMoleIntroFrame;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 * Classe principale (Main) che permette di scegliere quale gioco avviare.
 */
public final class GameLauncher {

    private static final String UTILITY = "Utility class";
    private static final Logger LOGGER = Logger.getLogger(GameLauncher.class.getName());
    private static final int SUDOKU_CHOICE = 0;
    private static final int HANGMAN_CHOICE = 1;
    private static final int WHACAMOLE_CHOICE = 2;
    private static final int TETRIS_CHOICE = 3;
    private static final int MAZE_CHOICE = 4;
    private static final int TYPERACER_CHOICE = 5;
    private static final int DINORUN_CHOICE = 6;
    private static final int MEMORY_CHOICE = 7;

    private GameLauncher() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Game Launcher startup.
     *
     * @param args arguments from command line.
     */
    public static void main(final String[] args) {
        SwingUtilities.invokeLater(() -> {
            final String[] options = {"Sudoku", "Hangman", "WhacaMole", "Tetris", "Maze Game", "TypeRacer", "Dino Run","Memory"};
            final int choice = JOptionPane.showOptionDialog(
                    null,
                    "Which game would you like to play?",
                    "Game Choice",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    0
            );

            switch (choice) {
                case SUDOKU_CHOICE:
                    new SudokuIntroFrame();
                    break;
                case HANGMAN_CHOICE:
                    new HangManIntroFrame();
                    break;
                case WHACAMOLE_CHOICE:
                    new WhacAMoleIntroFrame();
                    break;
                case TETRIS_CHOICE:
                    new TetrisIntroFrame();
                    break;
                case MAZE_CHOICE:
                    new MazeIntroFrame();
                    break;
                case TYPERACER_CHOICE:
                    new TyperacerGameIntroFrame();
                    break;
                case DINORUN_CHOICE:
                    new DinoGameIntroFrame();
                    break;
                case MEMORY_CHOICE:
                    new MemoryIntroFrame();
                    break;
                default:
                    LOGGER.log(Level.INFO, "Application closed by user.");
                    System.exit(0);
            }
        });
    }


}
