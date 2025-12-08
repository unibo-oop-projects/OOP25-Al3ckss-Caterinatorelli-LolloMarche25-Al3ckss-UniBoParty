package it.unibo.uniboparty.controller.minigames.typeracergame.impl;

import java.util.Objects;
import javax.swing.Timer;

import it.unibo.uniboparty.controller.minigames.typeracergame.api.Controller;
import it.unibo.uniboparty.model.minigames.typeracergame.api.Model;
import it.unibo.uniboparty.model.minigames.typeracergame.api.GameObserver;
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

    private final ModelDelegate model;
    private final ViewDelegate view;
    private Timer timer;

    /**
     * Constructor of ControllerImpl.
     * Starts the timer and launches the methods to update the window.
     * 
     * @param model the TypeRacer's model
     * @param view the TypeRacer's view
     */
    public ControllerImpl(final Model model, final View view) {
        Objects.requireNonNull(model);
        Objects.requireNonNull(view);

        this.model = new ModelDelegate(model);
        this.view = new ViewDelegate(view);

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
     * Wrapper for Model to avoid exposing direct mutable object reference.
     */
    private static final class ModelDelegate implements Model {
        private final Model delegate;

        ModelDelegate(final Model delegate) {
            this.delegate = delegate;
        }

        @Override
        public void setNewWord() {
            delegate.setNewWord();
        }

        @Override
        public String getCurrentWord() {
            return delegate.getCurrentWord();
        }

        @Override
        public void incrementPoints() {
            delegate.incrementPoints();
        }

        @Override
        public int getPoints() {
            return delegate.getPoints();
        }

        @Override
        public int getTime() {
            return delegate.getTime();
        }

        @Override
        public void decreaseTime() {
            delegate.decreaseTime();
        }

        @Override
        public GameState getState() {
            return delegate.getState();
        }

        @Override
        public void setState(final GameState state) {
            delegate.setState(state);
        }

        @Override
        public void gameOver() {
            delegate.gameOver();
        }

        @Override
        public void addObserver(final GameObserver observer) {
            delegate.addObserver(observer);
        }

        @Override
        public void removeObserver(final GameObserver observer) {
            delegate.removeObserver(observer);
        }
    }

    /**
     * Wrapper for View to avoid exposing direct mutable object reference.
     */
    private static final class ViewDelegate implements View {
        private final View delegate;

        ViewDelegate(final View delegate) {
            this.delegate = delegate;
        }

        @Override
        public void setLabel1(final String text) {
            delegate.setLabel1(text);
        }

        @Override
        public void updateTimeLabel(final int t) {
            delegate.updateTimeLabel(t);
        }

        @Override
        public javax.swing.JTextField getTextField() {
            return delegate.getTextField();
        }

        @Override
        public javax.swing.JLabel getLabel1() {
            return delegate.getLabel1();
        }

        @Override
        public void bindModel(final Model model) {
            delegate.bindModel(model);
        }

        @Override
        public void addTextFieldActionListener(
                final java.awt.event.ActionListener listener) {
            delegate.addTextFieldActionListener(listener);
        }

        @Override
        public String getTextFieldText() {
            return delegate.getTextFieldText();
        }

        @Override
        public void clearTextField() {
            delegate.clearTextField();
        }

        @Override
        public void showFinalScore(final int finalScore) {
            delegate.showFinalScore(finalScore);
        }

        @Override
        public void showVictoryMessage(final int finalScore) {
            delegate.showVictoryMessage(finalScore);
        }
    }
}
