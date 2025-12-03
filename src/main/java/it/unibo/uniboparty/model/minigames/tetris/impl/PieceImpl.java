package it.unibo.uniboparty.model.minigames.tetris.impl;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.unibo.uniboparty.model.minigames.tetris.api.Piece;

/**
 * Implementation of the Piece interface.
 */
public final class PieceImpl implements Piece {
    private final List<Point> cells;
    private final String name;
    private final Color color;

    /**
     * Creates a new PieceImpl instance with the specified cells, name, and color.
     * 
     * @param cells the list of relative cell coordinates
     * @param name the name of the piece
     * @param color the color of the piece
     */
    public PieceImpl(final List<Point> cells, final String name, final Color color) {

        final int minR = cells.stream().mapToInt(p -> p.y).min().orElse(0);
        final int minC = cells.stream().mapToInt(p -> p.x).min().orElse(0);
        final List<Point> norm = new ArrayList<>();
        for (final Point p : cells) {
            norm.add(new Point(p.x - minC, p.y - minR));
        }
        this.cells = Collections.unmodifiableList(norm);
        this.name = name;
        this.color = color;
    }

    /**
     * Creates a new PieceImpl instance with the specified coordinates, name, and color.
     *
     * @param coords the coordinates of the piece's blocks
     * @param name the name of the piece
     * @param color the color of the piece
     * @return a new PieceImpl instance
     */
    public static PieceImpl of(final int[][] coords, final String name, final Color color) {
        final List<Point> pts = new ArrayList<>();
        for (final int[] rc : coords) {
            pts.add(new Point(rc[1], rc[0]));
        }
        return new PieceImpl(pts, name, color);
    }

    /**
     * {@InheritDoc}.
     */
    @Override
    public int width() { 
        return cells.stream().mapToInt(p -> p.x).max().orElse(0) + 1; 
    }

    /**
     * {@InheritDoc}.
     */
    @Override
    public int height() { 
        return cells.stream().mapToInt(p -> p.y).max().orElse(0) + 1; 
    }

    /**
     * {@InheritDoc}.
     */
    @Override
    public List<Point> getCells() { 
        return Collections.unmodifiableList(cells); 
    }

    /**
     * {@InheritDoc}.
     */
    @Override
    public String getName() { 
        return name; 
    }

    /**
     * {@InheritDoc}.
     */
    @Override
    public Color getColor() { 
        return color; 
    }
}
