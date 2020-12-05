package me.wobblyyyy.pathfinder.core.pfc;

import me.wobblyyyy.pathfinder.fieldMapping.components.Coordinate;
import org.xguzm.pathfinding.finders.ThetaStarFinder;
import org.xguzm.pathfinding.grid.GridCell;
import org.xguzm.pathfinding.grid.NavigationGridGraph;
import org.xguzm.pathfinding.grid.finders.ThetaStarGridFinder;

import java.util.ArrayList;

/**
 * A class, containing an instance of a theta-based pathfinding algorithm.
 *
 * @author Colin Robertson
 */
public class PfcFinder {
    private final PfcManager pfcManager;

    /**
     * The finder itself.
     *
     * <p>
     * Per documentation in the PathfinderCore library, a ThetaStarGridFinder is
     * defined rather simply. First, it's worth noting that it's based on the much
     * more general {@link ThetaStarFinder}. Secondly, a theta star finder is just
     * defined as:
     * <code>
     * A finder which will use theta star algorithm on a grid (any angle path finding).
     * It also lets you find a path based on coordinates rather than nodes.
     * </code>
     * A {@link ThetaStarGridFinder} is thus defined as...
     * <pre>
     * A helper class to which lets you find a path based on coordinates rather than nodes
     * on an instance of {@link NavigationGridGraph}
     * </pre>
     * </p>
     */
    public ThetaStarGridFinder<GridCell> finder;

    /**
     * Create a new instance of the finder by using options provided in the
     * {@link PfcFinderOptions} class.
     */
    public PfcFinder(PfcManager pfcManager) {
        this.pfcManager = pfcManager;

        finder = new ThetaStarGridFinder<>(
                GridCell.class,
                PfcFinderOptions.options
        );
    }

    /**
     * Get a path that's notated in PathfindingCore's default GridCell implementation.
     *
     * <p>
     * Meccanum robots (or at least those that use this library) don't actually read GridCells - rather,
     * they read coordinates. Although this is a public function, it's rather unlikely you'll ever
     * need to use it. Rather, you should go check out {@link PfcFinder#getCoordinatePath(Coordinate, Coordinate)}.
     * </p>
     *
     * @param startDouble the start position (notated as a double coordinate).
     * @param endDouble the end position (notated as a double coordinate).
     * @return a list of scaled-up (1440x1440) {@link GridCell} instances.
     */
    public ArrayList<GridCell> getCellPath(Coordinate<Double> startDouble,
                                           Coordinate<Double> endDouble) {
        Coordinate<Integer> startInteger = PfcCells.doubleToInteger(startDouble);
        Coordinate<Integer> endInteger = PfcCells.doubleToInteger(endDouble);

        return new ArrayList<>(
                finder.findPath(
                        startInteger.getX(),
                        startInteger.getY(),
                        endInteger.getX(),
                        endInteger.getY(),
                        pfcManager.nav.navigationGrid
                )
        );
    }

    /**
     * Get a path, from a start coordinate to an end coordinate, that's directly readable
     * by our implementation of the pathfinding system.
     *
     * @param start the start coordinate.
     * @param end the end coordinate.
     * @return a group, composed of individual {@link Coordinate} items.
     */
    public ArrayList<Coordinate<Double>> getCoordinatePath(Coordinate<Double> start,
                                                           Coordinate<Double> end) {
        ArrayList<GridCell> cellPath = getCellPath(start, end);
        ArrayList<Coordinate<Double>> coordinatePath = new ArrayList<>();

        // For every grid cell, make some form a conversion between the grid
        // "coordinates" and our internally-used coordinates. Remember, these
        // grid coordinates only work based on integers - for this reason, it's
        // essential to convert each of these integers back into regular double
        // coordinates. That way... everything works. Yay!
        for (GridCell cell : cellPath) {
            coordinatePath.add(
                    PfcCells.integerToDouble(
                            new Coordinate<>(
                                    cell.getX(),
                                    cell.getY()
                            )
                    )
            );
        }

        return coordinatePath;
    }
}
