package it.unibo.uniboparty.view.minigames.tetris.impl;

import it.unibo.uniboparty.model.minigames.tetris.api.ModelListener;
import it.unibo.uniboparty.model.minigames.tetris.api.TetrisModel;
import it.unibo.uniboparty.model.minigames.tetris.impl.PieceImpl;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

final class GridViewImpl extends JPanel implements ModelListener {
    private final TetrisModel model;
    private final int cellSize;

    /**
     * Creates a new {@code GridViewImpl} instance.
     * @param model
     * @param cellSize
     */
    public GridViewImpl(TetrisModel model, int cellSize) {
        this.model = model;
        this.cellSize = cellSize;
        setPreferredSize(new Dimension(model.getGrid().getCols() * cellSize, model.getGrid().getRows() * cellSize));
        setBackground(new Color(0x121212));
        setOpaque(true);

        addMouseListener(new MouseAdapter() {
            @Override 
            public void mouseClicked(MouseEvent e) {
                int c = e.getX() / cellSize;
                int r = e.getY() / cellSize;
                model.tryPlaceAt(r, c);
            }
        });

        model.addListener(this);
    }

    /**
     * {@InheritDoc}
     */
    @Override 
    public void onModelChanged() { 
        repaint(); 
    }

    /**
     * {@InheritDoc}
     */
    @Override 
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        
        for (int r = 0; r < model.getGrid().getRows(); r++) {
            for (int c = 0; c < model.getGrid().getCols(); c++) {
                int x = c * cellSize; int y = r * cellSize;
                g2.setColor(new Color(0x1E1E1E));
                g2.fillRoundRect(x+1, y+1, cellSize-2, cellSize-2, 8, 8);
                if (model.getGrid().isOccupied(r, c)) {
                    g2.setColor(new Color(0x6AA84F));
                    g2.fillRoundRect(x+3, y+3, cellSize-6, cellSize-6, 10, 10);
                }
            }
        }

        
        PieceImpl sel = model.getSelected();
        if (sel != null) {
            Point mouse = getMousePosition();
            if (mouse != null) {
                int baseC = mouse.x / cellSize;
                int baseR = mouse.y / cellSize;
                boolean can = model.getGrid().canPlace(sel, baseR, baseC);
                for (Point rel : sel.getCells()) {
                    int r = baseR + rel.y, c = baseC + rel.x;
                    if (r < 0 || r >= model.getGrid().getRows() || c < 0 || c >= model.getGrid().getCols()) continue;
                    int x = c * cellSize; int y = r * cellSize;
                    g2.setColor(new Color(can ? 0x88FFFFFF : 0x88FF0000, true));
                    g2.fillRoundRect(x+3, y + 3, cellSize - 6, cellSize - 6, 10, 10);
                }
            }
        }
    }
}


