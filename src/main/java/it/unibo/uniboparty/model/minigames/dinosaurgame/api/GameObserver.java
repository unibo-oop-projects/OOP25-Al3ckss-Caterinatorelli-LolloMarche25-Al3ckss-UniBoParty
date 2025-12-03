package it.unibo.uniboparty.model.minigames.dinosaurgame.api;

/**
 * Interface for observers of the dinosaur game model.
 */
@FunctionalInterface
public interface GameObserver {

    /**
     * Called when the model updates its state.
     */
    void modelUpdated();
}
