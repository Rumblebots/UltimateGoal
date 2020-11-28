package me.wobblyyyy.pathfinder.fieldMapping.pathfinding.paths;

import me.wobblyyyy.pathfinder.fieldMapping.components.Coordinate;
import me.wobblyyyy.pathfinder.fieldMapping.components.HeadingCoordinate;
import me.wobblyyyy.pathfinder.fieldMapping.components.countable.Line;

public class PlannedLinearPath implements PlannedPath {
    public HeadingCoordinate<Double> start;
    public HeadingCoordinate<Double> end;
    public Coordinate<Double> _start;
    public Coordinate<Double> _end;
    public double headingDifference;
    public Line direct;

    public PlannedLinearPath(HeadingCoordinate<Double> start,
                             HeadingCoordinate<Double> end) {
        this.start = start;
        this.end = end;
        _start = new Coordinate<>(start.getX(), start.getY());
        _end = new Coordinate<>(end.getX(), end.getY());
        headingDifference = end.getHeading() - start.getHeading();
        direct = new Line(_start, _end);
    }

    public PlannedLinearPath(Line line,
                             double startHeading,
                             double endHeading) {
        this(
                new HeadingCoordinate<>(line.a.getX(), line.a.getY(), startHeading),
                new HeadingCoordinate<>(line.b.getX(), line.b.getY(), endHeading)
        );
    }

    @Override
    public HeadingCoordinate<Double> start() {
        return start;
    }

    @Override
    public HeadingCoordinate<Double> end() {
        return end;
    }
}
