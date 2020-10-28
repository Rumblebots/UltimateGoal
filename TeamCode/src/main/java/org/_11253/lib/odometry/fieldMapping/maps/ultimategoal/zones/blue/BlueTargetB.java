package org._11253.lib.odometry.fieldMapping.maps.ultimategoal.zones.blue;

import org._11253.lib.odometry.fieldMapping.maps.ultimategoal.shapes.BlueShapes;
import org._11253.lib.odometry.fieldMapping.shapes.Shape;
import org._11253.lib.odometry.fieldMapping.zones.specialized.RectangleZone;

public class BlueTargetB extends RectangleZone {
    @Override
    public String getName() {
        return "BlueTargetB";
    }

    @Override
    public Shape getParentShape() {
        return BlueShapes.BlueTargetB;
    }

    @Override
    public int getZonePriority() {
        return 2;
    }
}
