package unibo.party.board.model;

/**
 * Types of cells that can appear on the main board.
 * <ul>
 *   <li>{@link #NORMAL} – a regular cell with no special effect.</li>
 *   <li>{@link #MINIGAME} – a cell that triggers a minigame.</li>
 * </ul>
 */
public enum CellType {
    /**
     * A normal cell with no minigame.
     */
    NORMAL,

    /**
     * A cell that starts a minigame.
     */
    MINIGAME
}
