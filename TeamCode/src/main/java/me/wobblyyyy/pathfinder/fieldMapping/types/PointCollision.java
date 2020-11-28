package me.wobblyyyy.pathfinder.fieldMapping.types;

import me.wobblyyyy.pathfinder.fieldMapping.components.Coordinate;

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
