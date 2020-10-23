package org._11253.lib.odometry.fieldMapping.maps.ultimategoal.zones.red;

import org._11253.lib.odometry.fieldMapping.components.Coordinate;
import org._11253.lib.odometry.fieldMapping.components.countable.Line;
import org._11253.lib.odometry.fieldMapping.maps.ultimategoal.shapes.RedShapes;
import org._11253.lib.odometry.fieldMapping.shapes.Shape;
import org._11253.lib.odometry.fieldMapping.zones.Zone;

public class RedTargetC implements Zone {
    @Override
    public String getName() {
        return "RedTargetC";
    }

    @Override
    public Shape getParentShape() {
        return RedShapes.RedTargetC;
    }

    @Override
    public int getZonePriority() {
        return 0;
    }

    @Override
    public boolean doesLineEnterZone(Line line) {
        return false;
    }

    @Override
    public boolean isPointInZone(Coordinate<Double> point) {
        return false;
    }

    @Override
    public double getDriveSpeedMultiplier() {
        return 0;
    }
}
