package it.unibo.uniboparty.controller.minigames.typeracergame.impl;

import java.util.Objects;
import javax.swing.Timer;

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
        this.model = Objects.requireNonNull(model, "Model cannot be null");
        this.view = Objects.requireNonNull(view, "View cannot be null");

        model.setState(GameState.RUNNING);
        model.setNewWord(); // Generate first word before binding view
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
                    view.showFinalScore(model.getPoints());
                } else if (model.getState() == GameState.WIN) {
                    timer.stop();
                    view.showVictoryMessage(model.getPoints());
                }
            } else if (model.getState() == GameState.WIN) {
                timer.stop();
                view.showVictoryMessage(model.getPoints());
            }
        });
    }

    private void setupInputField() {
        view.addTextFieldActionListener(e -> {
            if (model.getState() != GameState.RUNNING) {
                return;
            }

            final String typed = view.getTextFieldText();
            final String current = model.getCurrentWord();

            if (typed.equals(current)) {
                model.incrementPoints();
                model.setNewWord();
                view.clearTextField();
            }
        });
    }

    /**
     * Returns the current game state.
     * 
     * @return 0 if game lost, 1 if game won, 2 if still running
     */
    @Override
    public int getState() {
        return switch (model.getState()) {
            case WIN -> 1;
            case GAME_OVER -> 0;
            default -> 2;
        };
    }

    /**
     * Stops the timer and cleans up resources.
     * Should be called when the game is no longer needed.
     */
    public void cleanup() {
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
    }
}
