package it.unibo.uniboparty.model.minigames.typeracergame.impl;

import java.util.List;
import java.util.Random;

import it.unibo.uniboparty.model.minigames.typeracergame.api.Model;

/**
 * Implementation of the game mechanics.
 */
public class ModelImpl implements Model {

    private static final List<String> WORDS = WordList.WORDS;

    private final Random random = new Random();

    private int points;
    private int time = GameConfig.INITIAL_TIME_SECONDS;
    private GameState state = GameState.READY;
    private String currentWord;

    @Override
    public void setNewWord() {
        currentWord = WORDS.get(random.nextInt(WORDS.size()));
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
