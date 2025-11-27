package it.unibo.uniboparty.view.minigames.tetris.impl;

import it.unibo.uniboparty.model.minigames.tetris.api.TetrisModel;
import it.unibo.uniboparty.model.minigames.tetris.api.ModelListener;
import it.unibo.uniboparty.model.minigames.tetris.impl.PieceImpl;
import it.unibo.uniboparty.view.minigames.tetris.api.Rackview;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Image;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

final class RackViewImpl extends JPanel implements Rackview, ModelListener {
    private static final long serialVersionUID = 1L;
    private static final int ARC_WIDTH_HEIGTH = 6;
    private static final int DARK_GRAY = 0x0F0F0F;
    private static final int DIMENSION_HEIGHT = 150;
    private static final int DIMENSION_WIDTH = 200;
    private final transient TetrisModel model;

    /**
     * Creates a new {@code RackViewImpl} instance.
     * 
     * @param model the Tetris model
     */
    RackViewImpl(final TetrisModel model) {
        this.model = model;
        setLayout(new FlowLayout(FlowLayout.LEFT, 8, 8));
        setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        setBackground(new Color(DARK_GRAY));
        setPreferredSize(new Dimension(DIMENSION_WIDTH, DIMENSION_HEIGHT));
        model.addListener(this);
        refresh();
    }

    /**
     * {@InheritDoc}.
     */
    @Override
    public void refresh() {
        removeAll();
        final ButtonGroup group = new ButtonGroup();
        for (final PieceImpl p : model.getRack()) {
            final JToggleButton btn = new JToggleButton(renderIcon(p));
            btn.setToolTipText(p.getName() + " (" + p.getCells().size() + ")");
            btn.addActionListener(e -> model.selectPiece(p));
            group.add(btn);
            add(btn);
        }
        revalidate();
        repaint();
    }

    /**
     * {@InheritDoc}.
     */
    @Override
    public Icon renderIcon(final PieceImpl p) {
        final int cell = 16;
        final int pad = 3;
        final int w = p.width() * cell + pad * 2;
        final int h = p.height() * cell + pad * 2;
        final Image img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g2 = (Graphics2D) img.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(0, 0, 0, 0));
        g2.fillRect(0, 0, w, h);
        g2.setColor(p.getColor());
        for (final Point rel : p.getCells()) {
            final int x = pad + rel.x * cell;
            final int y = pad + rel.y * cell;
            g2.fillRoundRect(x + 1, y + 1, cell - 2, cell - 2, ARC_WIDTH_HEIGTH, ARC_WIDTH_HEIGTH);
        }
        g2.dispose();
        return new ImageIcon(img);
    }

    /**
     * {@InheritDoc}.
     */
    @Override
    public void onModelChanged() {
        refresh();
    }
}
