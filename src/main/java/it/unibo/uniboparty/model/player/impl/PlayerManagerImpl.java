package it.unibo.uniboparty.model.player.impl;

import it.unibo.uniboparty.controller.board.api.BoardController;
import it.unibo.uniboparty.model.board.CellType;
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

    private final int numberOfPlayers;
    private final int[] positions;
    private final int[] scores;
    private final BoardViewDelegate boardViewDelegate;
    private final BoardControllerDelegate boardControllerDelegate;

    private int currentPlayerIndex;

    /**
     * Creates a new PlayerManager with the given number of players.
     *
     * @param numberOfPlayers the number of players in the match
     * @param boardView the board view for updating player positions
     * @param boardController the board controller for reading cell types
     */
    public PlayerManagerImpl(final int numberOfPlayers, final BoardView boardView, final BoardController boardController) {
        if (numberOfPlayers <= 0) {
            throw new IllegalArgumentException("numberOfPlayers must be positive");
        }
        this.numberOfPlayers = numberOfPlayers;
        this.positions = new int[numberOfPlayers];
        this.scores = new int[numberOfPlayers];
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
    public int getCurrentPlayerPosition() {
        if (this.currentPlayerIndex < 0 || this.currentPlayerIndex >= this.numberOfPlayers) {
            throw new IllegalStateException("Current player index out of range: " + this.currentPlayerIndex);
        }
        return this.positions[this.currentPlayerIndex];
    }

    @Override
    public int getPlayerPosition(final int playerIndex) {
        if (playerIndex < 0 || playerIndex >= this.numberOfPlayers) {
            throw new IllegalArgumentException("Invalid player index: " + playerIndex);
        }
        return this.positions[playerIndex];
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
        final int oldPos = this.positions[playerIndex];
        int newPos = oldPos + diceRoll;

        final int boardSize = this.boardControllerDelegate.getBoardSize();
        if (newPos >= boardSize) {
            newPos = boardSize - 1;
        }

        this.positions[playerIndex] = newPos;
        this.boardViewDelegate.setPlayerPosition(playerIndex);

        final CellType cellType = this.boardControllerDelegate.getCellTypeAt(newPos);
        MinigameId minigameToStart = null;

        switch (cellType) {
            case BACK_2 -> {
                newPos = Math.max(0, newPos - 2);
                this.positions[playerIndex] = newPos;
                this.boardViewDelegate.setPlayerPosition(playerIndex);
            }
            case SWAP -> {
                if (this.numberOfPlayers > 1) {
                    int otherIndex;
                    do {
                        otherIndex = (int) (Math.random() * this.numberOfPlayers);
                    } while (otherIndex == playerIndex);

                    final int tempPos = this.positions[otherIndex];
                    this.positions[otherIndex] = this.positions[playerIndex];
                    this.positions[playerIndex] = tempPos;

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
