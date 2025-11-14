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
    private final TetrisModel model;

    /**
     * Creates a new {@code RackViewImpl} instance.
     * 
     * @param model the Tetris model
     */
    RackViewImpl(final TetrisModel model) {
        this.model = model;
        setLayout(new FlowLayout(FlowLayout.LEFT, 8, 8));
        setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        setBackground(new Color(0x0F0F0F));
        setPreferredSize(new Dimension(200, 150));
        model.addListener(this);
        refresh();
    }

    /**
     * {@InheritDoc}
     */
    public void refresh() {
        removeAll();
        ButtonGroup group = new ButtonGroup();
        for (PieceImpl p : model.getRack()) {
            JToggleButton btn = new JToggleButton(renderIcon(p));
            btn.setToolTipText(p.getName() + " (" + p.getCells().size() + ")");
            btn.addActionListener(e -> model.selectPiece(p));
            group.add(btn);
            add(btn);
        }
        revalidate();
        repaint();
    }

    /**
     * {@InheritDoc}
     */
    public Icon renderIcon(PieceImpl p) {
        int cell = 16;
        int pad = 3;
        int w = p.width()*cell + pad*2;
        int h = p.height()*cell + pad*2;
        Image img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) img.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(0,0,0,0)); g2.fillRect(0,0,w,h);
        g2.setColor(p.getColor());
        for (Point rel : p.getCells()) {
            int x = pad + rel.x*cell; int y = pad + rel.y*cell;
            g2.fillRoundRect(x+1, y+1, cell-2, cell-2, 6, 6);
        }
        g2.dispose();
        return new ImageIcon(img);
    }

    /**
     * {@InheritDoc}
     */
    @Override
    public void onModelChanged() {
        refresh();
    }
}
