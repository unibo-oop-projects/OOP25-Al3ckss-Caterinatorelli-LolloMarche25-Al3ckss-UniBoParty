package it.unibo.UniBoParty.view.minigames.typeracerGame.impl;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import it.unibo.UniBoParty.model.minigames.typeracerGame.impl.GameConfig;
import it.unibo.UniBoParty.view.minigames.typeracerGame.api.View;

public class ViewImpl implements View {

    private final JFrame frame = new JFrame("TypeRacer");
    private final JLabel label1 = new JLabel();
    private final JLabel timeLabel = new JLabel();
    private final JTextField textField = new JTextField();

    public ViewImpl() {
        // costruzione GUI sul EDT
        SwingUtilities.invokeLater(() -> {
            frame.setBounds(100, 100, GameConfig.FRAME_WIDTH, GameConfig.FRAME_HEIGHT);

            label1.setFont(new Font("Arial", Font.BOLD, GameConfig.LABEL_FONT_SIZE));
            timeLabel.setFont(new Font("Arial", Font.BOLD, GameConfig.LABEL_FONT_SIZE));
            textField.setFont(new Font("Arial", Font.PLAIN, GameConfig.INPUT_FONT_SIZE));

            label1.setPreferredSize(new Dimension(GameConfig.FRAME_WIDTH, 100));
            timeLabel.setPreferredSize(new Dimension(GameConfig.FRAME_WIDTH, 100));
            textField.setPreferredSize(new Dimension(GameConfig.FRAME_WIDTH, 60));

            frame.add(label1, BorderLayout.NORTH);
            frame.add(timeLabel, BorderLayout.CENTER);
            frame.add(textField, BorderLayout.SOUTH);

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        });
    }

    @Override
    public void setLabel1(String text) {
        label1.setText(text);
    }

    @Override
    public void updateTimeLabel(int t) {
        timeLabel.setText("Tempo rimanente: " + t);
        timeLabel.revalidate();
        timeLabel.repaint();
    }

    @Override
    public JLabel getLabel1() {
        return label1;
    }

    @Override
    public JTextField getTextField() {
        return textField;
    }
}
