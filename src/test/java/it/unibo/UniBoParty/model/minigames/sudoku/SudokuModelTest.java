package it.unibo.UniBoParty.model.minigames.sudoku;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import it.unibo.UniBoParty.model.minigames.sudoku.api.ISudokuModel;
import it.unibo.UniBoParty.model.minigames.sudoku.impl.SudokuModelImpl;

class SudokuModelTest {

    private ISudokuModel model;

    @BeforeEach
    void setUp() {
        // Prima di ogni test, crea un nuovo modello
        model = new SudokuModelImpl();
    }

    @Test
    void testInizializzazione() {
        // Verifica che il modello parta con 0 errori
        assertEquals(0, model.getErrorCount(), "Il gioco deve iniziare con 0 errori");

        // Verifica che la griglia contenga caratteri validi (non null)
        String cell = model.getInitialPuzzleAt(0, 0);
        assertNotNull(cell, "La cella non deve essere null");
        assertTrue(cell.length() == 1, "La cella deve contenere un solo carattere");
    }

    @Test
    void testLogicaErrori() {
        // Cerchiamo una cella giocabile (non fissa)
        int r = -1, c = -1;
        // Ciclo per trovare una cella libera ('-') nel puzzle originale
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!model.isCellFixed(i, j)) {
                    r = i;
                    c = j;
                    break;
                }
            }
            if (r != -1) break;
        }

        // Se abbiamo trovato una cella giocabile
        if (r != -1) {
            int erroriIniziali = model.getErrorCount();

            // Proviamo a inserire un numero (es. 1).
            // Non sappiamo se è giusto o sbagliato a priori perché è random,
            // ma verifichiamo la coerenza della risposta.
            boolean risultato = model.isMoveCorrect(r, c, 1);

            if (!risultato) {
                // Se era sbagliato, gli errori devono essere aumentati
                assertEquals(erroriIniziali + 1, model.getErrorCount());
            } else {
                // Se era giusto, gli errori non devono cambiare
                assertEquals(erroriIniziali, model.getErrorCount());
            }
        }
    }
}