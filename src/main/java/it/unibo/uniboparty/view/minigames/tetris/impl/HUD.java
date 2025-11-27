package it.unibo.uniboparty.view.minigames.tetris.impl;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.Font;
import it.unibo.uniboparty.model.minigames.tetris.api.ModelListener;
import it.unibo.uniboparty.model.minigames.tetris.api.TetrisModel;

/**
 * Heads-Up Display (HUD) component for displaying game information such as score.
 */
final class HUD extends JPanel implements ModelListener {
    private static final long serialVersionUID = 1L;
    private static final int BLACK = 0x0F0F0F;
    private final transient TetrisModel model;
    private final JLabel scoreLbl = new JLabel("Score: 0");

    HUD(final TetrisModel model) {
        this.model = model;
        scoreLbl.setForeground(Color.WHITE);
        scoreLbl.setFont(new Font("Arial", Font.BOLD, 16));

        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBackground(new Color(BLACK));
        setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        add(scoreLbl);
        add(Box.createHorizontalStrut(16));

        model.addListener(this);
    }

    /**
     * {@InheritDoc}.
     */
    @Override
    public void onModelChanged() {
        scoreLbl.setText("Score: " + model.getScore());
    }
}
