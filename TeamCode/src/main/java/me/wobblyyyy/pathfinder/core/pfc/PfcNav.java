package me.wobblyyyy.pathfinder.core.pfc;

import org.xguzm.pathfinding.grid.GridCell;
import org.xguzm.pathfinding.grid.NavigationGrid;

/**
 * PathfindingCore: navigation-related utilities, including NavigationGrid.
 *
 * @author Colin Robertson
 */
public class PfcNav {
    /**
     * The navigation grid itself.
     */
    public NavigationGrid<GridCell> navigationGrid;

    /**
     * Create a new instance.
     *
     * @param pfcManager instanced manager class.
     */
    public PfcNav(PfcManager pfcManager) {

    }

    /**
     * Create a new instance of a NavigationGrid, with a bi-dimensional GridCell
     * array as an input.
     *
     * @param cells a bi-dimensional array of GridCells. This comes from the {@link PfcCells}
     *              class and will likely be set through {@link PfcManager} or something
     *              similar to that.
     */
    public void setGrid(GridCell[][] cells) {
        navigationGrid = new NavigationGrid<>(cells);
    }
}
