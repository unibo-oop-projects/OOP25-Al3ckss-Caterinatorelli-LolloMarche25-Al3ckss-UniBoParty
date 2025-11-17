package unibo.party.minigames.memory.controller.api;

import unibo.party.minigames.memory.view.api.MemoryGameView;

/**
 * API del controller del Memory.
 * La View chiama i metodi di questa interfaccia.
 */
public interface MemoryGameController {
    
    /**
     * Gestisce il click su una carta alla posizione (r, c) nella griglia.
     */
    void onCardClicked(int r, int c);

    /**
     * @return la View associata, da inserire nella Scene dell'app.
     */
    MemoryGameView getView();
}
