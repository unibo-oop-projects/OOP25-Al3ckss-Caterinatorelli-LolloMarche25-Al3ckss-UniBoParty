package it.unibo.uniboparty.controller.minigames.mazegame.impl;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.uniboparty.controller.minigames.mazegame.api.MazeController;
import it.unibo.uniboparty.model.minigames.mazegame.api.MazeModel;
import it.unibo.uniboparty.model.minigames.mazegame.impl.MazeModelImpl;
import it.unibo.uniboparty.utilities.Direction;
import it.unibo.uniboparty.view.minigames.mazegame.impl.MazeViewImpl;

/**
 * Implementation of the MazeController interface.
 */
public class MazeControllerImpl implements MazeController {

    private final MazeModel model;
    private final MazeViewImpl view;

    /**
     * Constructor for MazeControllerImpl.
     */
    public MazeControllerImpl() {
        this.model = new MazeModelImpl();
        this.view = new MazeViewImpl(model);
        model.addObserver(view);
        addKeyBindings(view);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startNewGame() {
        model.reset();
        view.render(model);
    }

    private void addKeyBindings(final JFrame frame) {
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(final KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP -> model.movePlayer(Direction.UP);
                    case KeyEvent.VK_DOWN -> model.movePlayer(Direction.DOWN);
                    case KeyEvent.VK_LEFT -> model.movePlayer(Direction.LEFT);
                    case KeyEvent.VK_RIGHT -> model.movePlayer(Direction.RIGHT);
                    default -> { }
                }
            }
        });
    }

    /**
     * {@inheritDoc}
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
     * {@inheritDoc}
     */
    @Override
    public int getState() {
        return this.view.getState();
    }
}
