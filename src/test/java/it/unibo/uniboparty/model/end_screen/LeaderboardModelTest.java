package it.unibo.uniboparty.model.end_screen;

import it.unibo.uniboparty.model.end_screen.api.LeaderboardModel;
import it.unibo.uniboparty.model.end_screen.api.Player;
import it.unibo.uniboparty.model.end_screen.impl.LeaderboardModelImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test unitari per il LeaderboardModel.
 */
class LeaderboardModelTest {

    private static final int WINNING_PLAYER = 200;
    private LeaderboardModel model;

    @BeforeEach
    void setUp() {
        model = new LeaderboardModelImpl();
    }

    @Test
    void testListaNonVuota() {
        final List<Player> topPlayers = model.getTopPlayers();
        assertNotNull(topPlayers, "La lista dei giocatori non deve essere null");
        assertFalse(topPlayers.isEmpty(), "La lista dei giocatori non deve essere vuota");
    }

    @Test
    void testNumeroMassimoGiocatori() {
        final List<Player> topPlayers = model.getTopPlayers();
        // Nel nostro mock abbiamo messo 5 giocatori, ma il metodo ne restituisce solo 3
        assertEquals(3, topPlayers.size(), "La classifica deve mostrare solo i primi 3 giocatori");
    }

    @Test
    void testOrdinamentoPunteggio() {
        final List<Player> topPlayers = model.getTopPlayers();

        // Controlliamo che il primo abbia un punteggio maggiore o uguale al secondo
        // e il secondo maggiore o uguale al terzo.
        if (topPlayers.size() >= 2) {
            assertTrue(topPlayers.get(0).getScore() >= topPlayers.get(1).getScore(),
                    "Il 1° classificato deve avere un punteggio >= del 2°");
        }

        if (topPlayers.size() >= 3) {
            assertTrue(topPlayers.get(1).getScore() >= topPlayers.get(2).getScore(),
                    "Il 2° classificato deve avere un punteggio >= del 3°");
        }
    }

    @Test
    void testContenutoDati() {
        final List<Player> topPlayers = model.getTopPlayers();
        final Player primo = topPlayers.get(0);

        // Sappiamo dai dati mock che "Peach" ha 200 punti ed è prima
        assertEquals("Gaia", primo.getName(), "Il vincitore atteso è Gaia");
        assertEquals(WINNING_PLAYER, primo.getScore(), "Il punteggio del vincitore deve essere 200");
    }
}
