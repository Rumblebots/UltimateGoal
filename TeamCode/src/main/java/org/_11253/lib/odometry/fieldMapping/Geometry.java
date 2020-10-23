package org._11253.lib.odometry.fieldMapping;

import org._11253.lib.odometry.fieldMapping.components.Coordinate;
import org._11253.lib.odometry.fieldMapping.components.countable.Line;

public class Geometry {
    public final static Coordinate<Double> origin = new Coordinate<>(0.0, 0.0);
    public final static Coordinate<Double> topRight = new Coordinate<>(144.0, 144.0);
    public final static Coordinate<Double> topLeft = new Coordinate<>(0.0, 144.0);
    public final static Coordinate<Double> bottomRight = new Coordinate<>(144.0, 0.0);
    public final static Coordinate<Double> bottomLeft = new Coordinate<>(0.0, 0.0);
    public final static Coordinate<Double> center = new Coordinate<>(72.0, 72.0);
    public final static Line top = new Line(topLeft, topRight);
    public final static Line right = new Line(topRight, bottomRight);
    public final static Line bottom = new Line(bottomRight, bottomLeft);
    public final static Line left = new Line(bottomLeft, topLeft);
    public final static Coordinate<Double> midTop = top.midpoint;
    public final static Coordinate<Double> midRight = right.midpoint;
    public final static Coordinate<Double> midBottom = bottom.midpoint;
    public final static Coordinate<Double> midLeft = left.midpoint;
    public final static Coordinate<Double> tile = new Coordinate<>(24.0, 24.0);
    public final static Coordinate<Double> tileX = new Coordinate<>(24.0, 0.0);
    public final static Coordinate<Double> tileY = new Coordinate<>(0.0, 24.0);
    public final static Coordinate<Double> halfTile = new Coordinate<>(12.0, 12.0);
    public final static Coordinate<Double> halfTileX = new Coordinate<>(12.0, 0.0);
    public final static Coordinate<Double> halfTileY = new Coordinate<>(0.0, 12.0);
    public final static Coordinate<Double> quarterTile = new Coordinate<>(6.0, 6.0);
    public final static Coordinate<Double> quarterTileX = new Coordinate<>(6.0, 0.0);
    public final static Coordinate<Double> quarterTileY = new Coordinate<>(0.0, 6.0);
    public final static Coordinate<Double> inch = new Coordinate<>(1.0, 1.0);
    public final static Coordinate<Double> inchX = new Coordinate<>(1.0, 0.0);
    public final static Coordinate<Double> inchY = new Coordinate<>(0.0, 1.0);
    public final static double tileSide = 24.0;
    public final static double halfTileSide = 12.0;
    public final static double quarterTileSide = 6.0;

    public static double getDistance(Coordinate<Double> a,
                                     Coordinate<Double> b) {
        return new Line(a, b).length;
    }
}
