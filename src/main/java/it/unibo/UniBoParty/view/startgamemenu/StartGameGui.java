package it.unibo.uniboparty.view.startgamemenu;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import it.unibo.uniboparty.model.startgamemenu.api.LogicStartGame;
import it.unibo.uniboparty.model.startgamemenu.impl.LogicStartGameImpl;

import java.awt.Graphics;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;

/**
 * Represents the Graphical User Interface (GUI) window for the "Unibo Party" game start menu.
 * It allows the user to enter player names and start the game.
 */
public class StartGameGui extends JFrame {
 private final JButton startButton;
    private final JButton exitButton;
    private final List<JTextField> playerFields;
    private final LogicStartGame logic;

    public StartGameGui() {
        this.logic = new LogicStartGameImpl();

        setTitle("Unibo Party");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        final Image background = new ImageIcon(
        "/Users/caterinatorelli/Desktop/uniboparty/src/main/resources/background.png").getImage();
        final Image logo = new ImageIcon(   "/Users/caterinatorelli/Desktop/uniboparty/src/main/resources/logo.png").getImage();

        final JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(final Graphics g) {
                super.paintComponent(g);
                g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            }
        };
        mainPanel.setLayout(new BorderLayout());
        add(mainPanel);

        final JPanel logoPanel = new JPanel() {
            @Override
            protected void paintComponent(final Graphics g) {
                super.paintComponent(g);
                final  int width = 500;
                final int height = 120;
                int x = (getWidth() - width) / 2;
                g.drawImage(logo, x, 10, width, height, this);
            }
        };
        logoPanel.setOpaque(false);
        logoPanel.setPreferredSize(new Dimension(800, 150));
        mainPanel.add(logoPanel, BorderLayout.NORTH);

        final JPanel playerPanel = new JPanel(new GridLayout(5, 2, 15, 15));
        playerPanel.setOpaque(false);
        playerPanel.setBorder(BorderFactory.createEmptyBorder(40, 150, 40, 150));

        playerFields = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            final JLabel label = new JLabel("Giocatore " + i + ": ");
            label.setFont(new Font("Comic Sans MS", Font.BOLD, 22));
            label.setForeground(Color.BLACK);

            final JTextField field = new JTextField();
            field.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
            field.setBackground(new Color(255, 255, 255, 220));
            field.setBorder(BorderFactory.createLineBorder(Color.PINK, 2, true));

            playerFields.add(field);
            playerPanel.add(label);
            playerPanel.add(field);
        }
        mainPanel.add(playerPanel, BorderLayout.CENTER);

        final JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);

        startButton = createButton(" Avvia Gioco ", new Color(144, 238, 144));
        exitButton = createButton(" Esci", new Color(255, 99, 71));

        buttonPanel.add(startButton);
        buttonPanel.add(exitButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        startButton.addActionListener(this::onStartPressed);
        exitButton.addActionListener(e -> System.exit(0));
    }

    private JButton createButton(final String text, final Color color) {
        final JButton button = new JButton(text);
        button.setBackground(color);
        button.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3, true));
        button.setPreferredSize(new Dimension(220, 60));
        return button;
    }

    private void onStartPressed(final ActionEvent e) {
        final List<String> names = new ArrayList<>();
        for (final JTextField f : playerFields) {
            if (!f.getText().isBlank()) {
                names.add(f.getText());
            }
        }

        logic.setPlayers(names);
        JOptionPane.showMessageDialog(this, logic.getStartMessage());
    }
}
