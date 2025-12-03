package it.unibo.uniboparty.controller.player.impl;

import java.util.Objects;

import it.unibo.uniboparty.controller.player.api.GameplayController;
import it.unibo.uniboparty.model.player.api.PlayerManager;
import it.unibo.uniboparty.model.player.api.TurnResult;
import it.unibo.uniboparty.utilities.MinigameId;

/**
 * Controller implementation for handling minigame launch mechanics.
 *
 * <p>
 * Delegates the logic of a player's turn to {@link PlayerManager} and
 * starts the appropriate minigame if the landed cell requires it.
 * </p>
 */
public final class GameplayControllerImpl implements GameplayController {

    private final PlayerManager playerManager;

    /**
     * Constructs a GameplayControllerImpl.
     *
     * @param playerManager the player manager
     */
    public GameplayControllerImpl(final PlayerManager playerManager) {
        this.playerManager = Objects.requireNonNull(playerManager, "PlayerManager must not be null");
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
        final TurnResult result = playerManager.playTurn(steps);

        // Avvia il minigioco se la cella lo richiede
        if (result.minigameToStart() != null) {
            startMinigame(result.minigameToStart());
        }
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
            case GAME_1 -> { /* TODO insert game initializer */ }
            case GAME_2 -> { /* TODO insert game initializer */ }
            case GAME_3 -> { /* TODO insert game initializer */ }
            case GAME_4 -> { /* TODO insert game initializer */ }
            case GAME_5 -> { /* TODO insert game initializer */ }
            case GAME_6 -> { /* TODO insert game initializer */ }
            case GAME_7 -> { /* TODO insert game initializer */ }
            case GAME_8 -> { /* TODO insert game initializer */ }
        }

        // TODO add score to player after turn ends
        // TODO start new round after turn ends
    }
}
