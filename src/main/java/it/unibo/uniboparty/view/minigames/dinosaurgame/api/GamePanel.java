package it.unibo.uniboparty.view.minigames.dinosaurgame.api;

/**
 * Interface of the game panel.
 */

@FunctionalInterface
public interface GamePanel {

    /**
     * Repaints the game View.
     * 
     * <p>
     * Called by the controller at every refresh.
     */
    void repaint();
}
