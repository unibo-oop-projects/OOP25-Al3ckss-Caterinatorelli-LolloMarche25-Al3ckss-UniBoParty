package it.unibo.uniboparty.view.minigames.typeracergame.impl;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import it.unibo.uniboparty.view.minigames.typeracergame.api.View;
import it.unibo.uniboparty.model.minigames.typeracergame.impl.GameConfig;

public final class ViewImpl implements View {

    private final JFrame frame = new JFrame("TypeRacer");
    private final JLabel label1 = new JLabel();
    private final JLabel timeLabel = new JLabel();
    private final JTextField textField = new JTextField();

    public ViewImpl() {
        SwingUtilities.invokeLater(() -> {
            frame.setBounds(100, 100, GameConfig.FRAME_WIDTH, GameConfig.FRAME_HEIGHT);

            label1.setFont(new Font(GameConfig.DEFAULT_FONT, Font.BOLD, GameConfig.LABEL_FONT_SIZE));
            timeLabel.setFont(new Font(GameConfig.DEFAULT_FONT, Font.BOLD, GameConfig.LABEL_FONT_SIZE));
            textField.setFont(new Font(GameConfig.DEFAULT_FONT, Font.PLAIN, GameConfig.INPUT_FONT_SIZE));

            label1.setPreferredSize(new Dimension(GameConfig.FRAME_WIDTH, GameConfig.FRAME_HEIGHT / 4));
            timeLabel.setPreferredSize(new Dimension(GameConfig.FRAME_WIDTH, GameConfig.FRAME_HEIGHT / 4));
            textField.setPreferredSize(new Dimension(GameConfig.FRAME_WIDTH, GameConfig.FRAME_HEIGHT / 4));

            frame.add(label1, BorderLayout.NORTH);
            frame.add(timeLabel, BorderLayout.CENTER);
            frame.add(textField, BorderLayout.SOUTH);

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        });
    }

    @Override
    public void setLabel1(final String text) {
        label1.setText(text);
    }

    @Override
    public void updateTimeLabel(final int t) {
        timeLabel.setText("Remaining time: " + t);
        timeLabel.revalidate();
        timeLabel.repaint();
    }

    @Override
    public JTextField getTextField() {
        return textField;
    }

    @Override
    public JLabel getLabel1() {
        return label1;
    }

}
