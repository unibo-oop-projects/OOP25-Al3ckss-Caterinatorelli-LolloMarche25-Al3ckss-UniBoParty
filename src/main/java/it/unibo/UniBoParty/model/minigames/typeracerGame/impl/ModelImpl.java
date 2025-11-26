package it.unibo.uniboparty.model.minigames.typeracergame.impl;

import java.util.List;
import java.util.Random;

import it.unibo.uniboparty.model.minigames.typeracergame.api.Model;

/**
 * Implementation of the game mechanics
 */
public class ModelImpl implements Model {

    private int points;
    private int time = GameConfig.INITIAL_TIME_SECONDS;
    private GameState state = GameState.READY;

    private final Random random = new Random();
    private static final List<String> words = WordList.WORDS;

    private String currentWord;

    @Override
    public void setNewWord() {
        currentWord = words.get(random.nextInt(words.size()));
    }

    @Override
    public String getCurrentWord() {
        return currentWord;
    }

    @Override
    public void incrementPoints() {
        points++;
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
        }
    }

    @Override
    public GameState getState() {
        return state;
    }

    @Override
    public void setState(final GameState state) {
        this.state = state;
    }

    @Override
    public void gameOver() {
        state = GameState.GAME_OVER;
    }
}