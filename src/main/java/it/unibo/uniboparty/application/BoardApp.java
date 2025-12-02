package it.unibo.uniboparty.application;

import it.unibo.uniboparty.controller.minigames.typeracergame.impl.ControllerImpl;
import it.unibo.uniboparty.model.minigames.typeracergame.impl.ModelImpl;
import it.unibo.uniboparty.view.minigames.typeracergame.impl.ViewImpl;

public class BoardApp {
    public static void main(String[] args) {
        ModelImpl model = new ModelImpl();
        ViewImpl view = new ViewImpl();

        // Bind view to model BEFORE initialization so observer is registered
        view.bindModel(model);
        
        // Initialize game state
        model.setNewWord();

        new ControllerImpl(model, view);
    }
}

/*DINOSAUR GAME
    public static void main(String[] args) {
        ModelImpl model = new ModelImpl();
        ViewImpl view = new ViewImpl(model);
        new ControllerImpl(model, view);
    }
*/