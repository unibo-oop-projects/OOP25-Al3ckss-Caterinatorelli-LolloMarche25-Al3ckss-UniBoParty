package it.unibo.uniboparty.controller.minigames.typeracergame.impl;

import java.util.Objects;
import javax.swing.Timer;
import javax.swing.SwingUtilities;

import it.unibo.uniboparty.controller.minigames.typeracergame.api.Controller;
import it.unibo.uniboparty.model.minigames.typeracergame.api.Model;
import it.unibo.uniboparty.model.minigames.typeracergame.api.GameObserver;
import it.unibo.uniboparty.model.minigames.typeracergame.impl.GameConfig;
import it.unibo.uniboparty.model.minigames.typeracergame.impl.GameState;
import it.unibo.uniboparty.view.minigames.typeracergame.api.View;

public final class ControllerImpl implements Controller, GameObserver {

    private final Model model;
    private final View view;
    private Timer timer;

    public ControllerImpl(final Model model, final View view) {
        this.model = Objects.requireNonNull(model);
        this.view = Objects.requireNonNull(view);

        model.setState(GameState.RUNNING);
        model.addObserver(this);

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
            if (model.getState() != GameState.RUNNING) return;

            final String typed = view.getTextField().getText();
            final String current = model.getCurrentWord();

            if (typed.equals(current)) {
                model.incrementPoints();
                model.setNewWord();
                SwingUtilities.invokeLater(() -> view.getTextField().setText(""));
            }
        });
    }

    @Override
    public void modelUpdated() {
        SwingUtilities.invokeLater(() -> {
            view.setLabel1(model.getCurrentWord());
            view.updateTimeLabel(model.getTime());
        });
    }
}
