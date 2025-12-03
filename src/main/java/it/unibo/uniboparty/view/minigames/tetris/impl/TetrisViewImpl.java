package it.unibo.uniboparty.view.minigames.tetris.impl;

import it.unibo.uniboparty.model.minigames.tetris.api.TetrisModel;
import it.unibo.uniboparty.view.minigames.tetris.api.TetrisView;
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.Timer;

/**
 * Implementation of the Tetris game view.
 */
public final class TetrisViewImpl extends JFrame implements TetrisView {
    private static final long serialVersionUID = 1L;
    private static final int CELL_SIZE = 36;
    private static final int DELAY = 40;
    private final GridViewImpl gridView;
    private final RackViewImpl rackView;
    private final HUD hud;

    /**
     * Creates a new {@code GameViewImpl} instance, initializing the game window with its components.
     * 
     * @param model the Tetris model
     */
    public TetrisViewImpl(final TetrisModel model) {
        super("Block Blast!");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        gridView = new GridViewImpl(model, CELL_SIZE);
        rackView = new RackViewImpl(model);
        hud = new HUD(model);

        add(hud, BorderLayout.NORTH);
        add(gridView, BorderLayout.CENTER);
        add(rackView, BorderLayout.SOUTH);

        setResizable(false);
        pack();
        setLocationRelativeTo(null);

        new Timer(DELAY, e -> gridView.repaint()).start();
    }

    /**
     * {@InheritDoc}.
     */
    @Override
    public void refresh() {
        hud.onModelChanged();
        gridView.onModelChanged();
        rackView.refresh();
    }
}

