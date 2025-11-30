package it.unibo.uniboparty.controller.player.impl;

import it.unibo.uniboparty.controller.board.api.BoardController;
import it.unibo.uniboparty.controller.player.api.GameplayController;
import it.unibo.uniboparty.model.player.api.PlayerManager;
import it.unibo.uniboparty.utilities.MinigameId;
import it.unibo.uniboparty.view.board.api.BoardView;

/**
 * Controller implementation for handling minigame launch mechanics.
 *
 * <p>
 * Manages the players' movement after a dice throw, starts
 * the minigame depending of the position
 * </p>
 */
public class GameplayControllerImpl implements GameplayController {

    private final BoardView boardView;
    private final BoardController boardController;
    private final PlayerManager playerManager;

    private PlayerManager currentPlayer;

    /**
     * @param boardView the board
     * @param playerManager the information about the players
     */
    public GameplayControllerImpl(final BoardView boardView, final PlayerManager playerManager) {

        this.boardView = boardView;
        this.playerManager = playerManager;
        this.boardController = boardView.getController(); 
    }

    @Override
    public final void onDiceRolled(final int steps) {

        //TODO add player handling for multiple players (currentPlayerIndex on PlayerManager)
        
        final int newPos = playerManager.moveCurrentPlayer(steps, boardController.getBoardSize());
        
        boardView.setPlayerPosition(newPos);

        final MinigameId mg = boardController.onPlayerLanded(newPos);

        if (mg != null) {
            startMinigame(mg, currentPlayer);
        }

        playerManager.nextPlayer();

    }

@Override
public final void startMinigame(final MinigameId id, final PlayerManager currentPlayer) {

    switch (id) {
        case GAME_1 -> {
            //TODO insert game initializer
        }
        case GAME_2 -> {
            //TODO insert game initializer
        }
        case GAME_3 -> {
            //TODO insert game initializer
        }
        case GAME_4 -> {
            //TODO insert game initializer
        }
        case GAME_5 -> {
            //TODO insert game initializer
        }
        case GAME_6 -> {
            //TODO insert game initializer
        }
        case GAME_7 -> {
            //TODO insert game initializer
        }
        case GAME_8 -> {
            //TODO insert game initializer
        }
    }

    //TODO add score to player after turn ends

    //TODO start new round after turn ends
}

}
