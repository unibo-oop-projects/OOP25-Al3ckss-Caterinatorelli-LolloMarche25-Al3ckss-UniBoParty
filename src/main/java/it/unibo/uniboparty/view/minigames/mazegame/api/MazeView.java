package it.unibo.uniboparty.view.minigames.mazegame.api;

import it.unibo.uniboparty.model.minigames.mazegame.api.MazeModel;

/**
 * Interface representing the Maze View.
 */
public interface MazeView extends GameObserver {
    /**
     * Render the maze based on the provided MazeModel.
     * 
     * @param model the MazeModel to render
     */
    void render(MazeModel model);

    /**
     * Setup the JFrame properties.
     */
    void setupFrame();
}
