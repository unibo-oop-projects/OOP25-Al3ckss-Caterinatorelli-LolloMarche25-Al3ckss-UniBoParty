package it.unibo.UniBoParty.application; // Puoi cambiare questo package a tuo piacimento

// --- Importazioni per Sudoku ---
import it.unibo.UniBoParty.model.minigames.sudoku.api.ISudokuModel;
import it.unibo.UniBoParty.view.minigames.sudoku.api.ISudokuView;
import it.unibo.UniBoParty.model.minigames.sudoku.impl.SudokuModelImpl;
import it.unibo.UniBoParty.view.minigames.sudoku.impl.SudokuViewImpl;
import it.unibo.UniBoParty.controller.minigames.sudoku.impl.SudokuControllerImpl;

// --- Importazioni per Impiccato ---
import it.unibo.UniBoParty.model.minigames.hangman.api.HangmanModel;
import it.unibo.UniBoParty.model.minigames.hangman.impl.HangmanModelImpl;
import it.unibo.UniBoParty.view.minigames.hangman.api.HangmanView;
import it.unibo.UniBoParty.view.minigames.hangman.impl.HangmanViewImpl;
import it.unibo.UniBoParty.controller.minigames.hangman.impl.HangmanControllerImpl;

// --- Importazioni generiche per Swing ---
import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;

/**
 * Classe principale (Main) che permette di scegliere quale gioco avviare.
 * Mostra un menu di scelta e avvia il gioco selezionato.
 * * NOTA: Questo file unisce le classi di avvio. Affinché questo codice
 * funzioni, TUTTE le classi importate (Model, View, Controller di entrambi
 * i giochi) devono essere presenti nel classpath durante la compilazione e
 * l'esecuzione.
 */
public class GameLauncher {

    /**
     * Metodo main - punto di avvio dell'applicazione.
     * Mostra una finestra di dialogo per scegliere il gioco.
     * @param args argomenti da riga di comando (non usati).
     */
    public static void main(String[] args) {

        // Avvia l'interfaccia di scelta nel thread degli eventi Swing
        SwingUtilities.invokeLater(() -> {
            String[] options = {"Sudoku", "Impiccato"};
            int choice = JOptionPane.showOptionDialog(
                    null, // Finestra genitore (null per centrato)
                    "Quale gioco vuoi avviare?", // Messaggio
                    "Scelta Gioco", // Titolo della finestra
                    JOptionPane.DEFAULT_OPTION, // Tipo di opzione
                    JOptionPane.QUESTION_MESSAGE, // Tipo di icona
                    null, // Icona personalizzata (null per default)
                    options, // Testo dei bottoni
                    options[0] // Bottone preselezionato
            );

            // Gestisce la scelta dell'utente
            switch (choice) {
                case 0:
                    // L'utente ha scelto "Sudoku"
                    SudokuLauncher.startSudoku();
                    break;
                case 1:
                    // L'utente ha scelto "Impiccato"
                    HangmanLauncher.startHangman();
                    break;
                default:
                    // L'utente ha chiuso la finestra (choice == -1)
                    System.out.println("Nessun gioco selezionato. Uscita.");
                    System.exit(0); // Termina l'applicazione
            }
        });
    }
}

/**
 * Classe (precedentemente MainApp) per avviare il Sudoku.
 * Resa non-public (rimuovendo 'public') per essere inclusa
 * nello stesso file di GameLauncher.
 */
class SudokuLauncher {

    /**
     * Metodo statico per avviare il gioco Sudoku.
     * Il main originale è stato rinominato in startSudoku.
     */
    public static void startSudoku() {

        // Avvia l'applicazione Sudoku nel thread degli eventi Swing
        SwingUtilities.invokeLater(() -> {

            // 1. Crea le implementazioni concrete
            final ISudokuModel model = new SudokuModelImpl();
            final ISudokuView view = new SudokuViewImpl();

            // 2. Crea il Controller e gli passa le interfacce
            new SudokuControllerImpl(model, view);
        });
    }
}

/**
 * Classe (precedentemente App) per avviare l'Impiccato.
 * Resa non-public (rimuovendo 'public') per essere inclusa
 * nello stesso file di GameLauncher.
 */
class HangmanLauncher {

    /**
     * Metodo statico per avviare il gioco Impiccato.
     * Il main originale è stato rinominato in startHangman.
     */
    // ... imports ...



    public static void startHangman() {
        javax.swing.SwingUtilities.invokeLater(() -> {
            HangmanModel model = new HangmanModelImpl();
            HangmanView view = new HangmanViewImpl();
            new HangmanControllerImpl(model, view);
        });
    }
}