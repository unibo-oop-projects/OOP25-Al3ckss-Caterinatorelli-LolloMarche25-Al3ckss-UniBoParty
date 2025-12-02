package it.unibo.uniboparty.controller.player.impl;

import java.util.Objects;

import it.unibo.uniboparty.controller.board.api.BoardController;
import it.unibo.uniboparty.controller.player.api.GameplayController;
import it.unibo.uniboparty.model.player.api.PlayerManager;
import it.unibo.uniboparty.utilities.MinigameId;
import it.unibo.uniboparty.view.board.api.BoardView;

/**
 * Controller implementation for handling minigame launch mechanics.
 *
 * <p>
 * Manages the players' movement after a dice throw and starts
 * the appropriate minigame based on the cell reached.
 * </p>
 */
public final class GameplayControllerImpl implements GameplayController {

    private final BoardView boardView;
    private final BoardController boardController;
    private final PlayerManager playerManager;

    /**
     * @param boardView the board view
     * @param playerManager player manager
     */
    public GameplayControllerImpl(final BoardView boardView, final PlayerManager playerManager) {
        this.boardView = Objects.requireNonNull(boardView, "BoardView must not be null");
        this.playerManager = Objects.requireNonNull(playerManager, "PlayerManager must not be null");

        this.boardController = Objects.requireNonNull(
                boardView.getController(),
                "BoardController must not be null"
        );
    }

    @Override
    public void onDiceRolled(final int steps) {

        final int currentPlayer = playerManager.getCurrentPlayerIndex();
        final int newPos = playerManager.moveCurrentPlayer(steps, boardController.getBoardSize());

        // Update the view with the specific player index so multiple
        // players can be displayed concurrently.
        boardView.setPlayerPosition(currentPlayer);

        final MinigameId mg = boardController.onPlayerLanded(newPos);

        if (mg != null) {
            startMinigame(mg);
        }

        playerManager.nextPlayer();
    }

    /**
     * Starts a minigame based on the landing cell.
     *
     * @param id the minigame identifier
     */
    @edu.umd.cs.findbugs.annotations.SuppressFBWarnings(
            value = "UC_USELESS_VOID_METHOD",
            justification = "Method will be implemented by team members handling minigames"
    )
    @Override
    public void startMinigame(final MinigameId id) {

        switch (id) {
            case GAME_1 -> {
                // TODO insert game initializer
            }
            case GAME_2 -> {
                // TODO insert game initializer
            }
            case GAME_3 -> {
                // TODO insert game initializer
            }
            case GAME_4 -> {
                // TODO insert game initializer
            }
            case GAME_5 -> {
                // TODO insert game initializer
            }
            case GAME_6 -> {
                // TODO insert game initializer
            }
            case GAME_7 -> {
                // TODO insert game initializer
            }
            case GAME_8 -> {
                // TODO insert game initializer
            }
        }

        // TODO add score to player after turn ends
        // TODO start new round after turn ends
    }
}
