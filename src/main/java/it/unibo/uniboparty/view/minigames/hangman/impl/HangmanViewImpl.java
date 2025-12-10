package it.unibo.uniboparty.view.minigames.hangman.impl;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import it.unibo.uniboparty.view.minigames.hangman.api.HangmanView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

/**
 * Concrete implementation of the {@link HangmanView} interface using Java Swing.
 *
 * <p>
 * This class provides the Graphical User Interface (GUI) for the Hangman game.
 * It organizes the visual components into three main areas:
 *
 * <ul>
 * <li><b>Center:</b> the {@link HangmanDrawPanel} which draws the stick figure;</li>
 * <li><b>North:</b> the display for the secret word (masked or revealed);</li>
 * <li><b>South:</b> the virtual keyboard and the full-word guess input.</li>
 * </ul>
 */
public final class HangmanViewImpl implements HangmanView {

    private static final int WIDTH = 600;
    private static final int HEIGHT = 500;
    private static final int WORD_SIZE = 30;
    private static final int LETTER_SIZE = 14;
    private static final int K_ROWS = 3;
    private static final int K_COLS = 9;
    private static final int K_GAP = 5;
    private static final int BORDER_LEN = 20;
    private static final int TEXT_FIELD = 10;

    private final JFrame frame;
    private final JLabel wordLabel;
    private final HangmanDrawPanel drawPanel;
    private final JTextField guessField;
    private final JButton guessButton;
    private final JPanel keyboardPanel;

    /**
     * Maps each character ('A'-'Z') to its corresponding JButton to allow easy disabling.
     */
    private final Map<Character, JButton> letterButtons = new HashMap<>();

    /**
     * Constructs the Hangman View.
     */
    public HangmanViewImpl() {
        frame = new JFrame("Hangman");
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        drawPanel = new HangmanDrawPanel();
        drawPanel.setBackground(Color.WHITE);
        frame.add(drawPanel, BorderLayout.CENTER);

        wordLabel = new JLabel("_ _ _ _", SwingConstants.CENTER);
        wordLabel.setFont(new Font("Monospaced", Font.BOLD, WORD_SIZE));
        wordLabel.setBorder(BorderFactory.createEmptyBorder(BORDER_LEN, 0, BORDER_LEN, 0));
        frame.add(wordLabel, BorderLayout.NORTH);

        final JPanel southPanel = new JPanel(new BorderLayout());

        keyboardPanel = new JPanel(new GridLayout(K_ROWS, K_COLS, K_GAP, K_GAP));
        final char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        for (final char c : alphabet) {
            final JButton btn = new JButton(String.valueOf(c));
            btn.setFont(new Font("Arial", Font.BOLD, LETTER_SIZE));
            btn.setFocusable(false);
            btn.setActionCommand(String.valueOf(c));
            letterButtons.put(c, btn);
            keyboardPanel.add(btn);
        }
        southPanel.add(keyboardPanel, BorderLayout.CENTER);

        final JPanel guessPanel = new JPanel();
        guessPanel.add(new JLabel("Or guess the whole word:"));
        guessField = new JTextField(TEXT_FIELD);
        guessButton = new JButton("Guess!");
        guessPanel.add(guessField);
        guessPanel.add(guessButton);
        southPanel.add(guessPanel, BorderLayout.SOUTH);

        frame.add(southPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    /**
     * Returns the top-level frame used by this view.
     *
     * @return the Swing {@link JFrame} that shows the game
     */
    @SuppressFBWarnings(
            value = "EI_EXPOSE_REP",
            justification = "The JFrame is the top-level Swing window of the Hangman view. "
                    + "It is intentionally exposed so that external code can show and dispose "
                    + "the minigame window, without modifying the internal state of the view "
                    + "beyond standard Swing usage."
    )
    public JFrame getFrame() {
        return this.frame;
    }

    @Override
    public void updateMaskedWord(final String text) {
        wordLabel.setText(text);
    }

    @Override
    public void updateMan(final int errors) {
        drawPanel.setErrors(errors);
    }

    @Override
    public void disableLetterButton(final char letter) {
        final JButton btn = letterButtons.get(Character.toUpperCase(letter));
        if (btn != null) {
            btn.setEnabled(false);
        }
    }

    @Override
    public void addLetterListener(final ActionListener listener) {
        for (final JButton btn : letterButtons.values()) {
            btn.addActionListener(listener);
        }
    }

    @Override
    public void addGuessWordListener(final ActionListener listener) {
        guessButton.addActionListener(listener);
    }

    @Override
    public String getGuessWordInput() {
        return guessField.getText();
    }

    @Override
    public void showVictory(final String secretWord) {
        JOptionPane.showMessageDialog(
                frame,
                "You Won! The word was: " + secretWord,
                "Victory",
                JOptionPane.INFORMATION_MESSAGE
        );
        frame.dispose();
    }

    @Override
    public void showDefeat(final String secretWord) {
        JOptionPane.showMessageDialog(
                frame,
                "You Lost! The word was: " + secretWord,
                "Lost",
                JOptionPane.ERROR_MESSAGE
        );
        frame.dispose();
    }
}
