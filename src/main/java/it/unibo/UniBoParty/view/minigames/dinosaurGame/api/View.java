package it.unibo.UniBoParty.view.minigames.dinosaurGame.api;

import javax.swing.JPanel;

/**
 * Interfaccia che definisce le operazioni principali della vista del gioco.
 */
public interface View {

    /**
     * Ridisegna la vista aggiornata del gioco.
     */
    void repaint();

    /**
     * Restituisce il pannello principale dove vengono disegnati dinosauro e ostacoli.
     *
     * @return il JPanel principale della vista.
     */
    JPanel getPanel();
}
