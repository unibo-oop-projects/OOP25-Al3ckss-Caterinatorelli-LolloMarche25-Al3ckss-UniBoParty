package it.unibo.UniBoParty.view.minigames.hangman.impl;

import it.unibo.UniBoParty.view.minigames.hangman.api.HangmanView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class HangmanViewImpl implements HangmanView {

    private final JFrame frame;
    private final JLabel wordLabel;
    private final HangmanDrawPanel drawPanel;
    private final JTextField guessField;
    private final JButton guessButton;
    private final JPanel keyboardPanel;

    private final Map<Character, JButton> letterButtons = new HashMap<>();

    public HangmanViewImpl() {
        frame = new JFrame("L'Impiccato");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        drawPanel = new HangmanDrawPanel();
        drawPanel.setBackground(Color.WHITE);
        frame.add(drawPanel, BorderLayout.CENTER);

        wordLabel = new JLabel("_ _ _ _", SwingConstants.CENTER);
        wordLabel.setFont(new Font("Monospaced", Font.BOLD, 30));
        wordLabel.setBorder(BorderFactory.createEmptyBorder(20,0,20,0));
        frame.add(wordLabel, BorderLayout.NORTH);

        JPanel southPanel = new JPanel(new BorderLayout());

        keyboardPanel = new JPanel(new GridLayout(3, 9, 5, 5));
        char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        for (char c : alphabet) {
            JButton btn = new JButton(String.valueOf(c));
            btn.setFont(new Font("Arial", Font.BOLD, 14));
            btn.setFocusable(false);
            btn.setActionCommand(String.valueOf(c));
            letterButtons.put(c, btn);
            keyboardPanel.add(btn);
        }
        southPanel.add(keyboardPanel, BorderLayout.CENTER);

        JPanel guessPanel = new JPanel();
        guessPanel.add(new JLabel("O indovina la parola:"));
        guessField = new JTextField(10);
        guessButton = new JButton("Prova!");
        guessPanel.add(guessField);
        guessPanel.add(guessButton);
        southPanel.add(guessPanel, BorderLayout.SOUTH);

        frame.add(southPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    @Override
    public void updateMaskedWord(String text) {
        wordLabel.setText(text);
    }

    @Override
    public void updateMan(int errors) {
        drawPanel.setErrors(errors);
    }

    @Override
    public void disableLetterButton(char letter) {
        JButton btn = letterButtons.get(Character.toUpperCase(letter));
        if (btn != null) {
            btn.setEnabled(false);
        }
    }

    @Override
    public void addLetterListener(ActionListener listener) {
        for (JButton btn : letterButtons.values()) {
            btn.addActionListener(listener);
        }
    }

    @Override
    public void addGuessWordListener(ActionListener listener) {
        guessButton.addActionListener(listener);
    }

    @Override
    public String getGuessWordInput() {
        return guessField.getText();
    }

    @Override
    public void showVictory(String secretWord) {
        JOptionPane.showMessageDialog(frame,
                "Hai Vinto! La parola era: " + secretWord, "Vittoria", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    @Override
    public void showDefeat(String secretWord) {
        JOptionPane.showMessageDialog(frame,
                "Hai Perso! La parola era: " + secretWord, "Sconfitta", JOptionPane.ERROR_MESSAGE);
        System.exit(0);
    }
}