package it.unibo.uniboparty.application;

import it.unibo.uniboparty.controller.dice.impl.DiceControllerImpl;
import it.unibo.uniboparty.controller.end_screen.impl.LeaderboardControllerImpl;
import it.unibo.uniboparty.controller.minigames.hangman.impl.HangmanControllerImpl;
import it.unibo.uniboparty.controller.minigames.mazegame.impl.MazeControllerImpl;
import it.unibo.uniboparty.controller.minigames.sudoku.impl.SudokuControllerImpl;
import it.unibo.uniboparty.controller.minigames.tetris.impl.TetrisControllerImpl;
import it.unibo.uniboparty.view.minigames.dinosaurgame.impl.DinoGameIntroFrame;
import it.unibo.uniboparty.view.minigames.typeracergame.impl.TyperacerGameIntroFrame;

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
    private static final int TYPERACER_CHOICE = 5;
    private static final int DINORUN_CHOICE = 6;

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
            final String[] options = {"Sudoku", "Hangman", "Leaderboard", "Tetris", "Maze Game", "TypeRacer", "Dino Run"};
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
                case 0:
                    SudokuLauncher.startSudoku();
                    break;
                case 1:
                    HangmanLauncher.startHangman();
                    break;
                case 2:
                    LeaderboardLauncher.startLeaderboard();
                    break;
                case 3:
                    new TetrisControllerImpl().startGame();
                    break;
                case 4:
                    new MazeControllerImpl().startNewGame();
                    break;
                case TYPERACER_CHOICE:
                    new TyperacerGameIntroFrame();
                    break;
                case DINORUN_CHOICE:
                    new DinoGameIntroFrame();
                    break;
                default:
                    LOGGER.log(Level.INFO, "Application closed by user.");
                    System.exit(0);
            }
        });
    }

    /**
     * Helper per avviare il Sudoku.
     */
    public static final class SudokuLauncher {

        private SudokuLauncher() {
            throw new UnsupportedOperationException(UTILITY);
        }

        /**
         * Avvia il Sudoku.
         */
        public static void startSudoku() {
            SwingUtilities.invokeLater(SudokuControllerImpl::new);
        }
    }

    /**
     * Helper per avviare l'Impiccato.
     */
    public static final class HangmanLauncher {

        private HangmanLauncher() {
            throw new UnsupportedOperationException(UTILITY);
        }

        /**
         * Avvia l'Impiccato.
         */
        public static void startHangman() {
            SwingUtilities.invokeLater(HangmanControllerImpl::new);
        }
    }

    /**
     * Helper per avviare la Classifica.
     */
    public static final class LeaderboardLauncher {

        private LeaderboardLauncher() {
            throw new UnsupportedOperationException(UTILITY);
        }

        /**
         * Helper to start the leaderboard.
         */
        public static void startLeaderboard() {
            SwingUtilities.invokeLater(LeaderboardControllerImpl::new);
        }
    }

    /**
     * Helper to open the dice interface.
     */
    public static final class DiceLauncher {
        private DiceLauncher() {
            throw new UnsupportedOperationException(UTILITY);
        }

        /**
         * Helper to throw the dice.
         */
        public static void startDice() {
            SwingUtilities.invokeLater(DiceControllerImpl::new);
        }
    }
}
