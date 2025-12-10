package it.unibo.uniboparty.model.minigames.memory.impl;

import java.util.List;

import it.unibo.uniboparty.model.minigames.memory.api.Card;
import it.unibo.uniboparty.model.minigames.memory.api.MemoryGameModel;
import it.unibo.uniboparty.model.minigames.memory.api.MemoryGameReadOnlyState;

/**
 * Concrete implementation of the {@link MemoryGameModel} interface.
 * 
 * <p>
 * This class contains the core logic of the Memory game:
 * flipping cards, checking for matches, tracking the game state and exposing
 * a read-only snapshot for the UI.
 * </p> 
 */
public final class MemoryGameImpl implements MemoryGameModel {

    /**
     * Maximum number of moves allowed, expressed as a multiplier
     * of the total number of pairs.
     */
    private static final int MOVES_PER_PAIR_LIMIT = 3;

    /**
     * Message shown when the player exceeds the move limit.
     */
    private static final String MOVE_LIMIT_MESSAGE = "You lost: move limit (%d) exceeded.";

    /**
     * All the cards in the current game.
     */
    private final List<Card> cards; 

    /**
     * Total number of pairs in the game.
     */
    private final int totalPairs;

    /**
     * Maximum number of moves allowed before the game is lost.
     */
    private final int maxMoves;

    /**
     * Number of pairs matched so far.
     */
    private int matchedPairs;

    /**
     * First selected card in the current turn.
     */
    private Card firstSelectedCard;

    /**
     * Second selected card in the current turn.
     */
    private Card secondSelectedCard;

    /**
     * {@code true} when the player has just revealed two different cards (mismatch)
     * and they must be hidden again.
     */
    private boolean mismatchPending;

    /**
     * {@code true} when the player has exceeded the maximum number of moves.
     */
    private boolean gameLost;

    /**
     * Short feedback message for the player.
     */
    private String lastMessage;

    /**
     * Number of moves made by the player (each move = two flips).
     */
    private int moves;

    /**
     * Creates a new Memory game using the given deck.
     * All cards are assumed to be initially hidden at the beginning.
     * 
     * @param deck the list of cards to use in the game
     */
    public MemoryGameImpl(final List<Card> deck) {
        this.cards = List.copyOf(deck);
        this.totalPairs = deck.size() / 2;
        this.maxMoves = this.totalPairs * MOVES_PER_PAIR_LIMIT;
        this.matchedPairs = 0;
        this.firstSelectedCard = null;
        this.secondSelectedCard = null;
        this.mismatchPending = false;
        this.gameLost = false;
        this.lastMessage = "Game started! Select a card.";
        this.moves = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean flipCard(final int index) {

        // Do not accept new clicks if the game is already over
        if (this.isGameOver()) {
            this.lastMessage = "The game is already over.";
            return false;
        }

        // Do not accept new clicks if there is a mismatch still visible
        if (this.mismatchPending) {
            this.lastMessage = "Are you in a hurry?";
            return false;
        }

        // Check that the index is inside the deck bounds
        if (!isValidIndex(index)) {
            this.lastMessage = "You are not supposed to be here!";
            return false;
        }

        final Card selected = this.cards.get(index);

        // Ignore clicks on already revealed cards
        if (selected.isRevealed()) {
            this.lastMessage = "This card is already revealed. U blind?";
            return false;
        }

        // Reveal the chosen card
        selected.reveal();

        // First card of the turn
        if (this.firstSelectedCard == null) {
            this.firstSelectedCard = selected;
            this.secondSelectedCard = null;
            this.lastMessage = "Kudos! Now try to find its twin.";
            return true;
        }

        // Second card of the turn
        this.secondSelectedCard = selected;

        // Every time you flip the second card, it counts as a move
        this.moves++;

        if (checkForMatch(this.firstSelectedCard, this.secondSelectedCard)) {
            this.matchedPairs++;

            if (this.matchedPairs == this.totalPairs) {
                // All pairs found: victory, regardless of move limit
                this.lastMessage = "Congratulations! You win in " + this.moves + " moves!";
            } else if (this.moves > this.maxMoves) {
                // Move limit exceeded before finding all pairs: defeat
                this.gameLost = true;
                this.lastMessage = String.format(MOVE_LIMIT_MESSAGE, this.maxMoves);
            } else {
                this.lastMessage = "It's a match!";
            }

            // Turn is finished, no mismatch to resolve
            endTurn();
        } else {
            // NO MATCH:
            // Keep both cards visible and wait for resolveMismatch()
            this.mismatchPending = true;

            if (this.moves > this.maxMoves && this.matchedPairs < this.totalPairs) {
                this.gameLost = true;
                this.lastMessage = String.format(MOVE_LIMIT_MESSAGE, this.maxMoves);
            } else {
                this.lastMessage = "No match. Try to remember these cards.";
            }
        }

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resolveMismatch() {
        if (this.mismatchPending && this.firstSelectedCard != null && this.secondSelectedCard != null) {
            this.firstSelectedCard.hide();
            this.secondSelectedCard.hide();
        }
        endTurn();
        this.mismatchPending = false;

        if (this.gameLost) {
            this.lastMessage = String.format(MOVE_LIMIT_MESSAGE, this.maxMoves);
        } else if (this.isGameOver()) {
            this.lastMessage = "You found all pairs in " + this.moves + " moves!";
        } else {
            this.lastMessage = "Try again!";
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasMismatchPending() {
        return this.mismatchPending;
    }

    /**
     * Checks if the two given cards form a matching pair.
     * 
     * @param first first card
     * @param second second card
     * @return {@code true} if the two cards match, {@code false} otherwise
     */
    private boolean checkForMatch(final Card first, final Card second) {
        // We can use == here because Symbol is an enum
        return first.getSymbol() == second.getSymbol();
    }

    /**
     * Ends the current turn by clearing the selected cards.
     * After this call, the next click will be considered as "first card".
     */
    private void endTurn() {
        this.firstSelectedCard = null;
        this.secondSelectedCard = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isGameOver() {
        return this.matchedPairs == this.totalPairs || this.gameLost;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Card> getCards() {
        // Returns a defensive copy to avoid external modifications
        return List.copyOf(this.cards);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MemoryGameReadOnlyState getGameState() {
        return new MemoryGameState(
            this.matchedPairs,
            this.totalPairs,
            this.isGameOver(),
            this.firstSelectedCard != null && !this.mismatchPending,
            List.copyOf(this.cards),
            this.lastMessage,
            this.moves
        );
    }

    /**
     * Checks if the given index is valid for the current deck.
     * 
     * @param index the index to check
     * @return {@code true} if the index is inside [0, cards.size() - 1]
     */
    private boolean isValidIndex(final int index) {
        return index >= 0 && index < this.cards.size();
    }
}
