package it.unibo.uniboparty.view.minigames.mazegame.impl;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import it.unibo.uniboparty.model.minigames.mazegame.api.Cell;
import it.unibo.uniboparty.model.minigames.mazegame.api.MazeModel;
import it.unibo.uniboparty.view.minigames.mazegame.api.MazeView;

/**
 * Implementation of the MazeView interface.
 */
public class MazeViewImpl extends JFrame implements MazeView {

    private static final int CELL_SIZE = 40;
    private MazeModel model;
    private MazePanel mazePanel;
    /**
     * Constructor for MazeViewImpl.
     * 
     * @param model the MazeModel to be visualized
     */
    public MazeViewImpl(final MazeModel model) {
        this.model = model;
        this.mazePanel = new MazePanel();

        setTitle("Maze Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final int cellSize = CELL_SIZE;
        setSize(model.getCols() * cellSize + 16, model.getRows() * cellSize + 39);
        setLocationRelativeTo(null); // centra la finestra
        add(mazePanel);

        setVisible(true);
        requestFocus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onModelUpdated(final MazeModel newModel) {
        this.model = newModel;
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
        this.model = newModel;
        mazePanel.repaint();
    }

    /**
     * Inner class for rendering the maze as a JPanel.
     */
    private final class MazePanel extends JPanel {
        /**
         * {@inheritDoc}
         */
        @Override
        protected void paintComponent(final Graphics g) {
            super.paintComponent(g);
            if (model == null){
                return;
            }

            final int rows = model.getRows();
            final int cols = model.getCols();
            final int cellSize = CELL_SIZE;

            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    Cell cell = model.getCell(r, c);
                    switch (cell.getType()) {
                        case WALL -> g.setColor(Color.BLACK);
                        case EMPTY -> g.setColor(Color.WHITE);
                        case START -> g.setColor(Color.GREEN);
                        case EXIT -> g.setColor(Color.RED);
                    }
                    g.fillRect(c * cellSize, r * cellSize, cellSize, cellSize);

                    if (r == model.getPlayer().getRow() && c == model.getPlayer().getCol()) {
                        g.setColor(Color.BLUE);
                        g.fillOval(c * cellSize + 5, r * cellSize + 5, cellSize - 10, cellSize - 10);
                    }
                }
            }
        }
    }
}
