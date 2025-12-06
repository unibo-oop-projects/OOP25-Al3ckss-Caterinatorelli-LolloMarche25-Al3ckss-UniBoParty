package it.unibo.uniboparty.view.minigames.dinosaurgame.impl;

import javax.swing.JFrame;

import it.unibo.uniboparty.utilities.AbstractMinigameIntroFrame;
import it.unibo.uniboparty.view.minigames.whacamole.impl.WhacAMoleViewImpl;

/**
 * Intro window for the Dinosaur Game minigame.
 */
public final class DinoGameIntroFrame extends AbstractMinigameIntroFrame {

    private static final long serialVersionUID = 1L;

    /**
     * Creates the intro window for the Dinosaur Game minigame.
     */
    public DinoGameIntroFrame() {
        super();
        initIntroFrame();
    }

    @Override
    protected String getMinigameTitle() {
        return "Dinosaur Run";
    }

@Override
protected String getRulesText() {
    return
          "How to play:\n"
        + "- Press SPACE to make the dinosaur jump.\n"
        + "- Avoid all obstacles: hitting one ends the game.\n"
        + "- The longer you survive, the higher your score.\n"
        + "- The speed increases over time, so be ready";
}

    @Override
    protected JFrame createGameFrame() {
        final JFrame gameFrame = new JFrame("Dinosaur Run");
        gameFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        gameFrame.setContentPane(new WhacAMoleViewImpl());
        gameFrame.pack();
        return gameFrame;
    }
}
