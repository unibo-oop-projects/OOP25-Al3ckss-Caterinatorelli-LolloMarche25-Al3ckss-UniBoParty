package it.unibo.uniboparty.controller.player.impl;

import java.util.List;
import java.util.Objects;

import it.unibo.uniboparty.controller.player.api.GameplayController;
import it.unibo.uniboparty.controller.board.api.BoardController;
import it.unibo.uniboparty.model.player.api.Player;
import it.unibo.uniboparty.model.player.api.PlayerManager;
import it.unibo.uniboparty.model.player.api.TurnResult;
import it.unibo.uniboparty.model.player.impl.PlayerManagerImpl;
import it.unibo.uniboparty.utilities.MinigameId;
import it.unibo.uniboparty.view.board.api.BoardView;

/**
 * Implementation of the GameplayController.
 */
public final class GameplayControllerImpl implements GameplayController {

    private final PlayerManager playerManager;
    private TurnResult lastTurnResult;

    /**
     * Creates a new GameplayControllerImpl.
     *
     * @param playerNames the names of players coming from the start menu
     * @param boardView the board view to update player positions
     * @param boardController the board controller for board information
     */
    public GameplayControllerImpl(
            final List<String> playerNames,
            final BoardView boardView,
            final BoardController boardController
    ) {
        Objects.requireNonNull(playerNames);
        Objects.requireNonNull(boardView);
        Objects.requireNonNull(boardController);

        final List<Player> players = playerNames.stream()
                .map(Player::new)
                .toList();

        this.playerManager = new PlayerManagerImpl(players, boardView, boardController);
    }

    @Override
    public void onDiceRolled(final int steps) {
        this.lastTurnResult = this.playerManager.playTurn(steps);
    }

    @Override
    public void onMinigameFinished(final int result, final MinigameId id) {
        if (result == 2) {
            return;
        }

        final int movement = (result == 1) ? 1 : -1;
        this.lastTurnResult = this.playerManager.playTurn(movement);
    }

    /**
     * Gets the last turn result.
     *
     * @return the last turn result, or null if no turn has been played yet
     */
    public TurnResult getLastTurnResult() {
        return this.lastTurnResult;
    }

    /**
     * Checks if the game has ended.
     *
     * @return true if the game has ended, false otherwise
     */
    public boolean isGameEnded() {
        return this.lastTurnResult != null && this.lastTurnResult.gameEnded();
    }
}
