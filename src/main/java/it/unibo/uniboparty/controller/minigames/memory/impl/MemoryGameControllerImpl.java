package it.unibo.uniboparty.controller.minigames.memory.impl;

import java.util.List;

import javax.swing.Timer;

import it.unibo.uniboparty.controller.minigames.memory.api.MemoryGameController;
import it.unibo.uniboparty.model.minigames.memory.api.Card;
import it.unibo.uniboparty.model.minigames.memory.api.MemoryDeckFactory;
import it.unibo.uniboparty.model.minigames.memory.api.MemoryGameModel;
import it.unibo.uniboparty.model.minigames.memory.api.MemoryGameReadOnlyState;
import it.unibo.uniboparty.model.minigames.memory.impl.MemoryDeckFactoryImpl;
import it.unibo.uniboparty.model.minigames.memory.impl.MemoryGameImpl;
import it.unibo.uniboparty.view.minigames.memory.api.MemoryGameView;

/**
 * Implementation of the Memory Game controller.
 * It connects the View with the Model and handles user input.
 */
public class MemoryGameControllerImpl implements MemoryGameController {

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
     * Timer that updates the time and the info panel evry second.
     */
    private final Timer timer;

    /**
     * Creates the controller, initializes the model and connects it to the view.
     * 
     * @param view a concrete implementation of {@link MemoryGameView}
     */
    public MemoryGameControllerImpl(final MemoryGameView view) {
        // Create a shuffled deck (8 pairs for a 4x4 grid)
        final int numberOfPairs = (ROWS * COLS) / 2;
        final MemoryDeckFactory factory = new MemoryDeckFactoryImpl();
        final List<Card> deck = factory.createShuffledDeck(numberOfPairs);

        // Create the model
        this.game = new MemoryGameImpl(deck);

        // Store the view and connect controller to the view
        this.view = view;
        this.view.setController(this);

        // First render of the UI
        final MemoryGameReadOnlyState initialState = this.game.getGameState();
        this.view.render(initialState);

        // Initialize timer and info panel (time + moves)
        this.secondsPassed = 0;
        this.view.updateInfoPanel(this.secondsPassed, initialState.getMoves());

        // TSwing timer that fires every second to update the info panel
        this.timer = new Timer(TIME_TICK_MILLIS, e -> {
            this.secondsPassed++;
            final MemoryGameReadOnlyState current = this.game.getGameState();
            this.view.updateInfoPanel(this.secondsPassed, current.getMoves());
        });
        this.timer.start();
    }

    /**
     * Called by the view when the user clicks on a card in the grid.
     * 
     * @param r row of the clicked card
     * @param c column of the clicked card
     */
    @Override
    public void onCardClicked(final int r, final int c) {
        // Convert 2D position (row, col) into 1D index in the deck
        final int index = r * COLS + c;

        // Try to flip the card in the model.
        // If flipCard() return false, the move is ignored
       final boolean accepted = this.game.flipCard(index);

        // State immediately after the flip attempt
        final MemoryGameReadOnlyState stateAfterFlip = this.game.getGameState();

        // Update the view to show what happened
        this.view.render(stateAfterFlip);
        this.view.updateInfoPanel(this.secondsPassed, stateAfterFlip.getMoves());

        // If the move was not accepted, nothing more to do
        if (!accepted) {
            return;
        }

        // Decide if the turn is complete.
        // waitingSecondFlip == true -> only the first card of the turn has been flipped
        // waitingSecondFlip == false -> the turn is complete (2 cards flipped)
        final boolean closedTurn = !stateAfterFlip.isWaitingSecondFlip();

        // Case A: the turn is NOT closed yet (only the first card flipped)
        // => the user can still click the second card
        if (!closedTurn) {
            return;
        }

        // Case B: the turn is closed (2 cards flipped)
        // Check if there is a mismatch to handle or if the game is over
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
     * It waits for a short delay, hides the cards in the model, and then updates the view.
     * </p>
     */
    private void handleMismatch() {
        // Disable all buttons while we wait to hide the cards
        this.view.setAllButtonsDisabled(true);

        // Single-shot Swing Timer that waits before hiding the mismatch
        final Timer mismatchTimer = new Timer(MISMATCH_DELAY_MILLIS, e -> {
            // Ask the model to hide the mismatching cards and end the turn
            this.game.resolveMismatch();

            final MemoryGameReadOnlyState afterHide = this.game.getGameState();
            this.view.render(afterHide);
            this.view.updateInfoPanel(this.secondsPassed, afterHide.getMoves());

            // If the game ended after this move, stop everything
            if (afterHide.isGameOver()) {
                endGame();
            } else {
                // Otherwise, re-enable the buttons to continue playing
                this.view.setAllButtonsDisabled(false);
            }

            ((Timer) e.getSource()).stop();
        });
        mismatchTimer.setRepeats(false);
        mismatchTimer.start();
    }

    /**
     * Called when the game is over.
     * Stops the timer and disables all buttons in the view.
     */
    private void endGame() {
        if (this.timer != null) {
            this.timer.stop();
        }
        this.view.setAllButtonsDisabled(true);
    }
}
