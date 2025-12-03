package it.unibo.uniboparty.controller.end_screen.impl;

import it.unibo.uniboparty.application.GameLauncher;
import it.unibo.uniboparty.controller.end_screen.api.LeaderboardController;
import it.unibo.uniboparty.model.end_screen.api.LeaderboardModel;
import it.unibo.uniboparty.model.end_screen.impl.LeaderboardModelImpl;
import it.unibo.uniboparty.view.end_screen.api.LeaderboardView;
import it.unibo.uniboparty.view.end_screen.impl.LeaderboardViewImpl;

/**
 * Concrete implementation of the {@link LeaderboardController} interface.
 *
 * <p>
 * This class serves as the controller for the end-game screen. It mediates
 * between the {@link LeaderboardModel} to retrieve rankings and the
 * {@link LeaderboardView} to display the podium. It also manages the application
 * flow when the user decides to exit the leaderboard.
 */
public class LeaderboardControllerImpl implements LeaderboardController {

    private final LeaderboardModel model;
    private final LeaderboardView view;

    /**
     * Constructs a new {@code LeaderboardControllerImpl}.
     *
     * <p>
     * This constructor instantiates the concrete {@link LeaderboardModelImpl} and
     * {@link LeaderboardViewImpl} internally. This approach ensures strict MVC
     * encapsulation and satisfies static analysis requirements (e.g., SpotBugs).
     */
    public LeaderboardControllerImpl() {
        this.model = new LeaderboardModelImpl();
        this.view = new LeaderboardViewImpl();

        initController();
    }

    /**
     * Initializes the controller logic.
     *
     * <p>
     * This method retrieves the top players from the model to populate the podium view.
     * Additionally, it registers a listener for the "Back" action, which handles closing
     * the current view and restarting the application via {@link GameLauncher}.
     */
    private void initController() {
        view.showPodium(model.getTopPlayers());

        view.addBackListener(e -> {
            view.close();
            GameLauncher.main(new String[]{});
        });
    }
}
