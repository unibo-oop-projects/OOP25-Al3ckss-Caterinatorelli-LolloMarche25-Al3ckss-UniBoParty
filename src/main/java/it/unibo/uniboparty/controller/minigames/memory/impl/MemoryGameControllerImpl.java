package it.unibo.uniboparty.controller.minigames.memory.impl;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.JOptionPane;

import it.unibo.uniboparty.controller.minigames.memory.api.MemoryGameController;
import it.unibo.uniboparty.model.minigames.memory.api.Card;
import it.unibo.uniboparty.model.minigames.memory.api.MemoryDeckFactory;
import it.unibo.uniboparty.model.minigames.memory.api.MemoryGameModel;
import it.unibo.uniboparty.model.minigames.memory.api.MemoryGameReadOnlyState;
import it.unibo.uniboparty.model.minigames.memory.impl.MemoryDeckFactoryImpl;
import it.unibo.uniboparty.model.minigames.memory.impl.MemoryGameImpl;
import it.unibo.uniboparty.view.minigames.memory.api.MemoryGameView;
import it.unibo.uniboparty.view.minigames.memory.impl.MemoryGameViewImpl;

/**
 * Implementation of the Memory game controller.
 *
 * <p>
 * This class sits between model and view:
 * it receives user input from the view, updates the model, and then
 * asks the view to redraw itself using a read-only game state.
 * </p>
 */
public final class MemoryGameControllerImpl implements MemoryGameController {

    private static final int ROWS = 4;
    private static final int COLS = 4;

    /**
     * Delay (in milliseconds) before hiding mismatching cards.
     */
    private static final int MISMATCH_DELAY_MILLIS = 1_000;

    /**
     * Interval (in milliseconds) between two time updates.
     */
    private static final int TIME_TICK_MILLIS = 1_000;

    /**
     * Result codes for the game:
     * 2 = in progress, 1 = won, 0 = lost.
     */
    private static final int RESULT_LOST = 0;
    private static final int RESULT_WON = 1;
    private static final int RESULT_IN_PROGRESS = 2;

    /**
     * The game model that contains the core logic.
     */
    private final MemoryGameModel game;

    /**
     * The view that shows the game to the user.
     */
    private final MemoryGameView view;

    /**
     * Seconds passed since the game started.
     */
    private int secondsPassed;

    /**
     * Encoded game result: 2 = in progress, 1 = won, 0 = lost.
     */
    private int resultCode;

    /**
     * Swing timer that updates the time and the info panel every second.
     */
    private final Timer timer;

    /**
     * True when the end-of-game dialog has already been shown.
     */
    private boolean endDialogShown;

    /**
     * Private constructor: the controller creates both model and view by itself.
     *
     * <p>
     * This keeps the internal fields encapsulated and avoids exposing
     * mutable collaborators to the outside.
     * </p>
     */
    private MemoryGameControllerImpl() {
        final int numberOfPairs = ROWS * COLS / 2;
        final MemoryDeckFactory factory = new MemoryDeckFactoryImpl();
        final List<Card> deck = factory.createShuffledDeck(numberOfPairs);

        this.game = new MemoryGameImpl(deck);

        this.view = new MemoryGameViewImpl();
        this.view.setController(this);

        final MemoryGameReadOnlyState initialState = this.game.getGameState();
        this.view.render(initialState);

        this.secondsPassed = 0;
        this.resultCode = RESULT_IN_PROGRESS;
        this.endDialogShown = false;
        this.view.updateInfoPanel(this.secondsPassed, initialState.getMoves());

        this.timer = new Timer(TIME_TICK_MILLIS, e -> {
            this.secondsPassed++;
            final MemoryGameReadOnlyState current = this.game.getGameState();
            this.view.updateInfoPanel(this.secondsPassed, current.getMoves());
        });
        this.timer.start();
    }

    /**
     * Factory method used by the application to create a new controller instance.
     *
     * @return a new {@link MemoryGameControllerImpl}
     */
    public static MemoryGameControllerImpl create() {
        return new MemoryGameControllerImpl();
    }

    /**
     * Creates a Swing panel that contains the game view.
     *
     * <p>
     * The internal {@link MemoryGameView} is not returned directly:
     * instead, it is wrapped inside a simple container panel.
     * This keeps the view field encapsulated inside the controller
     * and avoids exposing internal mutable state.
     * </p>
     *
     * @return a new {@link JPanel} that can be used as the main content of a frame
     */
    public JPanel createMainPanel() {
        final JPanel container = new JPanel(new BorderLayout());
        container.add((JPanel) this.view, BorderLayout.CENTER);
        return container;
    }

    /**
     * Returns the current result code of the game.
     *
     * <p>
     * 2 = game in progress,
     * 1 = game won,
     * 0 = game lost.
     * </p>
     *
     * @return the result code
     */
    public int getResultCode() {
        return this.resultCode;
    }

    /**
     * Called by the view when the user clicks on a card in the grid.
     *
     * @param r row of the clicked card
     * @param c column of the clicked card
     */
    @Override
    public void onCardClicked(final int r, final int c) {
        final int index = r * COLS + c;

        final boolean accepted = this.game.flipCard(index);

        final MemoryGameReadOnlyState stateAfterFlip = this.game.getGameState();

        this.view.render(stateAfterFlip);
        this.view.updateInfoPanel(this.secondsPassed, stateAfterFlip.getMoves());

        if (!accepted) {
            return;
        }

        final boolean closedTurn = !stateAfterFlip.isWaitingSecondFlip();

        if (!closedTurn) {
            return;
        }

        if (this.game.hasMismatchPending()) {
            handleMismatch();
        } else if (stateAfterFlip.isGameOver()) {
            endGame();
        }
    }

    /**
     * Handles the case when the player revealed two different cards (mismatch).
     *
     * <p>
     * It waits for a short delay, hides the cards in the model,
     * and then updates the view.
     * </p>
     */
    private void handleMismatch() {
        this.view.setAllButtonsDisabled(true);

        final Timer mismatchTimer = new Timer(MISMATCH_DELAY_MILLIS, e -> {
            this.game.resolveMismatch();

            final MemoryGameReadOnlyState afterHide = this.game.getGameState();
            this.view.render(afterHide);
            this.view.updateInfoPanel(this.secondsPassed, afterHide.getMoves());

            if (afterHide.isGameOver()) {
                endGame();
            } else {
                this.view.setAllButtonsDisabled(false);
            }

            ((Timer) e.getSource()).stop();
        });
        mismatchTimer.setRepeats(false);
        mismatchTimer.start();
    }

    /**
     * Called when the game is over.
     * Stops the timer, disables all buttons and shows a dialog
     * with the final result.
     */
    private void endGame() {
        this.timer.stop();
        this.view.setAllButtonsDisabled(true);

        final MemoryGameReadOnlyState finalState = this.game.getGameState();
        if (finalState.getMatchedPairs() == finalState.getTotalPairs()) {
            this.resultCode = RESULT_WON;
        } else {
            this.resultCode = RESULT_LOST;
        }

        if (!this.endDialogShown) {
            this.endDialogShown = true;

            final String message;
            if (this.resultCode == RESULT_WON) {
                message = "You win!\nYou found all pairs in "
                        + finalState.getMoves() + " moves.";
            } else {
                message = "You lost.\nYou used "
                        + finalState.getMoves() + " moves.";
            }

            JOptionPane.showMessageDialog(
                (JPanel) this.view,
                message,
                "Memory - Game Over",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
    }
}
