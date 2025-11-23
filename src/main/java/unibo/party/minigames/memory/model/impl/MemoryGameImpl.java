package unibo.party.minigames.memory.model.impl;

import java.util.List;

import unibo.party.minigames.memory.model.api.Card;
import unibo.party.minigames.memory.model.api.MemoryGameModel;
import unibo.party.minigames.memory.model.api.MemoryGameReadOnlyState;

/**
 * Concrete implementation of the MemoryGameModel.
 * This class contains the core logic of the Memory game:
 * flipping cards, checking for matches, tracking game state, etc. 
 */
public class MemoryGameImpl implements MemoryGameModel {
    
    /**
     * All the cards in the current game.
     */
    private final List<Card> cards; 
    
    /**
     * Total number of pairs in the game.
     */
    private final int totalPairs;

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
     * True when the player has just revealed two different cards (mismatch)
     * and they must be hidden again.
     */
    private boolean mismatchPending;  

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
     * @param deck the list of cards to use in the game
     */
    public MemoryGameImpl(final List<Card> deck) {
        this.cards = deck;
        this.totalPairs = deck.size() / 2;
        this.matchedPairs = 0;
        this.firstSelectedCard = null;
        this.secondSelectedCard = null;
        this.mismatchPending = false;
        this.lastMessage = "Game started! Select a card.";
        this.moves = 0;
    }

    @Override
     public boolean flipCard(final int index) {

        // Do not accept new clicks if there is a mismatch still visible
        if(this.mismatchPending) {
            this.lastMessage = "Are you in a hurry?";
            return false;
        }

        // Check that the index is inside the deck bounds
        if(!isValidIndex(index)) {
            this.lastMessage = "You are not supposed to be here!";
            return false;
        }

        final Card selected = this.cards.get(index);
        
        /**
         * Ignore clicks on already revealed cards
         */
        if(selected.isRevealed()) {
            this.lastMessage = "This card is already revealed. U blind?";
            return false;
        }

        // Reveal the chosen card
        selected.reveal();

        // First card of the turn
        if(this.firstSelectedCard == null) {
            this.firstSelectedCard = selected;
            this.secondSelectedCard = null;
            this.lastMessage = "Oh nice. Now try to find its twin.";
            return true;
        }

        // Second card of the turn
        this.secondSelectedCard = selected;

        // Every time you flip the second card, it count as a move
        this.moves++;         
        
        if(checkForMatch(this.firstSelectedCard, this.secondSelectedCard)) {
            this.matchedPairs++;

            if(this.isGameOver()) {
                this.lastMessage = "Congratulation! You win in " + this.moves + " moves!";
            } else {
                this.lastMessage = "It's a match!";
            }

            // Turn is finished, no mismatch to resolve
            endTurn();
        } else {
            // NO MATCH:
            // Keep both cards visible and wait for resolveMismatch()
            this.mismatchPending = true;
            this.lastMessage = "No match. Memorize them!";
            // We do not close the turn here
            // endTurn() will be called later in resolveMismatch()
        }
        
        return true;
     }

     /**
      * Hides the two previously revealed cards if they do not match, 
      * ends the current turn and updates the feedback message.
      */
     @Override
     public void resolveMismatch() {
        if(this.mismatchPending && this.firstSelectedCard != null && this.secondSelectedCard != null) {
            this.firstSelectedCard.hide();
            this.secondSelectedCard.hide();
        }
        endTurn();
        this.mismatchPending = false;
        
        if(this.isGameOver()) {
            this.lastMessage = "You won in " + this.moves + " moves!";
        } else {
            this.lastMessage = "Try again!";
        }
     }

     @Override
     public boolean hasMismatchPending() {
        return this.mismatchPending;
     }

     /**
      * Checks if the two given cards form a matching pair.
      * @param a first card
      * @param b second card
      * @return true if the two cards match, false otherwise
      */
     private boolean checkForMatch(final Card a, final Card b) {
        // We can use == here because Symbol is an enum
        return a.getSymbol() == b.getSymbol();

     /**
      * Ends the current turn by clearing the selected cards.
      * After this call, the next click will be considered as "first card".
      */
     private void endTurn() {
        this.firstSelectedCard = null;
        this.secondSelectedCard = null;
     }

     @Override
     public boolean isGameOver() {
        return this.matchedPairs == this.totalPairs;
     }

     @Override
     public List<Card> getCards() {
        return List.copyOf(this.cards); // restituisco una copia per evitare modifiche esterne
     }

     /**
      * Returns a read-only snapshot of the current game state,
      * meant to be used by the GUI or the controller.
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
      * @param index the index to check
      * @return true if the index is inside [0, cards.size()-1]
      */
     private boolean isValidIndex(final int index) {
        return index >= 0 && index < this.cards.size();
     }
}
