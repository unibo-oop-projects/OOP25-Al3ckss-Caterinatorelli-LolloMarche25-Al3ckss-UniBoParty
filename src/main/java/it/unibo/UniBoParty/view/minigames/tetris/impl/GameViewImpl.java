package it.unibo.uniboparty.view.minigames.tetris.impl;

import it.unibo.uniboparty.model.minigames.tetris.api.TetrisModel;
import it.unibo.uniboparty.view.minigames.tetris.api.GameView;
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.Timer;

public final class GameViewImpl extends JFrame implements GameView{
    private final GridViewImpl gridView;
    private final RackViewImpl rackView;
    private final HUD hud;

    /**
     * Creates a new {@code GameViewImpl} instance, initializing the game window with its components.
     * 
     * @param model 
     */
    public GameViewImpl(TetrisModel model) {
        super("Block Blast!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        gridView = new GridViewImpl(model, 36);
        rackView = new RackViewImpl(model);
        hud = new HUD(model);

        add(hud, BorderLayout.NORTH);
        add(gridView, BorderLayout.CENTER);
        add(rackView, BorderLayout.SOUTH);

        setResizable(false);
        pack();
        setLocationRelativeTo(null);

        
        new Timer(40, e -> gridView.repaint()).start();
    }

    /**
     * {@InheritDoc}
     */
    public void refresh() {
        hud.onModelChanged();
        gridView.onModelChanged();
        rackView.refresh();
    }
}