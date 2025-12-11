package it.unibo.uniboparty.view.board.impl;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

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
import it.unibo.uniboparty.controller.end_screen.impl.LeaderboardControllerImpl;
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
     * Label that shows whose turn it is.
     */
    private final JLabel currentPlayerLabel;

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

        /*
         * Top panel: shows which player's turn it is.
         */
        final JPanel topPanel = new JPanel();
        this.currentPlayerLabel = new JLabel();
        updateCurrentPlayerLabel();
        topPanel.add(this.currentPlayerLabel);
        this.add(topPanel, BorderLayout.NORTH);

        /*
         * Center: the board.
         */
        this.add(this.boardView, BorderLayout.CENTER);

        /*
         * Bottom panel: dice result + roll button.
         */
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
     * Updates the label that shows whose turn it is.
     */
    private void updateCurrentPlayerLabel() {
        final Player current = this.playerManager.getCurrentPlayer();
        this.currentPlayerLabel.setText("È il turno di: " + current.getName()
                                + " (posizione: " + current.getPosition() + ")");
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
        this.diceResultLabel.setText(this.playerManager.getCurrentPlayer().getName() + " rolled: " + diceRoll);

        final TurnResult result = this.playerManager.playTurn(diceRoll);

        final int numPlayers = this.playerManager.getNumberOfPlayers();
        final int currentIndexAfterTurn = this.playerManager.getCurrentPlayerIndex();
        this.lastPlayerIndex = (currentIndexAfterTurn - 1 + numPlayers) % numPlayers;

        this.lastMinigameId = result.minigameToStart();

        if (this.lastMinigameId != null) {
            launchMinigame(this.lastMinigameId);
        } else if (!result.gameEnded()) {
            updateCurrentPlayerLabel();
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
        this.rollButton.setEnabled(false);

        switch (minigameId) {
        case GAME_1:
            new WhacAMoleIntroFrame(this::handleMinigameResult);
            break;

        case GAME_2:
            new SudokuIntroFrame(this::handleMinigameResult);
            break;

        case GAME_3:
            new HangManIntroFrame(this::handleMinigameResult);
            break;

        case GAME_4:
            new MazeIntroFrame(this::handleMinigameResult);
            break;

        case GAME_5:
            new TetrisIntroFrame(this::handleMinigameResult);
            break;

        case GAME_6:
            new TyperacerGameIntroFrame(this::handleMinigameResult);
            break;

        case GAME_7:
            new DinoGameIntroFrame(this::handleMinigameResult);
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
            updateCurrentPlayerLabel();
            this.rollButton.setEnabled(true);
        }
    }

    /**
     * Opens the final leaderboard window using the current players.
     *
     * <p>
     * For now the "score" is the final position on the board:
     * the player closer to the finish cell is ranked higher.
     * </p>
     */
    private void showLeaderboard() {
        this.dispose();
        final int numPlayers = this.playerManager.getNumberOfPlayers();
        final List<it.unibo.uniboparty.model.end_screen.api.Player> leaderboardPlayers
            = new ArrayList<>();

        final List<Player> players = this.playerManager.getPlayers();

        for (int i = 0; i < numPlayers; i++) {
            final Player p = players.get(i);
            final int score = this.playerManager.getPlayerPosition(i);
            leaderboardPlayers.add(
                new it.unibo.uniboparty.model.end_screen.api.Player(p.getName(), score)
            );
        }

        new LeaderboardControllerImpl(leaderboardPlayers);
    }
}
