package it.unibo.uniboparty.utilities;

/**
 * Simple callback used by minigame intro frames to notify
 * the final result of the minigame to the main board.
 *
 * <p>
 * Convention:
 * <ul>
 *   <li>2 = game still in progress (no effect)</li>
 *   <li>1 = game won</li>
 *   <li>0 = game lost</li>
 * </ul>
 * </p>
 */
@FunctionalInterface
public interface MinigameResultCallback {

    /**
     * Called when the minigame ends and a result code is available.
     *
     * @param resultCode encoded result (2 = in progress, 1 = win, 0 = loss)
     */
    void onResult(int resultCode);
}
