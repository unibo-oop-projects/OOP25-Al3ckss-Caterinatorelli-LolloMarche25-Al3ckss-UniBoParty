package it.unibo.uniboparty.controller.dice.impl;

import it.unibo.uniboparty.controller.dice.api.DiceController;
import it.unibo.uniboparty.model.dice.api.DiceModel;
import it.unibo.uniboparty.model.dice.impl.DiceModelImpl;
import it.unibo.uniboparty.view.dice.api.DiceView;
import it.unibo.uniboparty.view.dice.impl.DiceViewImpl;

/**
 * Concrete implementation of the {@link DiceController} interface.
 *
 * <p>
 * This class acts as a bridge between the {@link DiceModel} and the {@link DiceView}.
 * It manages the application logic by handling user input (rolling the dice) and
 * updating the view to reflect the changes in the model.
 */
public class DiceControllerImpl implements DiceController {

    private final DiceModel model;
    private final DiceView view;

    /**
     * Constructs a new {@code DiceControllerImpl}.
     *
     * <p>
     * This constructor internally instantiates the concrete implementations of the
     * Model and View ({@link DiceModelImpl} and {@link DiceViewImpl}) to ensure
     * correct initialization and SpotBugs compliance.
     */
    public DiceControllerImpl() {
        this.model = new DiceModelImpl();
        this.view = new DiceViewImpl();

        initController();
    }

    /**
     * Initializes the controller logic.
     *
     * <p>
     * This method performs the initial update of the view and registers the
     * necessary event listeners, specifically setting up the callback for when
     * the dice are rolled.
     */
    private void initController() {
        updateView();

        view.addRollListener(e -> {
            model.roll();
            updateView();
        });
    }

    /**
     * Updates the view components based on the current state of the model.
     *
     * <p>
     * It retrieves the values of the individual dice and the total sum from the
     * model, then updates the corresponding UI elements in the view.
     */
    private void updateView() {
        view.setDiceValues(model.getDie1(), model.getDie2());
        view.setTotalText("Totale: " + model.getTotal());
    }
}
