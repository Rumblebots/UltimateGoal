package me.wobblyyyy.pathfinder.fieldMapping.maps.ultimategoal.zones.red;

import me.wobblyyyy.pathfinder.fieldMapping.maps.ultimategoal.shapes.RedShapes;
import me.wobblyyyy.pathfinder.fieldMapping.shapes.Shape;
import me.wobblyyyy.pathfinder.fieldMapping.zones.specialized.CircleZone;

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
