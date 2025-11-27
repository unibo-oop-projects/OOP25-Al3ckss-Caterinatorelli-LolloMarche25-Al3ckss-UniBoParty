package it.unibo.uniboparty.view.minigames.tetris.api;

/**
 * Represents the view component in the Tetris game.
 * Implementations of this interface are responsible for updating the visual representation
 * of the game state.
 */ 

public interface GameView {
    /**
     * Refreshes the game view to reflect the current state of the game.
     * This method should be called whenever the game state changes and the view needs to be updated.
     */
    void refresh();

    /**
     * Sets the visibility of the game view.
     * 
     * @param b true to make the view visible, false to hide it.
     */
    void setVisible(boolean b);

    /**
     * Disposes of the game view, releasing any resources it holds.
     * This method should be called when the view is no longer needed.
     */
    void dispose();
}
