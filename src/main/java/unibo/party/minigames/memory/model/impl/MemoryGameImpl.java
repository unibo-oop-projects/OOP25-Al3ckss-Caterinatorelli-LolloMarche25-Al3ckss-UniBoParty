package unibo.party.minigames.memory.model.impl;

import java.util.List;

import unibo.party.minigames.memory.model.api.Card;
import unibo.party.minigames.memory.model.api.MemoryGameModel;
import unibo.party.minigames.memory.model.api.MemoryGameReadOnlyState;

/**
 * Implementazione concreta del modello logico del Memory. 
 */
public class MemoryGameImpl implements MemoryGameModel {
    
    private final List<Card> cards;   
    private final int totalPairs;      
    private int matchedPairs;           

    private Card firstSelectedCard;   
    private Card secondSelectedCard;  
    private boolean mismatchPending;  

    private String lastMessage;   
    private int moves;

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

        // se c'è un mismatch ancora aperto, non accetto altri click
        if(this.mismatchPending) {
            this.lastMessage = "Are you in a hurry?";
            return false;
        }

        if(!isValidIndex(index)) {
            this.lastMessage = "You are not supposed to be here!";
            return false;
        }

        final Card selected = this.cards.get(index);
        if(selected.isRevealed()) {
            this.lastMessage = "This card is already revealed. U blind?";
            return false;
        }

        // Rivela la scelta
        selected.reveal();

        // Caso 1: è la prima carta del turno
        if(this.firstSelectedCard == null) {
            this.firstSelectedCard = selected;
            this.secondSelectedCard = null;
            this.lastMessage = "Oh nice. Now try to find its twin.";
            return true;
        }

        // Seconda carta del turno
        this.secondSelectedCard = selected;

        // ogni volta che giri la SECONDA carta, conta come una mossa
        this.moves++;         
        
        if(checkForMatch(this.firstSelectedCard, this.secondSelectedCard)) {
            this.matchedPairs++;

            if(this.isGameOver()) {
                this.lastMessage = "Congratulation! You win in " + this.moves + " moves!";
            } else {
                this.lastMessage = "It's a match!";
            }

            // turno finito, nessun mismatch
            endTurn();
        } else {
            // NO MATCH:
            // Non le chiudo subito
            this.mismatchPending = true;
            this.lastMessage = "No match. Memorize them!";
            // NOTA: non chiudiamo qui
            // non chiamiamo ancora endTurn()
        }
        
        return true;
     }

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

     private boolean checkForMatch(final Card a, final Card b) {
        return a.getSymbol() == b.getSymbol(); // non va bene usare equals() qui perché Symbol è un enum
     }

     /**
      * Termina il turno corrente, azzerando la carta selezionata in memeoria.
      * Dopo questa chiamata il prossimo click sarà considerato come "prima carta".
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
      * Controlla se l'indice passato punta a una carta del mazzo.
      */
     private boolean isValidIndex(final int index) {
        return index >= 0 && index < this.cards.size();
     }
}
