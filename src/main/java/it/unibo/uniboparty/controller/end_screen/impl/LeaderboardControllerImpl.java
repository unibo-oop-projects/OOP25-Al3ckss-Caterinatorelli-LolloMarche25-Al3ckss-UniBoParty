package it.unibo.uniboparty.controller.end_screen.impl;

import it.unibo.uniboparty.view.startgamemenu.StartGameGui;
import it.unibo.uniboparty.controller.end_screen.api.LeaderboardController;
import it.unibo.uniboparty.model.end_screen.api.LeaderboardModel;
import it.unibo.uniboparty.model.end_screen.api.Player;
import it.unibo.uniboparty.model.end_screen.impl.LeaderboardModelImpl;
import it.unibo.uniboparty.view.end_screen.api.LeaderboardView;
import it.unibo.uniboparty.view.end_screen.impl.LeaderboardViewImpl;

import java.util.List;

/**
 * Concrete implementation of the {@link LeaderboardController} interface.
 *
 * <p>
 * This class serves as the controller for the end-game screen. It mediates
 * between the {@link LeaderboardModel} to retrieve rankings and the
 * {@link LeaderboardView} to display the podium.
 * </p>
 */
public final class LeaderboardControllerImpl implements LeaderboardController {

    private final LeaderboardModel model;
    private final LeaderboardView view;

    /**
     * Default constructor: uses dummy data in the model.
     */
    public LeaderboardControllerImpl() {
        this.model = new LeaderboardModelImpl();
        this.view = new LeaderboardViewImpl();
        initController();
    }

    /**
     * Creates a leaderboard using the real match players.
     *
     * @param players players with their final scores
     */
    public LeaderboardControllerImpl(final List<Player> players) {
        this.model = new LeaderboardModelImpl(players);
        this.view = new LeaderboardViewImpl();
        initController();
    }

    /**
     * Initializes the controller logic.
     */
    private void initController() {
        this.view.showPodium(this.model.getTopPlayers());

        this.view.addBackListener(e -> {
            this.view.close();
            new StartGameGui().setVisible(true);
        });
    }
}
