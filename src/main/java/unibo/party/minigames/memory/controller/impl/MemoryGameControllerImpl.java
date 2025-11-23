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
 * Implementation of the Memory Game controller.
 * It connects the View with the Model and handles user input.
 */
public class MemoryGameControllerImpl implements MemoryGameController {

    private static final int ROWS = 4;
    private static final int COLS = 4;
    private static final double MISMATCH_DELAY_SECONDS = 1.0;

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
    private Timeline timer;

    /**
     * Creates the controller, initializes the model and connects the view
     * @param view a concrete implementation of MemoryGameView
     */
    public MemoryGameControllerImpl(final MemoryGameView view) {
        // Create a shuffled deck (8 pairs for a 4x4 grid)
        final int numberOfPairs = (ROWS * COLS) / 2;
        final MemoryDeckFactory factory = new MemoryDeckFactoryImpl();
        final List<Card> deck = factory.createShuffledDeck(numberOfPairs);

        // Create the model
        this.game = new MemoryGameImpl(deck);

        // Create the view and connect it to this controller
        this.view = view;
        this.view.setController(this);

        // First render of the UI
        final MemoryGameReadOnlyState initialState = this.game.getGameState();
        this.view.render(initialState);

        // Initialize timer and info panel (time + moves)
        this.secondsPassed = 0;
        this.view.updateInfoPanel(this.secondsPassed, initialState.getMoves());

        // Timeline that fires every second to update the info panel
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

        // Decide if the turn i complete.
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
     * It waits for a short delay, hides the cards in the model, and updates the view.
     */
    private void handleMismatch() {
        // Disable all buttons while we wait to hide the cards
        this.view.setAllButtonsDisabled(true);

        final PauseTransition pause = new PauseTransition(Duration.seconds(MISMATCH_DELAY_SECONDS));
        pause.setOnFinished(ev -> {
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
        });
        pause.play();
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

    /**
     * Used by the application to get the view to put into the Scene.
     * @return the view managed by this controller
     */
    @Override
    public MemoryGameView getView() {
        return this.view;
    }
}
