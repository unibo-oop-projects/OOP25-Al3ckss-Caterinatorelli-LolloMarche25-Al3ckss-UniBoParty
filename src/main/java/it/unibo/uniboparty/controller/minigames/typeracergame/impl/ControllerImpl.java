package it.unibo.uniboparty.controller.minigames.typeracergame.impl;

import java.util.Objects;
import javax.swing.Timer;
import javax.swing.SwingUtilities;

import it.unibo.uniboparty.controller.minigames.typeracergame.api.Controller;
import it.unibo.uniboparty.model.minigames.typeracergame.api.Model;
import it.unibo.uniboparty.model.minigames.typeracergame.impl.GameConfig;
import it.unibo.uniboparty.model.minigames.typeracergame.impl.GameState;
import it.unibo.uniboparty.view.minigames.typeracergame.api.View;

/**
 * Implementation of Controller for TypeRacer.
 * 
 * <p>
 * Manages the game loop (timer for time decrement), input handling (text field),
 * and delegates UI updates to the view via observer pattern.
 * The view registers itself as observer to the model and updates automatically
 * when the model state changes.
 * </p>
 */
public final class ControllerImpl implements Controller {

    private final Model model;
    private final View view;
    private Timer timer;

    /**
     * Constructor of ControllerImpl.
     * Starts the timer and launches the methods to update the window.
     * 
     * @param model the TypeRacer's model
     * @param view the TypeRacer's view
     */
    public ControllerImpl(final Model model, final View view) {
        this.model = Objects.requireNonNull(model);
        this.view = Objects.requireNonNull(view);

        model.setState(GameState.RUNNING);
        // bind the view to the model so the view will observe updates
        view.bindModel(model);

        setupTimer();
        setupInputField();
        timer.start();
    }

    private void setupTimer() {
        timer = new Timer(GameConfig.TIMER_DELAY_MS, e -> {
            if (model.getState() == GameState.RUNNING) {
                model.decreaseTime();
                if (model.getTime() <= 0) {
                    model.gameOver();
                    timer.stop();
                }
            }
        });
    }

    private void setupInputField() {
        view.getTextField().addActionListener(e -> {
            if (model.getState() != GameState.RUNNING) { return; }

            final String typed = view.getTextField().getText();
            final String current = model.getCurrentWord();

            if (typed.equals(current)) {
                model.incrementPoints();
                model.setNewWord();
                SwingUtilities.invokeLater(() -> view.getTextField().setText(""));
            }
        });
    }
}
