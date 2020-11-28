package org._11253.lib.odometry.fieldMapping.maps.ultimategoal.zones.red;

import org._11253.lib.odometry.fieldMapping.maps.ultimategoal.shapes.RedShapes;
import org._11253.lib.odometry.fieldMapping.shapes.Shape;
import org._11253.lib.odometry.fieldMapping.zones.specialized.RectangleZone;

public class RedTargetC extends RectangleZone {
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
        return 2;
    }
}
