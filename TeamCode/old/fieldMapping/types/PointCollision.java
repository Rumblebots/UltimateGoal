package org._11253.lib.odometry.fieldMapping.types;

import org._11253.lib.odometry.fieldMapping.components.Coordinate;

/**
 * A collision between two points.
 */
public class PointCollision {
    private Coordinate<Double> pointOfCollision;

    public PointCollision(Coordinate<Double> pointOfCollision) {
        this.pointOfCollision = pointOfCollision;
    }

    public Coordinate<Double> getPointOfCollision() {
        return pointOfCollision;
    }
}
