package it.unibo.uniboparty.model.player.impl;

import java.util.List;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.uniboparty.controller.board.api.BoardController;
import it.unibo.uniboparty.model.board.CellType;
import it.unibo.uniboparty.model.player.api.Player;
import it.unibo.uniboparty.model.player.api.PlayerManager;
import it.unibo.uniboparty.model.player.api.TurnResult;
import it.unibo.uniboparty.utilities.MinigameId;
import it.unibo.uniboparty.view.board.api.BoardView;

/**
 * Default implementation of the PlayerManager.
 *
 * <p>
 * Manages multiple players, their positions on the board,
 * their scores, and the turn order.
 * </p>
 */
public final class PlayerManagerImpl implements PlayerManager {

    private static final String INVALID_PLAYER_INDEX = "Invalid player index: ";

    private final List<Player> players;
    private final int numberOfPlayers;
    private final int[] scores;
    private final BoardView boardView;
    private final BoardController boardController;

    private int currentPlayerIndex;

    /**
     * Creates a new PlayerManager for the given players.
     *
     * <p>
     * Note: BoardView and BoardController are stored as interface references,
     * not concrete implementations. These are shared collaborators in the MVC pattern
     * that must maintain synchronized state throughout the game lifecycle.
     * The interfaces already provide a limited, controlled API preventing misuse.
     * </p>
     *
     * @param players the list of players in the match
     * @param boardView the board view for updating player positions (must not be null)
     * @param boardController the board controller for reading cell types (must not be null)
     * @throws IllegalArgumentException if players is null or empty, or if boardView or boardController is null
     */
    @SuppressFBWarnings(
        value = "EI_EXPOSE_REP2",
        justification = "Interfaces used as shared collaborators (MVC pattern). "
            + "Limited API prevents misuse. Defensive copying not applicable for stateful collaborators."
    )
    public PlayerManagerImpl(final List<Player> players,
                             final BoardView boardView,
                             final BoardController boardController) {
        if (players == null || players.isEmpty()) {
            throw new IllegalArgumentException("players must not be null or empty");
        }
        if (boardView == null) {
            throw new IllegalArgumentException("boardView must not be null");
        }
        if (boardController == null) {
            throw new IllegalArgumentException("boardController must not be null");
        }
        this.players = List.copyOf(players);
        this.numberOfPlayers = this.players.size();
        this.scores = new int[this.numberOfPlayers];
        this.currentPlayerIndex = 0;
        // Intentionally storing mutable references - these are shared collaborators
        this.boardView = boardView;
        this.boardController = boardController;
    }

    @Override
    public int getNumberOfPlayers() {
        return this.numberOfPlayers;
    }

    @Override
    public int getCurrentPlayerIndex() {
        return this.currentPlayerIndex;
    }

    @Override
    public Player getCurrentPlayer() {
        return this.players.get(this.currentPlayerIndex);
    }

    @Override
    public List<Player> getPlayers() {
        return this.players;
    }

    @Override
    public int getCurrentPlayerPosition() {
        if (this.currentPlayerIndex < 0 || this.currentPlayerIndex >= this.numberOfPlayers) {
            throw new IllegalStateException("Player " + this.currentPlayerIndex + " is out of range");
        }
        return this.players.get(this.currentPlayerIndex).getPosition();
    }

    @Override
    public int getPlayerPosition(final int playerIndex) {
        if (playerIndex < 0 || playerIndex >= this.numberOfPlayers) {
            throw new IllegalArgumentException(INVALID_PLAYER_INDEX + playerIndex);
        }
        return this.players.get(playerIndex).getPosition();
    }

    @Override
    public void nextPlayer() {
        this.currentPlayerIndex = (this.currentPlayerIndex + 1) % this.numberOfPlayers;
    }

    @Override
    public void addScore(final int playerIndex, final int amount) {
        if (playerIndex < 0 || playerIndex >= this.numberOfPlayers) {
            throw new IllegalArgumentException(INVALID_PLAYER_INDEX + playerIndex);
        }
        this.scores[playerIndex] += amount;
    }

    @Override
    public int getScore(final int playerIndex) {
        return this.scores[playerIndex];
    }

    @Override
    public TurnResult playTurn(final int diceRoll) {
        final int playerIndex = this.currentPlayerIndex;
        final Player current = this.players.get(playerIndex);

        int newPos = current.getPosition() + diceRoll;
        final int boardSize = this.boardController.getBoardSize();

        if (newPos >= boardSize) {
            newPos = boardSize - 1;
        }

        current.setPosition(newPos);
        this.boardView.setPlayerPosition(newPos);

        final CellType cellType = this.boardController.getCellTypeAt(newPos);
        MinigameId minigameToStart = null;

        switch (cellType) {
            case BACK_2 -> {
                newPos = Math.max(0, newPos - 2);
                current.setPosition(newPos);
                this.boardView.setPlayerPosition(newPos);
            }

            case SWAP -> {
                if (this.numberOfPlayers > 1) {
                    int otherIndex;
                    do {
                        otherIndex = (int) (Math.random() * this.numberOfPlayers);
                    } while (otherIndex == playerIndex);

                    final Player other = this.players.get(otherIndex);
                    final int tempPos = other.getPosition();
                    other.setPosition(current.getPosition());
                    current.setPosition(tempPos);

                    this.boardView.setPlayerPosition(current.getPosition());
                }
}
            case MINIGAME -> {
                minigameToStart = this.boardController.getMinigameAt(newPos);
            }
            default -> {
                // Normal cell, no additional action
            }
        }

        final boolean gameEnded = newPos == boardSize - 1;

        this.nextPlayer();

        return new TurnResult(newPos, minigameToStart, gameEnded);
    }

    @Override
    public void applyMinigameResult(final int playerIndex, final MinigameId minigameId, final int resultCode) {
        if (resultCode == 2) {
            // Game still in progress, no action
            return;
        }

        if (playerIndex < 0 || playerIndex >= this.numberOfPlayers) {
            throw new IllegalArgumentException(INVALID_PLAYER_INDEX + playerIndex);
        }

        final Player player = this.players.get(playerIndex);
        final int movement = (resultCode == 1) ? 1 : -1;
        int newPos = player.getPosition() + movement;
        final int boardSize = this.boardController.getBoardSize();

        // Ensure position stays within bounds
        if (newPos < 0) {
            newPos = 0;
        } else if (newPos >= boardSize) {
            newPos = boardSize - 1;
        }

        player.setPosition(newPos);
        this.boardView.setPlayerPosition(newPos);
    }
}
