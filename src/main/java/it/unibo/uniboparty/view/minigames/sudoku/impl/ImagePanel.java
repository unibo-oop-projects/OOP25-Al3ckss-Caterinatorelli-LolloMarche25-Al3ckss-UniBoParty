package it.unibo.uniboparty.view.minigames.sudoku.impl;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Image;

/**
 * Sovrascrive il metodo di disegno standard per mostrare un'immagine di sfondo scalata.
 */
public class ImagePanel extends JPanel {

    // --- AGGIUNGI QUESTA RIGA ---
    private static final long serialVersionUID = 1L;
    // ----------------------------

    private final transient Image backgroundImage; // 'transient' è opzionale ma corretto qui perché Image non è serializzabile

    /**
     * Costruisce un pannello con un'immagine di sfondo.
     *
     * @param backgroundImage L'immagine da usare come sfondo.
     */
    public ImagePanel(final Image backgroundImage) {
        // FIX SPOTBUGS EI2: Facciamo una copia difensiva.
        // Se l'immagine non è nulla, la avvolgiamo in un ImageIcon e la ri-estraiamo.
        // Questo crea un nuovo riferimento sicuro.
        if (backgroundImage != null) {
            this.backgroundImage = new ImageIcon(backgroundImage).getImage();
        } else {
            this.backgroundImage = null;
        }
    }

    /**
     * Sovrascrive il metodo di disegno standard per mostrare un'immagine di sfondo scalata.
     *
     * @param g Il contesto {@link java.awt.Graphics} utilizzato per il disegno.
     */
    @Override
    protected void paintComponent(final Graphics g) {
        // Chiama il metodo base (importante)
        super.paintComponent(g);

        // Disegna l'immagine di sfondo, allargandola
        // per coprire l'intero pannello.
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
