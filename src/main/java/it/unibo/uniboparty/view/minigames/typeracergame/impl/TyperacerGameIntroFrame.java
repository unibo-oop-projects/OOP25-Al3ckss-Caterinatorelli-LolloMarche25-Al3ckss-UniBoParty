package it.unibo.uniboparty.view.minigames.typeracergame.impl;

import javax.swing.JFrame;

import it.unibo.uniboparty.utilities.AbstractMinigameIntroFrame;
import it.unibo.uniboparty.view.minigames.whacamole.impl.WhacAMoleViewImpl;

/**
 * Intro window for the TyperacerGame minigame.
 */
public final class TyperacerGameIntroFrame extends AbstractMinigameIntroFrame {

    private static final long serialVersionUID = 1L;

    /**
     * Creates the intro window for the for the TyperacerGame minigame.
     */
    public TyperacerGameIntroFrame() {
        super();
        initIntroFrame();
    }

    @Override
    protected String getMinigameTitle() {
        return "TypeRacer";
    }

@Override
protected String getRulesText() {
    return
          "How to play:\n"
        + "- Type the displayed sentence as fast as you can.\n"
        + "- Every correct word gives you 1 point.\n"
        + "- If you make a mistake, you need to try again.\n"
        + "- You have 20 seconds: type as many words as you can to get more points.";
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
