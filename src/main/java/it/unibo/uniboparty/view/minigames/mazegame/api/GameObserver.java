package it.unibo.uniboparty.view.minigames.mazegame.api;

import it.unibo.uniboparty.model.minigames.mazegame.api.MazeModel;

/**
 * Observer interface for receiving updates from the MazeModel.
 */
@FunctionalInterface
public interface GameObserver {

/**
 * Called when the MazeModel is updated, so that the view can refresh its display.
 * 
 * @param model the updated MazeModel
 */
void onModelUpdated(MazeModel model);
}
