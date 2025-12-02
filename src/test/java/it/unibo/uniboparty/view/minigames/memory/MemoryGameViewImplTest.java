package it.unibo.uniboparty.view.minigames.memory;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.List;

import org.junit.jupiter.api.Test;

import it.unibo.uniboparty.controller.minigames.memory.api.MemoryGameController;
import it.unibo.uniboparty.model.minigames.memory.api.Card;
import it.unibo.uniboparty.model.minigames.memory.api.Symbol;
import it.unibo.uniboparty.model.minigames.memory.impl.CardImpl;
import it.unibo.uniboparty.model.minigames.memory.impl.MemoryGameImpl;
import it.unibo.uniboparty.view.minigames.memory.impl.MemoryGameViewImpl;

class MemoryGameViewImplTest {

    @Test
    void testViewCanBeCreatedAndControllerSet() {
        final MemoryGameViewImpl view = new MemoryGameViewImpl();

        final MemoryGameController fakeController = (r, c) -> {
            // no-op controller for testing
        };

        assertDoesNotThrow(() -> view.setController(fakeController));
    }

    @Test
    void testRenderWithValidStateDoesNotThrow() {
        // Create a simple deterministic 4x4 deck (8 pairs)
        final List<Card> deck = List.of(
            new CardImpl(0, Symbol.STAR),
            new CardImpl(1, Symbol.STAR),
            new CardImpl(2, Symbol.HEART),
            new CardImpl(3, Symbol.HEART),
            new CardImpl(4, Symbol.CAT),
            new CardImpl(5, Symbol.CAT),
            new CardImpl(6, Symbol.APPLE),
            new CardImpl(7, Symbol.APPLE),
            new CardImpl(8, Symbol.MOON),
            new CardImpl(9, Symbol.MOON),
            new CardImpl(10, Symbol.SUN),
            new CardImpl(11, Symbol.SUN),
            new CardImpl(12, Symbol.CAR),
            new CardImpl(13, Symbol.CAR),
            new CardImpl(14, Symbol.DOG),
            new CardImpl(15, Symbol.DOG)
        );

        final MemoryGameImpl game = new MemoryGameImpl(deck);
        final MemoryGameViewImpl view = new MemoryGameViewImpl();

        assertDoesNotThrow(() -> view.render(game.getGameState()));
    }

    @Test
    void testUpdateInfoPanelAndDisableButtonsDoNotThrow() {
        final MemoryGameViewImpl view = new MemoryGameViewImpl();

        assertDoesNotThrow(() -> view.updateInfoPanel(10, 3));
        assertDoesNotThrow(() -> view.setAllButtonsDisabled(true));
        assertDoesNotThrow(() -> view.setAllButtonsDisabled(false));
    }
}
