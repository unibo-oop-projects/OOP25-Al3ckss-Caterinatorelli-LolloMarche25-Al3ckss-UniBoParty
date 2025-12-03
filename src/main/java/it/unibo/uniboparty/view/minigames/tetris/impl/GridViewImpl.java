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
    private static final long serialVersionUID = 1L;
    private static final int RED = 0x88FF0000;
    private static final int WHITE = 0x88FFFFFF;
    private static final int GREY = 0x1E1E1E;
    private static final int DARK_GREEN = 0x6AA84F;
    private static final int DARK_GRAY = 0x121212;
    private static final int ARC_WIDTH_HEIGHT = 10;
    private static final int CELL_PADDING_TOTAL = 6;
    private final transient TetrisModel model;
    private final int cellSize;

    /**
     * Creates a new {@code GridViewImpl} instance.
     * 
     * @param model the Tetris model
     * @param cellSize the size of each cell 
     */
    GridViewImpl(final TetrisModel model, final int cellSize) {
        this.model = model;
        this.cellSize = cellSize;
        setPreferredSize(new Dimension(model.getGrid().getCols() * cellSize, model.getGrid().getRows() * cellSize));
        setBackground(new Color(DARK_GRAY));
        setOpaque(true);

        addMouseListener(new MouseAdapter() {
            @Override 
            public void mouseClicked(final MouseEvent e) {
                final int c = e.getX() / cellSize;
                final int r = e.getY() / cellSize;
                model.tryPlaceAt(r, c);
            }
        });

        model.addListener(this);
    }

    /**
     * {@InheritDoc}.
     */
    @Override 
    public void onModelChanged() { 
        repaint(); 
    }

    /**
     * {@InheritDoc}.
     */
    @Override 
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        final Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (int r = 0; r < model.getGrid().getRows(); r++) {
            for (int c = 0; c < model.getGrid().getCols(); c++) {
                final int x = c * cellSize;
                final int y = r * cellSize;
                g2.setColor(new Color(GREY));
                if (model.getGrid().isOccupied(r, c)) {
                    g2.setColor(new Color(DARK_GREEN));
                    g2.fillRoundRect(x + 3, y + 3, cellSize - CELL_PADDING_TOTAL, 
                    cellSize - CELL_PADDING_TOTAL, ARC_WIDTH_HEIGHT, ARC_WIDTH_HEIGHT);
                }
            }
        }

        final PieceImpl sel = model.getSelected();
        if (sel != null) {
            final Point mouse = getMousePosition();
            if (mouse != null) {
                final int baseC = mouse.x / cellSize;
                final int baseR = mouse.y / cellSize;
                final boolean can = model.getGrid().canPlace(sel, baseR, baseC);
                for (final Point rel : sel.getCells()) {
                    final int r = baseR + rel.y;
                    final int c = baseC + rel.x;
                    if (r < 0 || r >= model.getGrid().getRows() || c < 0 || c >= model.getGrid().getCols()) {
                        continue;
                    }
                    final int x = c * cellSize; 
                    final int y = r * cellSize;
                    g2.setColor(new Color(can ? WHITE : RED, true));
                    g2.fillRoundRect(x + 3, y + 3, cellSize - CELL_PADDING_TOTAL,
                    cellSize - CELL_PADDING_TOTAL, ARC_WIDTH_HEIGHT, ARC_WIDTH_HEIGHT);
                }
            }
        }
    }
}


