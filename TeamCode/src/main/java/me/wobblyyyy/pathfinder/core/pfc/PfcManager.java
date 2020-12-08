package me.wobblyyyy.pathfinder.core.pfc;

import org.xguzm.pathfinding.grid.GridCell;

/**
 * A class, containing everything related to working with Xavier's pathfinding
 * library.
 *
 * <p>
 * It's very likely that this will exclusively be used internally, so the documentation
 * here might be a bit sparse - I do apologize, but, you know what? Sucks to suck.
 * </p>
 *
 * @author Colin Robertson
 */
public class PfcManager {
    /**
     * Cells container.
     */
    public PfcCells cells = new PfcCells(this);

    /**
     * Navigation container.
     */
    public PfcNav nav = new PfcNav(this);

    /**
     * Finder container.
     */
    public PfcFinder finder = new PfcFinder(this);

    /**
     * Create a new instance of PfcManager.
     */
    public PfcManager(GridCell[][] cells) {
        this.cells.cells = cells;
        nav.setGrid(cells);
    }
}
