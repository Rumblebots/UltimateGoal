package me.wobblyyyy.pathfinder.fieldMapping.maps.ultimategoal.zones.blue;

import me.wobblyyyy.pathfinder.fieldMapping.maps.ultimategoal.shapes.BlueShapes;
import me.wobblyyyy.pathfinder.fieldMapping.shapes.Shape;
import me.wobblyyyy.pathfinder.fieldMapping.zones.specialized.CircleZone;

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
