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
 * Implementation of the TypeRacer minigame view.
 */
public final class ViewImpl implements View, GameObserver {

    private static final String TIME_PREFIX = "Remaining time: ";

    private final JFrame frame = new JFrame("TypeRacer");
    private final JLabel label1 = new JLabel();
    private final JLabel timeLabel = new JLabel();
    private final JTextField textField = new JTextField();

    private Model boundModel;

    /**
     * Empty constructor to allow instantiation without parameters.
     */
    public ViewImpl() {
        // empty
    }

    /**
     * Starts the Window for TypeRacer.
     */
    public void initView() {
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

            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(final WindowEvent e) {
                    if (boundModel != null) {
                        boundModel.removeObserver(ViewImpl.this);
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
        timeLabel.setText(TIME_PREFIX + t);
        timeLabel.revalidate();
        timeLabel.repaint();
    }

    @Override
    public JTextField getTextField() {
        final JTextField copy = new JTextField(textField.getText());
        copy.setEnabled(textField.isEnabled());
        return copy;
    }

    @Override
    public JLabel getLabel1() {
        final JLabel copy = new JLabel(label1.getText());
        copy.setFont(label1.getFont());
        return copy;
    }

    @Override
    public void bindModel(final Model model) {
        if (this.boundModel != null) {
            this.boundModel.removeObserver(this);
        }
        if (model == null) {
            this.boundModel = null;
            return;
        }
        // wrap the provided model to avoid storing direct external reference
        this.boundModel = new ModelDelegate(model);
        model.addObserver(this);
        SwingUtilities.invokeLater(() -> {
            label1.setText(model.getCurrentWord());
            timeLabel.setText(TIME_PREFIX + model.getTime());
        });
    }

    @Override
    public void addTextFieldActionListener(
            final java.awt.event.ActionListener listener) {
        this.textField.addActionListener(listener);
    }

    @Override
    public String getTextFieldText() {
        return this.textField.getText();
    }

    @Override
    public void clearTextField() {
        SwingUtilities.invokeLater(() -> this.textField.setText(""));
    }

    @Override
    public void modelUpdated() {
        SwingUtilities.invokeLater(() -> {
            if (boundModel == null) {
                return;
            }
            label1.setText(boundModel.getCurrentWord());
            timeLabel.setText(TIME_PREFIX + boundModel.getTime());
        });
    }

    @Override
    public void showFinalScore(final int finalScore) {
        SwingUtilities.invokeLater(() -> {
            label1.setText("Game Over! Final Score: " + finalScore);
            textField.setEnabled(false);
        });
    }

    /**
     * Wrapper for Model to avoid exposing direct mutable object reference.
     */
    private static final class ModelDelegate implements Model {
        private final Model delegate;

        ModelDelegate(final Model delegate) {
            this.delegate = delegate;
        }

        @Override
        public void setNewWord() {
            delegate.setNewWord();
        }

        @Override
        public String getCurrentWord() {
            return delegate.getCurrentWord();
        }

        @Override
        public void incrementPoints() {
            delegate.incrementPoints();
        }

        @Override
        public int getPoints() {
            return delegate.getPoints();
        }

        @Override
        public int getTime() {
            return delegate.getTime();
        }

        @Override
        public void decreaseTime() {
            delegate.decreaseTime();
        }

        @Override
        public it.unibo.uniboparty.model.minigames.typeracergame.impl.GameState getState() {
            return delegate.getState();
        }

        @Override
        public void setState(final it.unibo.uniboparty.model.minigames.typeracergame.impl.GameState state) {
            delegate.setState(state);
        }

        @Override
        public void gameOver() {
            delegate.gameOver();
        }

        @Override
        public void addObserver(final GameObserver observer) {
            delegate.addObserver(observer);
        }

        @Override
        public void removeObserver(final GameObserver observer) {
            delegate.removeObserver(observer);
        }
    }
}
