package it.unibo.uniboparty.model.minigames.tetris.api;

/**
 * Listener interface for receiving notifications when a model changes.
 */
@FunctionalInterface
public interface ModelListener {

    /**
     * Called when the model has changed.
     */
    void onModelChanged();
}
