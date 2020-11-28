package org._11253.lib.odometry.fieldMapping.pathfinding.paths;

import org._11253.lib.odometry.fieldMapping.components.Coordinate;
import org._11253.lib.odometry.fieldMapping.components.HeadingCoordinate;
import org._11253.lib.odometry.fieldMapping.components.countable.Line;

public class DoubleLinearPath {
    public PlannedLinearPath a;
    public PlannedLinearPath b;

    private Double toTargetX;
    private Double toTargetY;

    private Coordinate<Double> withTargetX;
    private Coordinate<Double> withTargetY;

    private Line lineToTargetX;
    private Line lineToTargetY;

    public DoubleLinearPath(HeadingCoordinate<Double> start,
                            HeadingCoordinate<Double> end) {
        toTargetX = end.getX() - start.getX();
        toTargetY = end.getY() - start.getY();

        withTargetX = new Coordinate<>(start.getX() + toTargetX, start.getY());
        withTargetY = new Coordinate<>(withTargetX.getX(), withTargetX.getY() + toTargetY);

        lineToTargetX = new Line(start.getCoordinate(), withTargetX);
        lineToTargetY = new Line(withTargetX, withTargetY);

        a = new PlannedLinearPath(lineToTargetX, start.getHeading(), start.getHeading());
        b = new PlannedLinearPath(lineToTargetY, start.getHeading(), end.getHeading());
    }
}
