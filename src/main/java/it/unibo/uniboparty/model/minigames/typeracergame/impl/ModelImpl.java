package it.unibo.uniboparty.model.minigames.typeracergame.impl;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import it.unibo.uniboparty.model.minigames.typeracergame.api.Model;
import it.unibo.uniboparty.model.minigames.typeracergame.api.GameObserver;

/**
 * Implementation of the game mechanics for TypeRacer.
 */
public final class ModelImpl implements Model {

    private static final List<String> WORDS = WordList.WORDS;

    private final Random random = new Random();

    private int points;
    private int time = GameConfig.INITIAL_TIME_SECONDS;
    private GameState state = GameState.READY;
    private String currentWord;

    private final List<GameObserver> observers = new CopyOnWriteArrayList<>();

    @Override
    public void setNewWord() {
        currentWord = WORDS.get(random.nextInt(WORDS.size()));
        notifyObservers();
    }

    @Override
    public String getCurrentWord() {
        return currentWord;
    }

    @Override
    public void incrementPoints() {
        points++;
        notifyObservers();
    }

    @Override
    public int getPoints() {
        return points;
    }

    @Override
    public int getTime() {
        return time;
    }

    @Override
    public void decreaseTime() {
        if (time > 0) {
            time--;
            if (time == 0) {
                state = GameState.GAME_OVER;
            }
            notifyObservers();
        }
    }

    @Override
    public GameState getState() {
        return state;
    }

    @Override
    public void setState(final GameState newState) {
        state = newState;
        notifyObservers();
    }

    @Override
    public void gameOver() {
        state = GameState.GAME_OVER;
        notifyObservers();
    }

    /**
     * Adds Observer.
     * 
     * @param observer the TypeRacer's Observer
     */
    @Override
    public void addObserver(final GameObserver observer) {
        observers.add(observer);
    }

    /**
     * Removes Observer.
     * 
     * @param observer the TypeRacer's Observer
     */
    @Override
    public void removeObserver(final GameObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        for (final GameObserver o : observers) {
            o.modelUpdated();
        }
    }
}
