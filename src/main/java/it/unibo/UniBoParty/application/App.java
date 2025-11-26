package it.unibo.uniboparty.application;

import it.unibo.uniboparty.controller.minigames.dinosaurgame.impl.ControllerImpl;
import it.unibo.uniboparty.model.minigames.dinosaurgame.impl.ModelImpl;
import it.unibo.uniboparty.view.minigames.dinosaurgame.impl.ViewImpl;

/**
 * Main file to run the game
 */
public class App {
    public static void main(final String[] args) {
        final ModelImpl model = new ModelImpl();
        final ViewImpl view = new ViewImpl(model);
        new ControllerImpl(model, view);
    }
}
