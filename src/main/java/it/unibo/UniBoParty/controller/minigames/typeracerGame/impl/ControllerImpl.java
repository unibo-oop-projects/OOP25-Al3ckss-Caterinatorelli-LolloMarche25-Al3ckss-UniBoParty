package it.unibo.uniboparty.controller.minigames.typeracergame.impl;

import javax.swing.Timer;

import it.unibo.uniboparty.controller.minigames.typeracergame.api.Controller;
import it.unibo.uniboparty.model.minigames.typeracergame.api.Model;
import it.unibo.uniboparty.model.minigames.typeracergame.impl.GameConfig;
import it.unibo.uniboparty.model.minigames.typeracergame.impl.GameState;
import it.unibo.uniboparty.view.minigames.typeracergame.api.View;

import javax.swing.SwingUtilities;

/**
 * Implementation of Typeracer's Controller.
 * Handles the connection between Model and View and the timing.
 */
public class ControllerImpl implements Controller {

    private final Model model;
    private final View view;
    private Timer timer;

    public ControllerImpl(final Model model, final View view) {
        this.model = model;
        this.view = view;

        model.setState(GameState.RUNNING);

        timer = new Timer(GameConfig.TIMER_DELAY_MS, e -> {
            if (model.getState() == GameState.RUNNING) {
                model.decreaseTime();
                SwingUtilities.invokeLater(() -> view.updateTimeLabel(model.getTime()));

                if (model.getTime() <= 0) {
                    model.gameOver();
                    SwingUtilities.invokeLater(() -> view.setLabel1("Tempo Finito. Punti: " + model.getPoints()));
                    timer.stop();
                }
            }
        });

        setupInputField();
        timer.start();
    }

    private void setupInputField() {
        view.getTextField().addActionListener(e -> {
            if (model.getState() != GameState.RUNNING) { return; }

            final String typed = view.getTextField().getText();
            final String current = model.getCurrentWord();

            if (typed.equals(current)) {
                model.incrementPoints();
                model.setNewWord();
                SwingUtilities.invokeLater(() -> {
                    view.setLabel1(model.getCurrentWord());
                    view.getTextField().setText("");
                });
            }
        });
    }
}
