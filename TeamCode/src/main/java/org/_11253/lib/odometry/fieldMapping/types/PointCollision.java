package org._11253.lib.odometry.fieldMapping.types;

import org._11253.lib.odometry.fieldMapping.components.Coordinate;

public class PointCollision {
    private Coordinate<Double> pointOfCollision;

    public PointCollision(Coordinate<Double> pointOfCollision) {
        this.pointOfCollision = pointOfCollision;
    }

    public Coordinate<Double> getPointOfCollision() {
        return pointOfCollision;
    }
}
