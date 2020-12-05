package me.wobblyyyy.pathfinder.core.pfc;

import me.wobblyyyy.pathfinder.fieldMapping.components.Coordinate;
import org.xguzm.pathfinding.grid.GridCell;

/**
 * Cells used when implementing PathfindingCore.
 *
 * @author Colin Robertson
 */
public class PfcCells {
    /**
     * A bi-dimensional array of cells, used in implementing PathfindingCore.
     */
    public GridCell[][] cells = new GridCell[1440][1440];

    /**
     * Empty constructor.
     *
     * @param pfcManager pfcManager instance.
     */
    public PfcCells(PfcManager pfcManager) {

    }

    /**
     * Convert a double coordinate to an integer one.
     *
     * <p>
     * Integer coordinates are magnified by 10 to allow for more precision. Thus,
     * conversions between integer coordinates and double coordinates will need to
     * be multiplied or divided by a factor of 10 when converting between the two.
     * </p>
     *
     * @param original the original coordinate.
     * @return the newly-converted coordinate.
     */
    public static Coordinate<Integer> doubleToInteger(Coordinate<Double> original) {
        return new Coordinate<>(
                (int) Math.round(original.getX() * 10),
                (int) Math.round(original.getY() * 10)
        );
    }

    /**
     * Convert an integer coordinate to a double one.
     *
     * <p>
     * Integer coordinates are magnified by 10 to allow for more precision. Thus,
     * conversions between integer coordinates and double coordinates will need to
     * be multiplied or divided by a factor of 10 when converting between the two.
     * </p>
     *
     * @param original the original coordinate.
     * @return the newly-converted coordinate.
     */
    public static Coordinate<Double> integerToDouble(Coordinate<Integer> original) {
        return new Coordinate<>(
                original.getX() / 10.0,
                original.getY() / 10.0
        );
    }
}
