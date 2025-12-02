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
import it.unibo.uniboparty.model.minigames.typeracergame.api.Model;
import it.unibo.uniboparty.model.minigames.typeracergame.api.GameObserver;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

    /**
     * Implementation of Typeracer view.
     * 
     * Creates the window and updates it with a new name.
     */
public final class ViewImpl implements View, GameObserver {

    private final JFrame frame = new JFrame("TypeRacer");
    private final JLabel label1 = new JLabel();
    private final JLabel timeLabel = new JLabel();
    private final JTextField textField = new JTextField();

    private Model model;

    /**
     * Constructor of ViewImpl.
     * 
     * Initializes the window and updates it.
     */
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

            // deregister observer on window close if bound
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(final WindowEvent e) {
                    if (model != null) {
                        model.removeObserver(ViewImpl.this);
                    }
                }
            });
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

    @Override
    public void bindModel(final Model model) {
        this.model = model;
        if (this.model != null) {
            this.model.addObserver(this);
            // initialize view with current state
            SwingUtilities.invokeLater(() -> {
                label1.setText(this.model.getCurrentWord());
                timeLabel.setText("Remaining time: " + this.model.getTime());
            });
        }
    }

    @Override
    public void modelUpdated() {
        SwingUtilities.invokeLater(() -> {
            if (model == null) return;
            label1.setText(model.getCurrentWord());
            timeLabel.setText("Remaining time: " + model.getTime());
        });
    }

}
