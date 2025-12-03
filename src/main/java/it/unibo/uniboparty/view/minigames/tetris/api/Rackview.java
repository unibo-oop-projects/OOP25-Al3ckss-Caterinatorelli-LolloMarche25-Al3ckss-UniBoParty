package it.unibo.uniboparty.view.minigames.tetris.api;

import javax.swing.Icon;
import it.unibo.uniboparty.model.minigames.tetris.impl.PieceImpl;

/**
 * The {@code Rackview} interface defines the contract for visualizing and refreshing
 * the rack of Tetris pieces in the user interface.
 * Implementations are responsible for updating the view and rendering piece icons.
 */
public interface Rackview {

    /**
     * Refreshes the rack view, updating its visual representation.
     * This method should be called whenever the state of the rack changes
     * and the UI needs to reflect those changes.
     */
    void refresh();

    /**
     * Renders an icon representation of the specified Tetris piece.
     *
     * @param p the {@link PieceImpl} instance to render as an icon
     * @return an {@link Icon} representing the given piece
     */
    Icon renderIcon(PieceImpl p);
}


