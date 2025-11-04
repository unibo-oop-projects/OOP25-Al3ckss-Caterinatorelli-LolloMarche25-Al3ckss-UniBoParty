package it.unibo.UniBoParty.view.minigames.mazegame.api;

import it.unibo.UniBoParty.model.minigames.mazegame.api.MazeModel;

/**
 * Observer interface for receiving updates from the MazeModel.
 */
public interface GameObserver {
/**
 * Called when the MazeModel is updated, so that the view can refresh its display.
 * @param model the updated MazeModel
*/
    void onModelUpdated(MazeModel model);
}
