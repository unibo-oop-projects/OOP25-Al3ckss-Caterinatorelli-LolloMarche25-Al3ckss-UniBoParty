package it.unibo.uniboparty.controller.player.impl;

import java.util.List;
import java.util.Objects;

import it.unibo.uniboparty.controller.player.api.GameplayController;
import it.unibo.uniboparty.model.player.api.Player;
import it.unibo.uniboparty.model.player.api.PlayerManager;
import it.unibo.uniboparty.model.player.api.TurnResult;
import it.unibo.uniboparty.model.player.impl.PlayerManagerImpl;
import it.unibo.uniboparty.view.board.api.BoardView;
import it.unibo.uniboparty.controller.board.api.BoardController;

/**
 * Controller implementation for handling the game flow.
 *
 * <p>
 * It converts player names into {@link Player} instances,
 * initializes the {@link PlayerManager}, and delegates turn logic
 * and minigame launching to it.
 * </p>
 */
public final class GameplayControllerImpl implements GameplayController {

    private final PlayerManager playerManager;

    /**
     * Constructs a GameplayControllerImpl by creating its PlayerManager.
     *
     * <p>
     * This constructor takes the list of player names created in the start menu,
     * converts them into {@link Player} objects, and initializes the
     * {@link PlayerManagerImpl}.
     * </p>
     *
     * @param playerNames the list of player names
     * @param boardView the board view used to update player positions
     * @param boardController the board controller used to read board information
     */
    public GameplayControllerImpl(
            final List<String> playerNames,
            final BoardView boardView,
            final BoardController boardController
    ) {
        Objects.requireNonNull(playerNames, "playerNames must not be null");
        Objects.requireNonNull(boardView, "boardView must not be null");
        Objects.requireNonNull(boardController, "boardController must not be null");

        final List<Player> players = playerNames.stream()
                .map(Player::new)
                .toList();

        this.playerManager = new PlayerManagerImpl(players, boardView, boardController);
    }

    /**
     * Handles a dice roll by executing the current player's turn.
     *
     * <p>The turn logic (movement, special effects, updating the view)
     * is handled entirely by {@link PlayerManager#playTurn(int)}. This
     * method only reacts to the results, such as starting minigames.</p>
     *
     * @param steps the number of steps rolled on the dice
     */
    @Override
    public void onDiceRolled(final int steps) {
        final TurnResult result = this.playerManager.playTurn(steps);
        //TODO Start minigame if required
    }
    //TODO callback win/lose
    //TODO start new round after turn ends

}
