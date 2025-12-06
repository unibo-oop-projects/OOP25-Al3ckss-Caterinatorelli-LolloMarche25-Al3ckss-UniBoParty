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
     * Gets the current state of the game.
     * 
     * @return 2 if there is no result, 1 if the player has won, 0 if the game is over
     */
    int getState();
}
