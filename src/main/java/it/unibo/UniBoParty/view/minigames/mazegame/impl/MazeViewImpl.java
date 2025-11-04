package it.unibo.UniBoParty.view.minigames.mazegame.impl;

import javax.swing.*;
import java.awt.*;
import it.unibo.UniBoParty.model.minigames.mazegame.api.Cell;
import it.unibo.UniBoParty.model.minigames.mazegame.api.MazeModel;
import it.unibo.UniBoParty.view.minigames.mazegame.api.MazeView;

public class MazeViewImpl extends JFrame implements MazeView {

    private MazeModel model;
    private MazePanel mazePanel;

    public MazeViewImpl(MazeModel model) {
        this.model = model;
        this.mazePanel = new MazePanel();

        setTitle("Maze Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int cellSize = 40;
        setSize(model.getCols() * cellSize + 16, model.getRows() * cellSize + 39);
        setLocationRelativeTo(null); // centra la finestra
        add(mazePanel);

        setVisible(true);
        requestFocus();
    }
    /**
     * @{inheritDoc}
     */
    @Override
    public void onModelUpdated(MazeModel model) {
        this.model = model;
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
     * @{inheritDoc}
     */
    @Override
    public void render(MazeModel model) {
        this.model = model;
        mazePanel.repaint();
    }

    /**
     * Inner class for rendering the maze as a JPanel.
     */
    private class MazePanel extends JPanel {
        /*+
         * @{inheritDoc}
         */
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (model == null) return;

            int rows = model.getRows();
            int cols = model.getCols();
            int cellSize = 40;

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
