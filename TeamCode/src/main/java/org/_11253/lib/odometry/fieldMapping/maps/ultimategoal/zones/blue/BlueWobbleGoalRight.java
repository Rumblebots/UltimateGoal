package org._11253.lib.odometry.fieldMapping.maps.ultimategoal.zones.blue;

import org._11253.lib.odometry.fieldMapping.maps.ultimategoal.shapes.BlueShapes;
import org._11253.lib.odometry.fieldMapping.shapes.Shape;
import org._11253.lib.odometry.fieldMapping.zones.specialized.CircleZone;

public class BlueWobbleGoalRight extends CircleZone {
    @Override
    public String getName() {
        return "BlueWobbleGoalRight";
    }

    @Override
    public Shape getParentShape() {
        return BlueShapes.BlueWobbleGoalRight;
    }

    @Override
    public int getZonePriority() {
        return 0;
    }
}
