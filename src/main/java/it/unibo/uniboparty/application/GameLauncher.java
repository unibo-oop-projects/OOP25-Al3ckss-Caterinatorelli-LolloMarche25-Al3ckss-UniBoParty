package it.unibo.uniboparty.application;

import it.unibo.uniboparty.controller.minigames.hangman.impl.HangmanControllerImpl;
import it.unibo.uniboparty.controller.minigames.sudoku.impl.SudokuControllerImpl;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 * Classe principale (Main) che permette di scegliere quale gioco avviare.
 */
public final class GameLauncher {

    private static final Logger LOGGER = Logger.getLogger(GameLauncher.class.getName());

    private GameLauncher() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Metodo main - punto di avvio dell'applicazione.
     *
     * @param args argomenti da riga di comando.
     */
    public static void main(final String[] args) {
        SwingUtilities.invokeLater(() -> {
            final String[] options = {"Sudoku", "Impiccato"};
            final int choice = JOptionPane.showOptionDialog(
                    null,
                    "Quale gioco vuoi avviare?",
                    "Scelta Gioco",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            switch (choice) {
                case 0:
                    SudokuLauncher.startSudoku();
                    break;
                case 1:
                    HangmanLauncher.startHangman();
                    break;
                default:
                    LOGGER.log(Level.INFO, "Application closed by user.");
                    System.exit(0);
            }
        });
    }

    // --- CLASSI INTERNE (Spostate qui dentro per correggere l'errore) ---

    /**
     * Helper per avviare il Sudoku.
     */
    public static final class SudokuLauncher {

        private SudokuLauncher() {
            throw new UnsupportedOperationException("Utility class");
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
            throw new UnsupportedOperationException("Utility class");
        }

        /**
         * Avvia l'Impiccato.
         */
        public static void startHangman() {
            // Corretto: Usa l'interfaccia (IHangman...) come tipo
            SwingUtilities.invokeLater(HangmanControllerImpl::new);
        }
    }
}
