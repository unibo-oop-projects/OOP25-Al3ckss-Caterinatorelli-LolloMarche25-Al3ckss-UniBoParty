package it.unibo.uniboparty.application;

import it.unibo.uniboparty.controller.minigames.sudoku.impl.SudokuControllerImpl;

import javax.swing.SwingUtilities;

/**
 * Classe principale (Main) dell'applicazione.
 * Ha il compito di "assemblare" i componenti MVC:
 * 1. Crea le implementazioni concrete (Model, View).
 * 2. Le "inietta" nel costruttore del Controller (usando le loro interfacce).
 */
public final class MainApp {

    private MainApp() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Metodo main - punto di avvio dell'applicazione.
     *
     *  @param args argomenti da riga di comando (non usati).
     */
    public static void main(final String[] args) {

        // È FONDAMENTALE avviare le applicazioni Swing
        // in questo thread speciale (Event Dispatch Thread)
        // 1. Crea le implementazioni concrete (le "cucine")
        // 2. Crea il Controller e gli passa le interfacce (i "menu")
        // Il Controller non saprà mai che sta parlando
        // con un 'SudokuModelImpl', ma solo con un 'ISudokuModel'.
        SwingUtilities.invokeLater(SudokuControllerImpl::new);
    }
}
