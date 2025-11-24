package it.unibo.UniBoParty.model.minigames.hangman;

import it.unibo.UniBoParty.model.minigames.hangman.api.HangmanModel;
import it.unibo.UniBoParty.model.minigames.hangman.impl.HangmanModelImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HangmanModelTest {

    private HangmanModel model;

    @BeforeEach
    void setUp() {
        model = new HangmanModelImpl();
    }

    @Test
    void testInizializzazione() {
        assertNotNull(model.getSecretWord());
        assertFalse(model.getSecretWord().isEmpty());
        assertEquals(0, model.getErrorCount());
        assertFalse(model.isGameOver());
        assertFalse(model.isGameWon());

        // La parola mascherata deve contenere solo underscore e spazi all'inizio
        String masked = model.getMaskedWord();
        assertTrue(masked.contains("_"));
    }

    @Test
    void testIndovinaLetterGiusta() {
        String secret = model.getSecretWord();
        char correctChar = secret.charAt(0); // Prendiamo la prima lettera vera

        boolean result = model.guessLetter(correctChar);

        assertTrue(result, "Indovinare una lettera presente deve ritornare true");
        assertEquals(0, model.getErrorCount(), "Gli errori non devono aumentare");

        // Controlla che la maschera sia cambiata (ora mostra la lettera)
        assertTrue(model.getMaskedWord().contains(String.valueOf(correctChar).toUpperCase()));
    }

    @Test
    void testIndovinaLetteraSbagliata() {
        String secret = model.getSecretWord();

        // Troviamo un carattere che SICURAMENTE non c'è nella parola
        char wrongChar = '0'; // Usiamo un numero, le parole sono solo lettere

        boolean result = model.guessLetter(wrongChar);

        assertFalse(result, "Indovinare una lettera assente deve ritornare false");
        assertEquals(1, model.getErrorCount(), "Gli errori devono aumentare di 1");
    }

    @Test
    void testVittoriaIndovinandoParola() {
        String secret = model.getSecretWord();

        // Indoviniamo subito la parola intera
        boolean result = model.guessWord(secret);

        assertTrue(result);
        assertTrue(model.isGameWon());
        assertFalse(model.isGameOver());
    }

    @Test
    void testSconfittaDopoMaxErrori() {
        int maxErrors = model.getMaxErrors();

        // Sbagliamo apposta N volte
        for (int i = 0; i < maxErrors; i++) {
            model.guessLetter('0'); // Sbagliato
        }

        assertEquals(maxErrors, model.getErrorCount());
        assertTrue(model.isGameOver());
        assertFalse(model.isGameWon());
    }
}