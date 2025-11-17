package unibo.party.minigames.memory.controller.impl;

import java.util.List;

import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

import unibo.party.minigames.memory.controller.api.MemoryGameController;
import unibo.party.minigames.memory.model.api.Card;
import unibo.party.minigames.memory.model.api.MemoryDeckFactory;
import unibo.party.minigames.memory.model.api.MemoryGameModel;
import unibo.party.minigames.memory.model.api.MemoryGameReadOnlyState;
import unibo.party.minigames.memory.model.impl.MemoryDeckFactoryImpl;
import unibo.party.minigames.memory.model.impl.MemoryGameImpl;
import unibo.party.minigames.memory.view.api.MemoryGameView;

/**
 * Implementazione JavaFX del controller del memory.
 * Gestisce l'interazione tra View e Model.
 */
public class MemoryGameControllerImpl implements MemoryGameController {

    private static final int ROWS = 4;
    private static final int COLS = 4;
    private static final double MISMATCH_DELAY_SECONDS = 1.0;


    private final MemoryGameModel game;
    private final MemoryGameView view;

    private int secondsPassed;
    private Timeline timer;

    /**
     * Crea Controller, Model e View.
     * @param view una implementazione concreta di MemoryGameView.
     */
    public MemoryGameControllerImpl(final MemoryGameView view) {
        // Crea il mazzo mischiato (8 coppie per 4x4)
        final int numberOfPairs = (ROWS * COLS) / 2;
        final MemoryDeckFactory factory = new MemoryDeckFactoryImpl();
        final List<Card> deck = factory.createShuffledDeck(numberOfPairs);

        // Crea il model
        this.game = new MemoryGameImpl(deck);

        // Crea la view
        this.view = view;
        this.view.setController(this);

        // Primo render
        final MemoryGameReadOnlyState initialState = this.game.getGameState();
        this.view.render(initialState);

        // Inizializza timer e pannello info
        this.secondsPassed = 0;
        this.view.updateInfoPanel(this.secondsPassed, initialState.getMoves());

        this.timer = new Timeline(
            new KeyFrame(Duration.seconds(1), e -> {
                this.secondsPassed++;
                final MemoryGameReadOnlyState current = this.game.getGameState();
                this.view.updateInfoPanel(this.secondsPassed, current.getMoves());
            })
        );
        this.timer.setCycleCount(Timeline.INDEFINITE);
        this.timer.play();
    }

    /**
     * Questo verrà chiamato dalla view quando l'utente clicca una carta
     */
    @Override
    public void onCardClicked(final int r, final int c) {
        final int index = r * COLS + c;

        // Prova a girare la carta nel model.
        // Se flipCard() ritorna false, è una mossa ignorata (tipo "Wait...")
        boolean accepted = this.game.flipCard(index);

        // Stato subito dopo il tentativo
        final MemoryGameReadOnlyState stateAfterFlip = this.game.getGameState();

        // Aggiorna subito la view per mostrare cosa è successo
        this.view.render(stateAfterFlip);
        this.view.updateInfoPanel(this.secondsPassed, stateAfterFlip.getMoves());

        // Se la mossa non è stata accettata (es. era "Wait.."), non fare altro.
        if (!accepted) {
            return;
        }

        // Decidiamo se il turno è completo.
        // waitingSecondFlip == true -> abbiamo scoperto SOLO la prima carta del turno
        // waitingFirstFlip == false -> abbiamo già scelto anche la seconda carta
        final boolean closedTurn = !stateAfterFlip.isWaitingSecondFlip();

        // Caso A: il turno NON è ancora chiuso (cioè ho appena rivelato la prima carta)
        // => niente da fare, l'utente può ancora cliccare la seconda carta.
        if (!closedTurn) {
            return;
        }

        // Caso B: il turno è chiuso.
        // Potrebbe essere:
        // - match (hasMismatchPending() == false)
        // - non match (hasMismatchPending() == true)
        if (this.game.hasMismatchPending()) {
            handleMismatch();
        } else if (stateAfterFlip.isGameOver()) {
            endGame();
        }
    }

    private void handleMismatch() {
        this.view.setAllButtonsDisabled(true);

        final PauseTransition pause = new PauseTransition(Duration.seconds(MISMATCH_DELAY_SECONDS));
        pause.setOnFinished(ev -> {
            this.game.resolveMismatch();

            final MemoryGameReadOnlyState afterHide = this.game.getGameState();
            this.view.render(afterHide);
            this.view.updateInfoPanel(this.secondsPassed, afterHide.getMoves());

            if (afterHide.isGameOver()) {
                endGame();
            } else {
                this.view.setAllButtonsDisabled(false);
            }
        });
        pause.play();
    }

    private void endGame() {
        if (this.timer != null) {
            this.timer.stop();
        }
        this.view.setAllButtonsDisabled(true);
    }

    /**
     * Questo serve all'application per ottenere la view da mettere nella Scene.
     */
    @Override
    public MemoryGameView getView() {
        return this.view;
    }
}
