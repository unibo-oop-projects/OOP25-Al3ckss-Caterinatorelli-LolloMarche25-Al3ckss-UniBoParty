package it.unibo.UniBoParty.model.minigames.mazegame.api;

import it.unibo.UniBoParty.utilities.CellType;

public interface MazeGenerator {
    /**
     * Generates a maze
     * @return a maze as a 2D array of CellType
     */
    CellType[][] generate();

    
}
