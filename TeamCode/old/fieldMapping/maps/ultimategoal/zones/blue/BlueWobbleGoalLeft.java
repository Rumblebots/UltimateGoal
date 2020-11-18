package org._11253.lib.odometry.fieldMapping.maps.ultimategoal.zones.blue;

import org._11253.lib.odometry.fieldMapping.maps.ultimategoal.shapes.BlueShapes;
import org._11253.lib.odometry.fieldMapping.shapes.Shape;
import org._11253.lib.odometry.fieldMapping.zones.specialized.CircleZone;

public class BlueWobbleGoalLeft extends CircleZone {
    @Override
    public String getName() {
        return "BlueWobbleGoalLeft";
    }

    @Override
    public Shape getParentShape() {
        return BlueShapes.BlueWobbleGoalLeft;
    }

    @Override
    public int getZonePriority() {
        return 0;
    }
}
