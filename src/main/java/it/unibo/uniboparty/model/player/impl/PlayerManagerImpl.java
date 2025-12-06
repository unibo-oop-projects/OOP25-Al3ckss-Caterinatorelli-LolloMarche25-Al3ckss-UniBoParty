package it.unibo.uniboparty.model.player.impl;

import java.util.List;

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

    private final List<Player> players;
    private final int numberOfPlayers;
    private final int[] scores;
    private final BoardViewDelegate boardViewDelegate;
    private final BoardControllerDelegate boardControllerDelegate;

    private int currentPlayerIndex;

    /**
     * Creates a new PlayerManager for the given players.
     *
     * @param players the list of players in the match
     * @param boardView the board view for updating player positions
     * @param boardController the board controller for reading cell types
     */
    public PlayerManagerImpl(final List<Player> players,
                             final BoardView boardView,
                             final BoardController boardController) {
        if (players == null || players.isEmpty()) {
            throw new IllegalArgumentException("players must not be null or empty");
        }
        this.players = List.copyOf(players);
        this.numberOfPlayers = this.players.size();
        this.scores = new int[this.numberOfPlayers];
        this.currentPlayerIndex = 0;
        this.boardViewDelegate = new BoardViewDelegate(boardView);
        this.boardControllerDelegate = new BoardControllerDelegate(boardController);
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
            throw new IllegalStateException("Current player index out of range: " + this.currentPlayerIndex);
        }
        return this.players.get(this.currentPlayerIndex).getPosition();
    }

    @Override
    public int getPlayerPosition(final int playerIndex) {
        if (playerIndex < 0 || playerIndex >= this.numberOfPlayers) {
            throw new IllegalArgumentException("Invalid player index: " + playerIndex);
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
            throw new IllegalArgumentException("Invalid player index: " + playerIndex);
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
        final int boardSize = this.boardControllerDelegate.getBoardSize();

        if (newPos >= boardSize) {
            newPos = boardSize - 1;
        }

        current.setPosition(newPos);
        this.boardViewDelegate.setPlayerPosition(playerIndex);

        final CellType cellType = this.boardControllerDelegate.getCellTypeAt(newPos);
        MinigameId minigameToStart = null;

        switch (cellType) {
            case BACK_2 -> {
                newPos = Math.max(0, newPos - 2);
                current.setPosition(newPos);
                this.boardViewDelegate.setPlayerPosition(playerIndex);
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

                    this.boardViewDelegate.setPlayerPosition(playerIndex);
                    this.boardViewDelegate.setPlayerPosition(otherIndex);
                }
            }
            case MINIGAME -> {
                minigameToStart = this.boardControllerDelegate.getMinigameAt(newPos);
            }
            default -> {
                // Normal cell: no additional action needed
            }
        }

        final boolean gameEnded = newPos == boardSize - 1;

        this.nextPlayer();

        return new TurnResult(newPos, minigameToStart, gameEnded);
    }

    /**
     * Private delegate for BoardView to prevent exposure of mutable references.
     */
    private static final class BoardViewDelegate {
        private final BoardView delegate;

        BoardViewDelegate(final BoardView delegate) {
            this.delegate = delegate;
        }

        void setPlayerPosition(final int playerIndex) {
            this.delegate.setPlayerPosition(playerIndex);
        }
    }

    /**
     * Private delegate for BoardController to prevent exposure of mutable references.
     */
    private static final class BoardControllerDelegate {
        private final BoardController delegate;

        BoardControllerDelegate(final BoardController delegate) {
            this.delegate = delegate;
        }

        int getBoardSize() {
            return this.delegate.getBoardSize();
        }

        CellType getCellTypeAt(final int position) {
            return this.delegate.getCellTypeAt(position);
        }

        MinigameId getMinigameAt(final int position) {
            return this.delegate.getMinigameAt(position);
        }
    }
}
