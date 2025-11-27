package it.unibo.uniboparty.model.minigames.tetris;

import org.junit.jupiter.api.Test;
import it.unibo.uniboparty.model.minigames.tetris.impl.PieceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;
import java.awt.Point;
import java.util.List;

class PieceTest {

    private static final Color TEST_COLOR = Color.RED;

    private final int[][] lShapeCoords = {{1, 0}, {2, 0}, {3, 0}, {3, 1}};
    private final int[][] offsetCoords = {{10, -5}, {10, -4}, {11, -5}};
    private final int[][] oShapeCoords = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};

    /**
     * test factory method and basic getters.
     */
    @Test
    void testFactoryMethodAndBasicGetters() {
        final PieceImpl piece = PieceImpl.of(lShapeCoords, "LTest", TEST_COLOR);

        assertEquals("LTest", piece.getName());
        assertEquals(TEST_COLOR, piece.getColor());
        assertEquals(4, piece.getCells().size());

        final List<Point> cells = piece.getCells();

        assertTrue(cells.contains(new Point(0, 0))); 

        assertTrue(cells.contains(new Point(0, 1)));

        assertTrue(cells.contains(new Point(0, 2)));

        assertTrue(cells.contains(new Point(1, 2)));
    }

    /**
     * test width and height methods.
     */
    @Test
    void testWidthAndHeight() {
        final PieceImpl pieceL = PieceImpl.of(lShapeCoords, "L", TEST_COLOR);

        assertEquals(2, pieceL.width());
        assertEquals(3, pieceL.height());

        final PieceImpl pieceO = PieceImpl.of(oShapeCoords, "O", TEST_COLOR);

        assertEquals(2, pieceO.width());
        assertEquals(2, pieceO.height());
    }

    /**
     * test normalization of coordinates with offsets.
     */
    @Test
    void testNormalizationWithOffsetCoords() {
        final PieceImpl piece = PieceImpl.of(offsetCoords, "Offset", TEST_COLOR);

        final List<Point> cells = piece.getCells();
        assertEquals(3, cells.size());

        assertTrue(cells.contains(new Point(0, 0))); 

        assertTrue(cells.contains(new Point(1, 0)));

        assertTrue(cells.contains(new Point(0, 1)));

        assertEquals(2, piece.width());
        assertEquals(2, piece.height());
    }
}
