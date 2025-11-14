package it.unibo.uniboparty.model.minigames.tetris.impl;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Standard Tetris pieces definitions.
 */
public final class StandardPieces {
    public static final List<PieceImpl> ALL;
    private static final int SIX_BLOCKS_INDEX_START = 6;
    private static final int FIVE_BLOCKS_INDEX_START = 5;

    private StandardPieces() {
    }

    private static Color pick(final int idx) {
        final Color[] palette = {
            new Color(0x4CAF50), new Color(0x2196F3), new Color(0xFF9800),
            new Color(0x9C27B0), new Color(0xF44336), new Color(0x009688),
            new Color(0x3F51B5),
        };
        return palette[idx % palette.length];
    }

    static {
        final List<PieceImpl> list = new ArrayList<>();

        list.add(PieceImpl.of(new int[][]{{0, 0}}, "Dot", pick(0)));

        list.add(PieceImpl.of(new int[][]{{0, 0}, {0, 1}}, "DominoH", pick(1)));
        list.add(PieceImpl.of(new int[][]{{0, 0}, {1, 0}}, "DominoV", pick(2)));

        list.add(PieceImpl.of(new int[][]{{0, 0}, {0, 1}, {0, 2}}, "I3H", pick(3)));
        list.add(PieceImpl.of(new int[][]{{0, 0}, {1, 0}, {2, 0}}, "I3V", pick(4)));
        list.add(PieceImpl.of(new int[][]{{0, 0}, {1, 0}, {1, 1}}, "L3", pick(FIVE_BLOCKS_INDEX_START)));

        list.add(PieceImpl.of(new int[][]{{0, 0}, {0, 1}, {0, 2}, {0, 3}}, "I4H", pick(SIX_BLOCKS_INDEX_START)));
        list.add(PieceImpl.of(new int[][]{{0, 0}, {1, 0}, {2, 0}, {3, 0}}, "I4V", pick(0)));
        list.add(PieceImpl.of(new int[][]{{0, 0}, {0, 1}, {1, 0}, {1, 1}}, "O", pick(1)));
        list.add(PieceImpl.of(new int[][]{{0, 0}, {1, 0}, {2, 0}, {2, 1}}, "L4", pick(2)));
        list.add(PieceImpl.of(new int[][]{{0, 1}, {1, 0}, {1, 1}, {1, 2}}, "T4", pick(3)));

        list.add(PieceImpl.of(new int[][]{{0, 0}, {0, 1}, {0, 2}, {0, 3}, {0, 4}}, "I5H", pick(4)));
        list.add(PieceImpl.of(new int[][]{{0, 0}, {1, 0}, {2, 0}, {3, 0}, {4, 0}}, "I5V", pick(FIVE_BLOCKS_INDEX_START)));
        list.add(PieceImpl.of(new int[][]{{0, 0}, {1, 0}, {2, 0}, {2, 1}, {2, 2}}, "L5", pick(SIX_BLOCKS_INDEX_START)));
        list.add(PieceImpl.of(new int[][]{{0, 1}, {1, 0}, {1, 1}, {1, 2}, {2, 1}}, "Plus", pick(0)));
        list.add(PieceImpl.of(new int[][]{{0, 0}, {1, 0}, {1, 1}, {2, 1}, {3, 1}}, "Stair5", pick(1)));
        ALL = Collections.unmodifiableList(list);
    }
}
