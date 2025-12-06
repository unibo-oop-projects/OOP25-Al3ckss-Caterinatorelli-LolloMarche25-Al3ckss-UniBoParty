package it.unibo.uniboparty.controller.minigames.tetris.impl;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.uniboparty.controller.minigames.tetris.api.TetrisController;
import it.unibo.uniboparty.model.minigames.tetris.api.TetrisModel;
import it.unibo.uniboparty.model.minigames.tetris.api.ModelListener;
import it.unibo.uniboparty.model.minigames.tetris.impl.TetrisModelImpl;
import it.unibo.uniboparty.view.minigames.tetris.impl.TetrisViewImpl;

/**
 * Implementation of the Tetris game controller.
 */
public final class TetrisControllerImpl implements TetrisController {
    private static final int GRID_SIZE = 8;
    private int state;
    private final TetrisModel model;
    private final TetrisViewImpl view;

    /**
     * Creates a new {@code GameControllerImpl} instance, initializing the game model and view.
     */
    public TetrisControllerImpl() {
        this.model = new TetrisModelImpl(GRID_SIZE, GRID_SIZE);
        this.view = new TetrisViewImpl(model);
        this.state = 2;

        this.model.addListener(new ModelListener() {
            @Override
            public void onModelChanged() {
                view.refresh();
                checkGameOver();
            }
        });
    }

    /**
     * {@InheritDoc}.
     */
    @Override
    public void startGame() {
        view.setVisible(true);
    }

    /**
     * {@InheritDoc}.
     */
    @Override
    public void checkGameOver() {

       if (!model.hasAnyMove()) {
            JOptionPane.showMessageDialog(null, "No moves available. Score: " + model.getScore(),
                    "Game Over", JOptionPane.INFORMATION_MESSAGE);
            state = 0;
            view.dispose();

        }
        if (model.getScore() > 100) {
            JOptionPane.showMessageDialog(null, "You Win. Score: " + model.getScore(),
                    "You wins", JOptionPane.INFORMATION_MESSAGE);
            state = 1;
            view.dispose();

        }
    }

    /**
     * {@InheritDoc}.
     */
    @SuppressFBWarnings(
        value = "EI_EXPOSE_REP",
        justification = "La view è immutabile / l’esposizione è intenzionale"
    )
    @Override
    public JFrame getView() {
        return this.view;
    }

    /**
     * {@InheritDoc}.
     */
    @Override
    public int getState() {
        return this.state;
    }
}
