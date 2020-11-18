package org._11253.lib.odometry.fieldMapping.maps.ultimategoal.zones.shared;

import org._11253.lib.odometry.fieldMapping.maps.ultimategoal.shapes.SharedShapes;
import org._11253.lib.odometry.fieldMapping.shapes.Shape;
import org._11253.lib.odometry.fieldMapping.zones.specialized.RectangleZone;

public class SharedFarSide extends RectangleZone {
    @Override
    public String getName() {
        return "SharedFarSide";
    }

    @Override
    public Shape getParentShape() {
        return SharedShapes.SharedFarSide;
    }

    @Override
    public int getZonePriority() {
        return 1;
    }
}
