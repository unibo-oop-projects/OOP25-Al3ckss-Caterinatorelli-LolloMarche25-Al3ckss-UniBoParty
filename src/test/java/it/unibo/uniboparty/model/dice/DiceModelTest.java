package it.unibo.uniboparty.model.dice;

import it.unibo.uniboparty.model.dice.api.DiceModel;
import it.unibo.uniboparty.model.dice.impl.DiceModelImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test unitari per il modello dei Dadi.
 */
class DiceModelTest {

    private static final int MIN_VAL = 1;
    private static final int MAX_VAL = 6;
    private static final int TEST_ITERATIONS = 100;

    private DiceModel model;

    @BeforeEach
    void setUp() {
        model = new DiceModelImpl();
    }

    @Test
    void testInizializzazione() {
        // Appena creato, il modello deve avere già dei valori validi
        final int d1 = model.getDie1();
        final int d2 = model.getDie2();

        assertTrue(d1 >= MIN_VAL && d1 <= MAX_VAL, "Il dado 1 deve essere tra 1 e 6");
        assertTrue(d2 >= MIN_VAL && d2 <= MAX_VAL, "Il dado 2 deve essere tra 1 e 6");
        assertEquals(d1 + d2, model.getTotal(), "Il totale deve essere la somma dei dadi");
    }

    @Test
    void testRollMultipli() {
        // Eseguiamo molti lanci per assicurarci che non escano mai numeri fuori range (es. 0 o 7)
        for (int i = 0; i < TEST_ITERATIONS; i++) {
            model.roll();

            final int d1 = model.getDie1();
            final int d2 = model.getDie2();
            final int total = model.getTotal();

            assertTrue(d1 >= MIN_VAL && d1 <= MAX_VAL, "Errore nel range dado 1 al lancio " + i);
            assertTrue(d2 >= MIN_VAL && d2 <= MAX_VAL, "Errore nel range dado 2 al lancio " + i);
            assertEquals(d1 + d2, total, "Errore nella somma al lancio " + i);
        }
    }
}
