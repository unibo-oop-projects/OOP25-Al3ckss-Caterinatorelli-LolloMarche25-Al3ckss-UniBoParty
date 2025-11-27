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
public final class StartGameGui extends JFrame {
    private static final long serialVersionUID = 1L; 

    private static final String FONT_NAME = "Comic Sans MS";
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;
    private static final int LOGO_WIDTH = 500;
    private static final int LOGO_HEIGHT = 120;
    private static final int LOGO_PANEL_HEIGHT = 150;
    private static final int PLAYER_GRID_ROWS = 5;
    private static final int GRID_GAP = 15;
    private static final int BORDER_PADDING = 40;
    private static final int SIDE_PADDING = 150;
    private static final int BUTTON_WIDTH = 220;
    private static final int BUTTON_HEIGHT = 60;
    private static final int PLAYER_FIELD_FONT_SIZE = 20; 
    private static final Color FIELD_BG_COLOR = new Color(255, 255, 255, 220);
    private static final Color START_GAME_COLOR = new Color(144, 238, 144);
    private static final Color EXIT_GAME_COLOR = new Color(255, 99, 71);
    private static final Font LABEL_FONT = new Font(FONT_NAME, Font.BOLD, 22);
    private static final Font FIELD_FONT = new Font(FONT_NAME, Font.PLAIN, PLAYER_FIELD_FONT_SIZE); 
    private final JButton startButton;
    private final JButton exitButton;
    private final List<JTextField> playerFields;
    private final transient LogicStartGame logic;

    /**
     * Constructs and initializes the game start menu GUI window.
     */
    public StartGameGui() {
        this.logic = new LogicStartGameImpl();

        setTitle("Unibo Party");
        setDefaultCloseOperation(EXIT_ON_CLOSE); 
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocationRelativeTo(null);

        final Image background = new ImageIcon(
        "/Users/caterinatorelli/Desktop/uniboparty/src/main/resources/background.png").getImage();
        final Image logo = new ImageIcon("/Users/caterinatorelli/Desktop/uniboparty/src/main/resources/logo.png").getImage();

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
                final int width = LOGO_WIDTH;
                final int height = LOGO_HEIGHT;
                final int x = (getWidth() - width) / 2;
                g.drawImage(logo, x, 10, width, height, this);
            }
        };
        logoPanel.setOpaque(false);
        logoPanel.setPreferredSize(new Dimension(WINDOW_WIDTH, LOGO_PANEL_HEIGHT));
        mainPanel.add(logoPanel, BorderLayout.NORTH);

        final JPanel playerPanel = new JPanel(new GridLayout(PLAYER_GRID_ROWS, 2, GRID_GAP, GRID_GAP));
        playerPanel.setOpaque(false);
        playerPanel.setBorder(BorderFactory.createEmptyBorder(BORDER_PADDING, SIDE_PADDING, BORDER_PADDING, SIDE_PADDING));

        playerFields = new ArrayList<>();
        for (int i = 1; i <= PLAYER_GRID_ROWS; i++) {
            final JLabel label = new JLabel("Giocatore " + i + ": ");
            label.setFont(LABEL_FONT);
            label.setForeground(Color.BLACK);

            final JTextField field = new JTextField();
            field.setFont(FIELD_FONT);
            field.setBackground(FIELD_BG_COLOR);
            field.setBorder(BorderFactory.createLineBorder(Color.PINK, 2, true));

            playerFields.add(field);
            playerPanel.add(label);
            playerPanel.add(field);
        }
        mainPanel.add(playerPanel, BorderLayout.CENTER);

        final JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);

        startButton = createButton(" Avvia Gioco ", START_GAME_COLOR);
        exitButton = createButton(" Esci", EXIT_GAME_COLOR);

        buttonPanel.add(startButton);
        buttonPanel.add(exitButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        startButton.addActionListener(this::onStartPressed);
        exitButton.addActionListener(e -> this.dispose());
    }

    private JButton createButton(final String text, final Color color) {
        final JButton button = new JButton(text);
        button.setBackground(color);
        button.setFont(new Font(FONT_NAME, Font.BOLD, PLAYER_FIELD_FONT_SIZE)); 
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3, true));
        button.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        return button;
    }

    @SuppressWarnings("unused")
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
