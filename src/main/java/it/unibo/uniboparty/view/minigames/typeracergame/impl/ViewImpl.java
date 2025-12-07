package it.unibo.uniboparty.view.minigames.typeracergame.impl;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import it.unibo.uniboparty.view.minigames.typeracergame.api.View;
import it.unibo.uniboparty.model.minigames.typeracergame.impl.GameConfig;
import it.unibo.uniboparty.model.minigames.typeracergame.api.Model;
import it.unibo.uniboparty.model.minigames.typeracergame.api.GameObserver;

/**
 * Implementation of the TypeRacer minigame view as a JPanel.
 */
public final class ViewImpl extends JPanel implements View, GameObserver {

    private static final long serialVersionUID = 1L;
    private static final String TIME_PREFIX = "Remaining time: ";

    private final JLabel wordLabel = new JLabel();
    private final JLabel timeLabel = new JLabel();
    private final JTextField textField = new JTextField();

    private transient Model boundModel;

    /**
     * Creates the TypeRacer view panel with all UI components configured.
     */
    public ViewImpl() {
        super(new BorderLayout());
        configureComponents();
    }

    /**
     * Configures the panel components and layout.
     */
    private void configureComponents() {
        wordLabel.setFont(new Font(GameConfig.DEFAULT_FONT, Font.BOLD, GameConfig.LABEL_FONT_SIZE));
        timeLabel.setFont(new Font(GameConfig.DEFAULT_FONT, Font.BOLD, GameConfig.LABEL_FONT_SIZE));
        textField.setFont(new Font(GameConfig.DEFAULT_FONT, Font.PLAIN, GameConfig.INPUT_FONT_SIZE));

        wordLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        timeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        wordLabel.setPreferredSize(new Dimension(GameConfig.FRAME_WIDTH, GameConfig.FRAME_HEIGHT / 4));
        timeLabel.setPreferredSize(new Dimension(GameConfig.FRAME_WIDTH, GameConfig.FRAME_HEIGHT / 4));
        textField.setPreferredSize(new Dimension(GameConfig.FRAME_WIDTH, GameConfig.FRAME_HEIGHT / 4));

        wordLabel.setText("Loading...");
        timeLabel.setText(TIME_PREFIX + "0");

        add(wordLabel, BorderLayout.NORTH);
        add(timeLabel, BorderLayout.CENTER);
        add(textField, BorderLayout.SOUTH);
    }

    @Override
    public void setLabel1(final String text) {
        wordLabel.setText(text);
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
        final JLabel copy = new JLabel(wordLabel.getText());
        copy.setFont(wordLabel.getFont());
        return copy;
    }

    @Override
    public void bindModel(final Model model) {
        if (this.boundModel != null) {
            this.boundModel.removeObserver(this);
        }

        // Wrap model to avoid exposing internal mutable reference
        this.boundModel = model == null ? null : new ModelDelegate(model);

        if (model != null) {
            model.addObserver(this);
            SwingUtilities.invokeLater(() -> {
                wordLabel.setText(model.getCurrentWord());
                timeLabel.setText(TIME_PREFIX + model.getTime());
            });
        }
    }

    /**
     * Unregisters this view from the bound model.
     * Should be called when the view is no longer needed (e.g., window closing).
     */
    public void unbindModel() {
        if (boundModel != null) {
            boundModel.removeObserver(this);
            boundModel = null;
        }
    }

    @Override
    public void addTextFieldActionListener(final java.awt.event.ActionListener listener) {
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
            wordLabel.setText(boundModel.getCurrentWord());
            timeLabel.setText(TIME_PREFIX + boundModel.getTime());
        });
    }

    @Override
    public void showFinalScore(final int finalScore) {
        SwingUtilities.invokeLater(() -> {
            wordLabel.setText("Game Over! Final Score: " + finalScore);
            textField.setEnabled(false);
        });
    }

    @Override
    public void showVictoryMessage(final int finalScore) {
        SwingUtilities.invokeLater(() -> {
            wordLabel.setText("You Win! Score: " + finalScore + " words!");
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
