package me.wobblyyyy.pathfinder.fieldMapping.maps.ultimategoal.zones.blue;

import me.wobblyyyy.pathfinder.fieldMapping.maps.ultimategoal.shapes.BlueShapes;
import me.wobblyyyy.pathfinder.fieldMapping.shapes.Shape;
import me.wobblyyyy.pathfinder.fieldMapping.zones.specialized.CircleZone;

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
