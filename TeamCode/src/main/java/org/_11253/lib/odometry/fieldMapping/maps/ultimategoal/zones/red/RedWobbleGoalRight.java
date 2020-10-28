package org._11253.lib.odometry.fieldMapping.maps.ultimategoal.zones.red;

import org._11253.lib.odometry.fieldMapping.maps.ultimategoal.shapes.RedShapes;
import org._11253.lib.odometry.fieldMapping.shapes.Shape;
import org._11253.lib.odometry.fieldMapping.zones.specialized.CircleZone;

public class RedWobbleGoalRight extends CircleZone {
    @Override
    public String getName() {
        return "RedWobbleGoalRight";
    }

    @Override
    public Shape getParentShape() {
        return RedShapes.RedWobbleGoalRight;
    }

    @Override
    public int getZonePriority() {
        return 0;
    }
}
