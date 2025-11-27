package it.unibo.uniboparty.controller.dice.impl;

import it.unibo.uniboparty.controller.dice.api.DiceController;
import it.unibo.uniboparty.model.dice.api.DiceModel;
import it.unibo.uniboparty.model.dice.impl.DiceModelImpl;
import it.unibo.uniboparty.view.dice.api.DiceView;
import it.unibo.uniboparty.view.dice.impl.DiceViewImpl;

/**
 * Concrete implementation of the dice Controller.
 */
public class DiceControllerImpl implements DiceController {

    private final DiceModel model;
    private final DiceView view;

    /**
     * Costruttore: Istanzia Model e View internamente (SpotBugs friendly).
     */
    public DiceControllerImpl() {
        this.model = new DiceModelImpl();
        this.view = new DiceViewImpl();

        initController();
    }

    private void initController() {
        // Mostra lo stato iniziale
        updateView();

        // Quando clicchi "LANCIA"
        view.addRollListener(e -> {
            model.roll();
            updateView();
        });
    }

    private void updateView() {
        view.setDiceValues(model.getDie1(), model.getDie2());
        view.setTotalText("Totale: " + model.getTotal());

        // QUI POTRAI AGGIUNGERE LA LOGICA PER MUOVERE LA PEDINA NEL GIOCO DELL'OCA
        // Esempio: gameBoard.movePlayer(model.getTotal());
    }
}
