package it.unibo.uniboparty.model.board.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.unibo.uniboparty.model.board.CellModel;
import it.unibo.uniboparty.model.board.CellType;
import it.unibo.uniboparty.model.board.api.BoardModel;
import it.unibo.uniboparty.utilities.MinigameId;

/**
 * Default implementation of the BoardModel.
 * The board is represented as an ordered list of cells.
 */
public final class BoardModelImpl implements BoardModel {

    private final List<CellModel> cells;

    /**
     * Creates the default configuration of the board.
     */
    public BoardModelImpl() {
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

    @Override
    public int getSize() {
        return this.cells.size();
    }

    @Override
    public CellModel getCellAt(final int index) {
        return this.cells.get(index);
    }

    @Override
    public List<CellModel> getCells() {
        return Collections.unmodifiableList(this.cells);
    }
}
