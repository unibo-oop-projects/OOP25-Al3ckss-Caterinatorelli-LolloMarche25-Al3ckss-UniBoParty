package it.unibo.UniBoParty.view.minigames.dinosaurGame.impl;

import java.awt.Dimension;
import javax.swing.JFrame;

import it.unibo.UniBoParty.model.minigames.dinosaurGame.api.Model;
import it.unibo.UniBoParty.view.minigames.dinosaurGame.api.View;
/**
 * Implementazione concreta della vista del gioco.
 * <p>
 * Gestisce la finestra principale e il pannello di gioco.
 */
public class ViewImpl implements View {

    private GamePanelImpl panel1;
    /**
     * Crea una nuova finestra di gioco con il pannello principale.
     *
     * @param model il modello del gioco, necessario per disegnare dinosauro e ostacoli.
     */
    public ViewImpl(Model model){
        JFrame frame = new JFrame("Dino Game");
        panel1 = new GamePanelImpl(model);
        frame.setMinimumSize(new Dimension(600, 500));
        frame.add(panel1);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Rende il pannello principale pronto a ricevere input da tastiera
        panel1.setFocusable(true);
        panel1.requestFocusInWindow();
    }

    @Override
    public void repaint(){
        panel1.repaint();
    }

    @Override
    public javax.swing.JPanel getPanel(){
        return panel1;
    }
}
