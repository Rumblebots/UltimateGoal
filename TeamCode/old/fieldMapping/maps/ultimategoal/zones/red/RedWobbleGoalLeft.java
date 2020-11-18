package org._11253.lib.odometry.fieldMapping.maps.ultimategoal.zones.red;

import org._11253.lib.odometry.fieldMapping.maps.ultimategoal.shapes.RedShapes;
import org._11253.lib.odometry.fieldMapping.shapes.Shape;
import org._11253.lib.odometry.fieldMapping.zones.specialized.CircleZone;

public class RedWobbleGoalLeft extends CircleZone {
    @Override
    public String getName() {
        return "RedWobbleGoalLeft";
    }

    @Override
    public Shape getParentShape() {
        return RedShapes.RedWobbleGoalLeft;
    }

    @Override
    public int getZonePriority() {
        return 0;
    }
}
