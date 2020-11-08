package org._11253.lib.odometry.fieldMapping.pathfinding.paths.instructions;

import org._11253.lib.odometry.fieldMapping.Geometry;
import org._11253.lib.odometry.fieldMapping.TwoDimensionalRobot;
import org._11253.lib.odometry.fieldMapping.components.Coordinate;
import org._11253.lib.odometry.fieldMapping.components.HeadingCoordinate;

public class MoveInstruction implements Instruction {
    private HeadingCoordinate<Double> start;
    private HeadingCoordinate<Double> end;
    private Coordinate<Double> _start;
    private Coordinate<Double> _end;
    private TwoDimensionalRobot robot;

    public MoveInstruction(HeadingCoordinate<Double> start,
                           HeadingCoordinate<Double> end) {
        this.start = start;
        this.end = end;
        _start = start.getCoordinate();
        _end = end.getCoordinate();
    }

    @Override
    public boolean cancellable() {
        return true;
    }

    @Override
    public boolean isTimed() {
        return true;
    }

    @Override
    public int maxTime() {
        return 10000;
    }

    @Override
    public int minTime() {
        return 0;
    }

    @Override
    public boolean conditionStart() {
        return Geometry.isNearPoint(robot.position, _start, 1.5);
    }

    @Override
    public boolean conditionContinue() {
        return true;
    }

    @Override
    public boolean conditionStop() {
        return Geometry.isNearPoint(robot.position, _end, 1.5);
    }
}
