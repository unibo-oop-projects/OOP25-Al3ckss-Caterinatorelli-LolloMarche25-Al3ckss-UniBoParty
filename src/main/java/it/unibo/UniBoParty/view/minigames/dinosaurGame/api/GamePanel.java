package it.unibo.UniBoParty.view.minigames.dinosaurGame.api;

/**
 * Interfaccia per il pannello di gioco.
 * <p>
 * Definisce i metodi principali che una vista del gioco deve implementare.
 * Permette al controller di interagire con il pannello senza conoscere la classe concreta.
 */
public interface GamePanel {

    /**
     * Ridisegna la vista del gioco.
     * <p>
     * Questo metodo viene chiamato dal controller ogni volta
     * che lo stato del gioco viene aggiornato.
     */
    void repaint();
}
