package me.wobblyyyy.pathfinder.fieldMapping.maps.ultimategoal.zones.shared;

import me.wobblyyyy.pathfinder.fieldMapping.maps.ultimategoal.shapes.SharedShapes;
import me.wobblyyyy.pathfinder.fieldMapping.shapes.Shape;
import me.wobblyyyy.pathfinder.fieldMapping.zones.specialized.RectangleZone;

public class SharedLaunchLine extends RectangleZone {
    @Override
    public String getName() {
        return "SharedLaunchLine";
    }

    @Override
    public Shape getParentShape() {
        return SharedShapes.SharedLaunchLine;
    }

    @Override
    public int getZonePriority() {
        return 3;
    }
}
