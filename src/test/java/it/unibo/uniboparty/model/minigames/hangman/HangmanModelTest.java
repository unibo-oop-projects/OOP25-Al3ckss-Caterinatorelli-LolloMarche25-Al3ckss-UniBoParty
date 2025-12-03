package it.unibo.uniboparty.model.minigames.hangman;

import it.unibo.uniboparty.model.minigames.hangman.api.HangmanModel;
import it.unibo.uniboparty.model.minigames.hangman.impl.HangmanModelImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Locale;

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
        final String masked = model.getMaskedWord();
        assertTrue(masked.contains("_"));
    }

    @Test
    void testIndovinaLetterGiusta() {
        final String secret = model.getSecretWord();
        final char correctChar = secret.charAt(0); // Prendiamo la prima lettera vera

        final boolean result = model.guessLetter(correctChar);

        assertTrue(result, "Indovinare una lettera presente deve ritornare true");
        assertEquals(0, model.getErrorCount(), "Gli errori non devono aumentare");
        assertTrue(model.getMaskedWord().contains(String.valueOf(correctChar).toUpperCase(Locale.ROOT)));
    }

    @Test
    void testIndovinaLetteraSbagliata() {
        // CANCELLA QUESTA RIGA: String secret = model.getSecretWord();

        // Troviamo un carattere che SICURAMENTE non c'è nella parola
        final char wrongChar = '0'; // Usiamo un numero, le parole sono solo lettere

        final boolean result = model.guessLetter(wrongChar);

        assertFalse(result, "Indovinare una lettera assente deve ritornare false");
        assertEquals(1, model.getErrorCount(), "Gli errori devono aumentare di 1");
    }

    @Test
    void testVittoriaIndovinandoParola() {
        final String secret = model.getSecretWord();

        // Indoviniamo subito la parola intera
        final boolean result = model.guessWord(secret);

        assertTrue(result);
        assertTrue(model.isGameWon());
        assertFalse(model.isGameOver());
    }

    @Test
    void testSconfittaDopoMaxErrori() {
        final int maxErrors = model.getMaxErrors();

        // Assicuriamoci che non siamo già in game over
        assertFalse(model.isGameOver());

        // Sbagliamo apposta N volte
        for (int i = 0; i < maxErrors; i++) {
            // Usiamo un carattere che sicuramente non è nella parola (es. cifre o simboli)
            // Usiamo un carattere diverso a ogni giro per evitare logiche di "lettera già detta"
            final char wrongChar = (char) ('0' + i);
            model.guessLetter(wrongChar);
        }

        assertEquals(maxErrors, model.getErrorCount(), "Il conteggio errori deve essere al massimo");
        assertTrue(model.isGameOver(), "Il gioco deve essere finito (Game Over)");
        assertFalse(model.isGameWon(), "Non puoi aver vinto sbagliando tutto");
    }
}
