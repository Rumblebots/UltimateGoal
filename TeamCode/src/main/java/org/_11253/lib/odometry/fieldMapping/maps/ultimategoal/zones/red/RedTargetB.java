package org._11253.lib.odometry.fieldMapping.maps.ultimategoal.zones.red;

import org._11253.lib.odometry.fieldMapping.maps.ultimategoal.shapes.RedShapes;
import org._11253.lib.odometry.fieldMapping.shapes.Shape;
import org._11253.lib.odometry.fieldMapping.zones.specialized.RectangleZone;

public class RedTargetB extends RectangleZone {
    @Override
    public String getName() {
        return "RedTargetB";
    }

    @Override
    public Shape getParentShape() {
        return RedShapes.RedTargetB;
    }

    @Override
    public int getZonePriority() {
        return 2;
    }
}
