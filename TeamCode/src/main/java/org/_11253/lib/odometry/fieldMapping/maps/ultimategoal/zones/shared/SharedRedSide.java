package org._11253.lib.odometry.fieldMapping.maps.ultimategoal.zones.shared;

import org._11253.lib.odometry.fieldMapping.components.Coordinate;
import org._11253.lib.odometry.fieldMapping.components.countable.Line;
import org._11253.lib.odometry.fieldMapping.maps.ultimategoal.shapes.SharedShapes;
import org._11253.lib.odometry.fieldMapping.shapes.Shape;
import org._11253.lib.odometry.fieldMapping.zones.Zone;

public class SharedRedSide implements Zone {
    @Override
    public String getName() {
        return "SharedRedSide";
    }

    @Override
    public Shape getParentShape() {
        return SharedShapes.SharedRedSide;
    }

    @Override
    public int getZonePriority() {
        return 1;
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
