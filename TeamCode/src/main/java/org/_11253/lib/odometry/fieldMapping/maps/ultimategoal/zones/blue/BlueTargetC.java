package org._11253.lib.odometry.fieldMapping.maps.ultimategoal.zones.blue;

import org._11253.lib.odometry.fieldMapping.components.Coordinate;
import org._11253.lib.odometry.fieldMapping.components.countable.Line;
import org._11253.lib.odometry.fieldMapping.maps.ultimategoal.shapes.BlueShapes;
import org._11253.lib.odometry.fieldMapping.shapes.Shape;
import org._11253.lib.odometry.fieldMapping.zones.Zone;

public class BlueTargetC implements Zone {
    @Override
    public String getName() {
        return "BlueTargetC";
    }

    @Override
    public Shape getParentShape() {
        return BlueShapes.BlueTargetC;
    }

    @Override
    public int getZonePriority() {
        return 2;
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
