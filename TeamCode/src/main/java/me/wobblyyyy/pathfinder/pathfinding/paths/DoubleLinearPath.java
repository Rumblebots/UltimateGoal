package me.wobblyyyy.pathfinder.pathfinding.paths;

import me.wobblyyyy.pathfinder.fieldMapping.components.Coordinate;
import me.wobblyyyy.pathfinder.fieldMapping.components.HeadingCoordinate;
import me.wobblyyyy.pathfinder.fieldMapping.components.countable.Line;

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
