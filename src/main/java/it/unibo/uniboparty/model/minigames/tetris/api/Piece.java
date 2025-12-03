package it.unibo.uniboparty.model.minigames.tetris.api;

import java.awt.Color;
import java.awt.Point;
import java.util.List;

/**
 * Represents a Tetris piece and provides methods for its properties and transformations.
 */
public interface Piece {

    /**
     * Returns the width of the piece.
     *
     * @return the width of the piece
     */
    int width();

    /**
     * Returns the height of the piece.
     *
     * @return the height of the piece
     */
    int height();

    /**
     * Returns the list of relative cell coordinates that make up the piece.
     *
     * @return the list of relative cell coordinates
     */
    List<Point> getCells();

    /**
     * Returns the name of the piece.
     *
     * @return the name of the piece
     */
    String getName();

    /**
     * Returns the color of the piece.
     *
     * @return the color of the piece
     */
    Color getColor();
}
