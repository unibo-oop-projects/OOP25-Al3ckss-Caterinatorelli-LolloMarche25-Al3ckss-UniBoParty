package it.unibo.UniBoParty.application;

import it.unibo.UniBoParty.controller.minigames.dinosaurGame.impl.ControllerImpl;
import it.unibo.UniBoParty.model.minigames.dinosaurGame.impl.ModelImpl;
import it.unibo.UniBoParty.view.minigames.dinosaurGame.impl.ViewImpl;

public class App {
    public static void main(String[] args) {
        ModelImpl model = new ModelImpl();
        ViewImpl view = new ViewImpl(model);
        new ControllerImpl(model, view);
    }
}