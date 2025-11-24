package it.unibo.UniBoParty.model.minigames.typeracerGame.impl;

import java.util.List;
import java.util.Random;

import it.unibo.UniBoParty.model.minigames.typeracerGame.api.Model;

public class ModelImpl implements Model {

    private int points = 0;
    private int time = GameConfig.INITIAL_TIME_SECONDS;
    private GameState state = GameState.READY;

    private final Random random = new Random();
    private final List<String> words = WordList.WORDS;

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
            if (time == 0) state = GameState.GAME_OVER;
        }
    }

    @Override
    public GameState getState() {
        return state;
    }

    @Override
    public void setState(GameState state) {
        this.state = state;
    }

    @Override
    public void gameOver() {
        state = GameState.GAME_OVER;
    }
}
