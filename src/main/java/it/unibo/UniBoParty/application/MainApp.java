package it.unibo.UniBoParty.application;

// Importa le INTERFACCE (API)
import it.unibo.UniBoParty.model.minigames.sudoku.api.ISudokuModel;
import it.unibo.UniBoParty.view.minigames.sudoku.api.ISudokuView;

// Importa le IMPLEMENTAZIONI (IMPL)
import it.unibo.UniBoParty.model.minigames.sudoku.impl.SudokuModelImpl;
import it.unibo.UniBoParty.view.minigames.sudoku.impl.SudokuViewImpl;
import it.unibo.UniBoParty.controller.minigames.sudoku.impl.SudokuControllerImpl;

import javax.swing.SwingUtilities;

/**
 * Classe principale (Main) dell'applicazione.
 * Ha il compito di "assemblare" i componenti MVC:
 * 1. Crea le implementazioni concrete (Model, View).
 * 2. Le "inietta" nel costruttore del Controller (usando le loro interfacce).
 */
public class MainApp {

    /**
     * Metodo main - punto di avvio dell'applicazione.
     * @param args argomenti da riga di comando (non usati).
     */
    public static void main(String[] args) {

        // È FONDAMENTALE avviare le applicazioni Swing
        // in questo thread speciale (Event Dispatch Thread)
        SwingUtilities.invokeLater(() -> {

            // 1. Crea le implementazioni concrete (le "cucine")
            final ISudokuModel model = new SudokuModelImpl();
            final ISudokuView view = new SudokuViewImpl();

            // 2. Crea il Controller e gli passa le interfacce (i "menu")
            // Il Controller non saprà mai che sta parlando
            // con un 'SudokuModelImpl', ma solo con un 'ISudokuModel'.
            new SudokuControllerImpl(model, view);
        });
    }
}