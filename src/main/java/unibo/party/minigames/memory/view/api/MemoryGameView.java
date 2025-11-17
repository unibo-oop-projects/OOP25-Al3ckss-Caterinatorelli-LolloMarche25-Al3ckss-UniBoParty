package unibo.party.minigames.memory.view.api;

import unibo.party.minigames.memory.controller.api.MemoryGameController;
import unibo.party.minigames.memory.model.api.MemoryGameReadOnlyState;

/**
 * API della View del memory
 */
public interface MemoryGameView {
    
    /** 
     * Associa il controller alla view. 
     */
    void setController(MemoryGameController controller);

    /**
     * Ridisegna la UI in base allo stato del gioco.
     * Questo è chiamato dal controller dopo ogni mossa.
     */
    void render(MemoryGameReadOnlyState state);

    /**
     * Aggiorna pannello info (timer/mosse).
     */
    void updateInfoPanel(int secondsElapsed, int moves);

    /**
     * Abilita/disabilita tutti i pulsanti/celle della griglia
     */
    void setAllButtonsDisabled(boolean disabled);
}
