package it.unibo.uniboparty.view.minigames.dinosaurgame.impl;

import javax.swing.JFrame;

import it.unibo.uniboparty.controller.minigames.dinosaurgame.impl.ControllerImpl;
import it.unibo.uniboparty.model.minigames.dinosaurgame.impl.ModelImpl;
import it.unibo.uniboparty.utilities.AbstractMinigameIntroFrame;

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
        + "- Avoid all obstacles, you lose if you touch one of them.\n"
    + "- Survive for 30 seconds to win.\n"
    + "- The speed increases over time, so be ready.";
}

    @Override
    protected JFrame createGameFrame() {
        final JFrame gameFrame = new JFrame("Dinosaur Run - Game");
        final ModelImpl model = new ModelImpl();
        final ViewImpl view = new ViewImpl(model);

        // Controller starts the game in its constructor
        new ControllerImpl(model, view);

        gameFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        gameFrame.setContentPane(view);
        gameFrame.pack();

        // Cleanup when window closes
        gameFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(final java.awt.event.WindowEvent e) {
                view.unbindModel();
            }
        });

        return gameFrame;
    }
}
