package org._11253.lib.odometry.fieldMapping.zones.commonZones;

import org._11253.lib.odometry.fieldMapping.components.Coordinate;
import org._11253.lib.odometry.fieldMapping.components.countable.Line;
import org._11253.lib.odometry.fieldMapping.shapes.Rectangle;
import org._11253.lib.odometry.fieldMapping.shapes.Shape;
import org._11253.lib.odometry.fieldMapping.zones.specialized.RectangleZone;

public class Robot extends RectangleZone {
    Rectangle rectangle;

    public Robot(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    @Override
    public String getName() {
        return "2dRobot";
    }

    @Override
    public Shape getParentShape() {
        return rectangle;
    }

    @Override
    public int getZonePriority() {
        return -1;
    }

    @Override
    public boolean doesLineEnterZone(Line line) {
        return rectangle.doesLineEnterShape(line);
    }

    @Override
    public boolean isPointInZone(Coordinate<Double> point) {
        return rectangle.isPointInShape(point);
    }

    @Override
    public double getDriveSpeedMultiplier() {
        return 0;
    }
}
