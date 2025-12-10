package it.unibo.uniboparty.view.board.impl;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import it.unibo.uniboparty.model.dice.api.DiceModel;
import it.unibo.uniboparty.model.dice.impl.DiceModelImpl;
import it.unibo.uniboparty.model.player.api.Player;
import it.unibo.uniboparty.model.player.api.PlayerManager;
import it.unibo.uniboparty.model.player.api.TurnResult;
import it.unibo.uniboparty.model.player.impl.PlayerManagerImpl;
import it.unibo.uniboparty.utilities.MinigameId;
import it.unibo.uniboparty.view.minigames.whacamole.impl.WhacAMoleIntroFrame;
import it.unibo.uniboparty.view.minigames.memory.impl.MemoryIntroFrame;
import it.unibo.uniboparty.view.minigames.dinosaurgame.impl.DinoGameIntroFrame;
import it.unibo.uniboparty.view.minigames.hangman.impl.HangManIntroFrame;
import it.unibo.uniboparty.view.minigames.mazegame.impl.MazeIntroFrame;
import it.unibo.uniboparty.view.minigames.sudoku.impl.SudokuIntroFrame;
import it.unibo.uniboparty.view.minigames.tetris.impl.TetrisIntroFrame;
import it.unibo.uniboparty.view.minigames.typeracergame.impl.TyperacerGameIntroFrame;

/**
 * Main game window that shows:
 * <ul>
 *   <li>the board,</li>
 *   <li>a button to roll the dice,</li>
 *   <li>the result of the last dice roll.</li>
 * </ul>
 *
 * <p>
 * This class connects the board view, the dice model and
 * the {@link PlayerManager}. It delegates:
 * <ul>
 *   <li>movement rules to {@link PlayerManager},</li>
 *   <li>board rendering to {@link BoardViewImpl},</li>
 *   <li>dice generation to {@link DiceModel}.</li>
 * </ul>
 * It also coordinates minigames: when a player lands on a
 * MINIGAME cell, this frame opens the corresponding intro
 * window and applies the result to the {@link PlayerManager}.
 * </p>
 */
public final class MainBoardFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    private final BoardViewImpl boardView;
    private final transient DiceModel diceModel;
    private final transient PlayerManager playerManager;

    private final JLabel diceResultLabel;
    private final JButton rollButton;

    /**
     * Index of the player who triggered the last minigame.
     */
    private int lastPlayerIndex;

    /**
     * Identifier of the last started minigame.
     */
    private MinigameId lastMinigameId;

    /**
     * Creates the main board window using the given list of player names.
     *
     * <p>
     * For each name a {@link Player} instance is created.
     * The {@link PlayerManagerImpl} is responsible for tracking
     * player positions and applying the rules of the board.
     * </p>
     *
     * @param playerNames names of the players participating in the match
     */
    public MainBoardFrame(final List<String> playerNames) {
        super("UniBoParty - Board");

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());

        this.boardView = new BoardViewImpl();
        this.diceModel = new DiceModelImpl();

        final List<Player> players = new ArrayList<>();
        for (final String name : playerNames) {
            players.add(new Player(name));
        }

        this.playerManager = new PlayerManagerImpl(
            players,
            this.boardView,
            this.boardView.getController()
        );

        this.lastPlayerIndex = 0;
        this.lastMinigameId = null;

        this.add(this.boardView, BorderLayout.CENTER);

        final JPanel bottomPanel = new JPanel();
        this.diceResultLabel = new JLabel("Roll the dice to start");
        this.rollButton = new JButton("Roll dice");

        bottomPanel.add(this.diceResultLabel);
        bottomPanel.add(this.rollButton);
        this.add(bottomPanel, BorderLayout.SOUTH);

        this.rollButton.addActionListener(e -> handleRoll());

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    /**
     * Handles a dice roll:
     * <ol>
     *   <li>rolls the dice via the {@link DiceModel},</li>
     *   <li>asks the {@link PlayerManager} to play the turn,</li>
     *   <li>eventually launches a minigame,</li>
     *   <li>checks if the game has ended.</li>
     * </ol>
     */
    private void handleRoll() {
        this.diceModel.roll();
        final int diceRoll = this.diceModel.getTotal();
        this.diceResultLabel.setText("You rolled: " + diceRoll);

        final TurnResult result = this.playerManager.playTurn(diceRoll);

        final int numPlayers = this.playerManager.getNumberOfPlayers();
        final int currentIndexAfterTurn = this.playerManager.getCurrentPlayerIndex();
        this.lastPlayerIndex = (currentIndexAfterTurn - 1 + numPlayers) % numPlayers;

        this.lastMinigameId = result.minigameToStart();

        if (this.lastMinigameId != null) {
            launchMinigame(this.lastMinigameId);
        }

        if (result.gameEnded()) {
            this.rollButton.setEnabled(false);
            showLeaderboard();
        }
    }

    /**
     * Opens the intro window for the given minigame.
     *
     * @param minigameId identifier of the minigame to start
     */
    private void launchMinigame(final MinigameId minigameId) {
        switch (minigameId) {
        case GAME_1:
            new WhacAMoleIntroFrame(this::handleMinigameResult);
            break;

        case GAME_2:
            new SudokuIntroFrame();
            break;

        case GAME_3:
            new HangManIntroFrame();
            break;

        case GAME_4:
            new MazeIntroFrame();
            break;

        case GAME_5:
            new TetrisIntroFrame();
            break;

        case GAME_6:
            new TyperacerGameIntroFrame();
            break;

        case GAME_7:
            new DinoGameIntroFrame();
            break;

        case GAME_8:
            new MemoryIntroFrame(this::handleMinigameResult);
            break;
        }
    }

    /**
     * Applies the result of the last minigame to the {@link PlayerManager}.
     *
     * <p>
     * This method is called by the minigame intro frame through a callback:
     * <ul>
     *   <li>2 = game still in progress → no movement,</li>
     *   <li>1 = win  → player moves forward (e.g. +1),</li>
     *   <li>0 = loss → player moves backward (e.g. -1).</li>
     * </ul>
     * The logic of how much to move the player is implemented inside
     * {@link PlayerManager#applyMinigameResult(int, MinigameId, int)}.
     * </p>
     *
     * @param resultCode encoded result of the minigame
     */
    private void handleMinigameResult(final int resultCode) {
        if (this.lastMinigameId == null) {
            return;
        }

        this.playerManager.applyMinigameResult(
            this.lastPlayerIndex,
            this.lastMinigameId,
            resultCode
        );

        final int boardSize = this.boardView.getController().getBoardSize();
        final int pos = this.playerManager.getPlayerPosition(this.lastPlayerIndex);
        if (pos == boardSize - 1) {
            this.rollButton.setEnabled(false);
            showLeaderboard();
        } else {
            this.rollButton.setEnabled(true);
        }
    }

    /**
     * Shows a simple final summary of the players positions.
     *
     * <p>
     * This is a temporary implementation used before the full
     * leaderboard screen is integrated. It just shows a dialog
     * with the final position of each player on the board.
     * </p>
     */
    private void showLeaderboard() {
        final int numPlayers = this.playerManager.getNumberOfPlayers();
        final List<Player> players = this.playerManager.getPlayers();

        final StringBuilder msg = new StringBuilder("Final positions:\n");

        for (int i = 0; i < numPlayers; i++) {
            final Player p = players.get(i);
            final int pos = this.playerManager.getPlayerPosition(i);
            msg.append("- ")
                .append(p.getName())
                .append(" at cell ")
                .append(pos + 1)  // +1 se le celle logiche partono da 1
                .append(System.lineSeparator());
        }

        JOptionPane.showMessageDialog(
            this,
            msg.toString(),
            "Game over",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
}
