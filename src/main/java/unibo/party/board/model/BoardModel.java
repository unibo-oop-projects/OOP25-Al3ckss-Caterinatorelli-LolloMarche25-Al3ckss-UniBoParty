package unibo.party.board.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import unibo.party.minigames.MinigameId;

/**
 * Model representing the main game board as a list of {@link CellModel} objects.
 * Each cell may be a normal cell or one that triggers a minigame.
 */
public final class BoardModel {

    private final List<CellModel> cells;

    /**
     * Creates the default configuration of the board.
     * The board is internally represented as an ordered list of cells.
     */
    public BoardModel() {
        this.cells = new ArrayList<>();

        this.cells.add(new CellModel(CellType.NORMAL, null));
        this.cells.add(new CellModel(CellType.MINIGAME, MinigameId.GAME_1));
        this.cells.add(new CellModel(CellType.NORMAL, null));
        this.cells.add(new CellModel(CellType.MINIGAME, MinigameId.GAME_2));
        this.cells.add(new CellModel(CellType.NORMAL, null));
        this.cells.add(new CellModel(CellType.MINIGAME, MinigameId.GAME_3));
        this.cells.add(new CellModel(CellType.NORMAL, null));
        this.cells.add(new CellModel(CellType.MINIGAME, MinigameId.GAME_4));
        this.cells.add(new CellModel(CellType.NORMAL, null));
        this.cells.add(new CellModel(CellType.MINIGAME, MinigameId.GAME_5));
        this.cells.add(new CellModel(CellType.NORMAL, null));
        this.cells.add(new CellModel(CellType.MINIGAME, MinigameId.GAME_6));
        this.cells.add(new CellModel(CellType.NORMAL, null));
        this.cells.add(new CellModel(CellType.MINIGAME, MinigameId.GAME_7));
        this.cells.add(new CellModel(CellType.NORMAL, null));
        this.cells.add(new CellModel(CellType.MINIGAME, MinigameId.GAME_8));
    }

    /**
     * Returns the number of cells of the board.
     *
     * @return the size of the board
     */
    public int getSize() {
        return this.cells.size();
    }

    /**
     * Returns the cell at the given index.
     *
     * @param index the position of the desired cell
     * @return the {@link CellModel} at that position
     */
    public CellModel getCellAt(final int index) {
        return this.cells.get(index);
    }

    /**
     * Returns an unmodifiable view of the list of cells.
     *
     * @return an unmodifiable list of cells
     */
    public List<CellModel> getCells() {
        return Collections.unmodifiableList(this.cells);
    }
}
