package me.wobblyyyy.pathfinder.fieldMapping.maps.ultimategoal.zones.red;

import me.wobblyyyy.pathfinder.fieldMapping.maps.ultimategoal.shapes.RedShapes;
import me.wobblyyyy.pathfinder.fieldMapping.shapes.Shape;
import me.wobblyyyy.pathfinder.fieldMapping.zones.specialized.CircleZone;

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
