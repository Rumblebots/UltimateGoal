package org._11253.lib.odometry.fieldMapping;

import org._11253.lib.odometry.fieldMapping.components.Coordinate;
import org._11253.lib.odometry.fieldMapping.components.countable.Line;

public class Geometry {
    public static Coordinate<Double> origin = new Coordinate<>(0.0, 0.0);

    public static double getDistance(Coordinate<Double> a,
                                     Coordinate<Double> b) {
        return new Line(a, b).length;
    }
}
