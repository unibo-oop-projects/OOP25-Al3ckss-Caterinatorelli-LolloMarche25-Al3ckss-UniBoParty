package it.unibo.uniboparty.view.minigames.mazegame.impl;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.uniboparty.model.minigames.mazegame.api.Cell;
import it.unibo.uniboparty.model.minigames.mazegame.api.MazeModel;
import it.unibo.uniboparty.view.minigames.mazegame.api.MazeView;

/**
 * Implementation of the MazeView interface.
 */
public class MazeViewImpl extends JFrame implements MazeView {
    private static final long serialVersionUID = 1L;
    private static final int CELL_SIZE = 40;
    private static final int FRAME_WIDTH_INSET = 16;
    private static final int FRAME_HEIGHT_INSET = 39;
    private static final int PLAYER_PADDING = 5;
    private static final int PLAYER_DIAMETER_INSET = 10;
    private final transient MazeModel model;
    private final MazePanel mazePanel;

    /**
     * Constructor for MazeViewImpl.
     * 
     * @param model the MazeModel to be visualized
     */
    @SuppressFBWarnings(
        value = "EI_EXPOSE_REP2",
        justification = "The model is intended to be shared between the MVC components"
    )
    public MazeViewImpl(final MazeModel model) {
        this.model = model;
        this.mazePanel = new MazePanel();
    }

    /**
     * Setup the JFrame properties.
     */
    @Override
    public void setupFrame() {
        setTitle("Maze Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setSize(model.getCols() * CELL_SIZE + FRAME_WIDTH_INSET, model.getRows() * CELL_SIZE + FRAME_HEIGHT_INSET);

        setLocationRelativeTo(null);
        add(mazePanel);

        setVisible(true);
        requestFocus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onModelUpdated(final MazeModel newModel) {
        mazePanel.repaint();

        if (model.checkWin()) {
            JOptionPane.showMessageDialog(this, "Hai vinto!");
            this.dispose();
        } else if (model.checkLose()) {
            JOptionPane.showMessageDialog(this, "Hai perso!");
            this.dispose();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render(final MazeModel newModel) {
        mazePanel.repaint();
    }

    /**
     * Inner class for rendering the maze as a JPanel.
     */
    private final class MazePanel extends JPanel {
        private static final long serialVersionUID = 1L;

        /**
         * {@inheritDoc}
         */
        @Override
        protected void paintComponent(final Graphics g) {
            super.paintComponent(g);
            if (model == null) {
                return;
            }

            final int rows = model.getRows();
            final int cols = model.getCols();
            final int cellSize = CELL_SIZE;

            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    final Cell cell = model.getCell(r, c);
                    switch (cell.getType()) {
                        case WALL -> g.setColor(Color.BLACK);
                        case EMPTY -> g.setColor(Color.WHITE);
                        case START -> g.setColor(Color.GREEN);
                        case EXIT -> g.setColor(Color.RED);
                    }
                    g.fillRect(c * cellSize, r * cellSize, cellSize, cellSize);

                    if (r == model.getPlayer().getRow() && c == model.getPlayer().getCol()) {
                        g.setColor(Color.BLUE);
                        g.fillOval(c * CELL_SIZE + PLAYER_PADDING, r * CELL_SIZE + PLAYER_PADDING,
                        CELL_SIZE - PLAYER_DIAMETER_INSET, CELL_SIZE - PLAYER_DIAMETER_INSET);
                    }
                }
            }
        }
    }
}
