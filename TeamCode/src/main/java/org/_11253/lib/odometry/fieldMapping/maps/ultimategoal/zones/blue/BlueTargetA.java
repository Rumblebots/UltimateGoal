package org._11253.lib.odometry.fieldMapping.maps.ultimategoal.zones.blue;

import org._11253.lib.odometry.fieldMapping.maps.ultimategoal.shapes.BlueShapes;
import org._11253.lib.odometry.fieldMapping.shapes.Shape;
import org._11253.lib.odometry.fieldMapping.zones.specialized.RectangleZone;

public class BlueTargetA extends RectangleZone {
    @Override
    public String getName() {
        return "BlueTargetA";
    }

    @Override
    public Shape getParentShape() {
        return BlueShapes.BlueTargetA;
    }

    @Override
    public int getZonePriority() {
        return 2;
    }
}
