package me.wobblyyyy.pathfinder.fieldMapping;

import me.wobblyyyy.intra.ftc2.utils.math.Comparator;
import me.wobblyyyy.intra.ftc2.utils.math.Math;
import me.wobblyyyy.pathfinder.fieldMapping.components.Coordinate;
import me.wobblyyyy.pathfinder.fieldMapping.components.HeadingCoordinate;
import me.wobblyyyy.pathfinder.fieldMapping.components.countable.Line;

/**
 * Geometry-related utilities.
 *
 * <p>
 * Of course it's all static. It's all the same. It's just a bunch of
 * random geometry-related utilities. Yay.
 * </p>
 *
 * @author Colin Robertson
 */
public class Geometry {
    /**
     * (0, 0) position.
     */
    public final static Coordinate<Double> origin = new Coordinate<>(0.0, 0.0);

    /**
     * Top-right position.
     */
    public final static Coordinate<Double> topRight = new Coordinate<>(144.0, 144.0);

    /**
     * Top-left position.
     */
    public final static Coordinate<Double> topLeft = new Coordinate<>(0.0, 144.0);

    /**
     * Bottom-right position.
     */
    public final static Coordinate<Double> bottomRight = new Coordinate<>(144.0, 0.0);

    /**
     * Bottom-left position.
     */
    public final static Coordinate<Double> bottomLeft = new Coordinate<>(0.0, 0.0);

    /**
     * Center position.
     */
    public final static Coordinate<Double> center = new Coordinate<>(72.0, 72.0);

    /**
     * Top line (between top left and top right).
     */
    public final static Line top = new Line(topLeft, topRight);

    /**
     * Right line (between top right and bottom right).
     */
    public final static Line right = new Line(topRight, bottomRight);

    /**
     * Bottom line (between bottom left and bottom right).
     */
    public final static Line bottom = new Line(bottomRight, bottomLeft);

    /**
     * Left line (between top left and bottom left).
     */
    public final static Line left = new Line(bottomLeft, topLeft);

    /**
     * Midpoint of the top line.
     */
    public final static Coordinate<Double> midTop = top.midpoint;

    /**
     * Midpoint of the right line.
     */
    public final static Coordinate<Double> midRight = right.midpoint;

    /**
     * Midpoint of the bottom line.
     */
    public final static Coordinate<Double> midBottom = bottom.midpoint;

    /**
     * Midpoint of the left line.
     */
    public final static Coordinate<Double> midLeft = left.midpoint;

    /**
     * Coordinate tile position.
     */
    public final static Coordinate<Double> tile = new Coordinate<>(24.0, 24.0);

    /**
     * Coordinate tile X.
     */
    public final static Coordinate<Double> tileX = new Coordinate<>(24.0, 0.0);

    /**
     * Coordinate tile Y.
     */
    public final static Coordinate<Double> tileY = new Coordinate<>(0.0, 24.0);

    /**
     * Coordinate for half of a tile.
     */
    public final static Coordinate<Double> halfTile = new Coordinate<>(12.0, 12.0);

    /**
     * Coordinate for half of a tile's X.
     */
    public final static Coordinate<Double> halfTileX = new Coordinate<>(12.0, 0.0);

    /**
     * Coordinate for half of a tile's Y.
     */
    public final static Coordinate<Double> halfTileY = new Coordinate<>(0.0, 12.0);

    /**
     * Coordinate for a quarter of a tile.
     */
    public final static Coordinate<Double> quarterTile = new Coordinate<>(6.0, 6.0);

    /**
     * Coordinate for a quarter of a tile's X.
     */
    public final static Coordinate<Double> quarterTileX = new Coordinate<>(6.0, 0.0);

    /**
     * Coordinate for a quarter of a tile's Y.
     */
    public final static Coordinate<Double> quarterTileY = new Coordinate<>(0.0, 6.0);

    /**
     * Coordinate for one inch.
     */
    public final static Coordinate<Double> inch = new Coordinate<>(1.0, 1.0);

    /**
     * One inch (only X).
     */
    public final static Coordinate<Double> inchX = new Coordinate<>(1.0, 0.0);

    /**
     * One inch (only Y).
     */
    public final static Coordinate<Double> inchY = new Coordinate<>(0.0, 1.0);

    /**
     * Tile side length.
     */
    public final static double tileSide = 24.0;

    /**
     * Half tile side length.
     */
    public final static double halfTileSide = 12.0;

    /**
     * Quarter tile side length.
     */
    public final static double quarterTileSide = 6.0;

    /**
     * Get the distance between two points.
     *
     * @param a first coordinate.
     * @param b second coordinate.
     * @return the distance between the two coordinates.
     */
    public static double getDistance(Coordinate<Double> a,
                                     Coordinate<Double> b) {
        return new Line(a, b).length;
    }

    /**
     * Determine whether or not a point is within a given proximity to
     * another point.
     *
     * @param test the point to test.
     * @param base the point which is used as a base.
     * @param tolerance how far the two points can be.
     * @return whether or not the two points are within a given proximity.
     */
    public static boolean isNearPoint(Coordinate<Double> test,
                                      Coordinate<Double> base,
                                      double tolerance) {
        return tolerance <= new Line(test, base).length;
    }

    /**
     * Comparator wrapper for determining if two headings are alike.
     *
     * @param test the heading to test.
     * @param base the heading that serves as a base.
     * @param tolerance the maximum allowable tolerance.
     * @return if the headings are like.
     */
    public static boolean areHeadingsClose(HeadingCoordinate<Double> test,
                                           HeadingCoordinate<Double> base,
                                           double tolerance) {
        return new Comparator(tolerance).compare(
                test.getHeading(),
                base.getHeading()
        );
    }

    /**
     * Convert degrees to radians.
     *
     * @param degrees the degrees input to use.
     * @return the radians output.
     */
    public static double toRadians(double degrees) {
        return Math.toRadians(degrees);
    }

    /**
     * Convert radians to degrees.
     *
     * @param radians the radians input to use.
     * @return the degrees output.
     */
    public static double toDegrees(double radians) {
        return Math.toDegrees(radians);
    }
}
