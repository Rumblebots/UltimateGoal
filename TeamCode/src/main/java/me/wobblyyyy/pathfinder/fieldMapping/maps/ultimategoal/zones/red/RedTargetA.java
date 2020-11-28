package me.wobblyyyy.pathfinder.fieldMapping.maps.ultimategoal.zones.red;

import me.wobblyyyy.pathfinder.fieldMapping.maps.ultimategoal.shapes.RedShapes;
import me.wobblyyyy.pathfinder.fieldMapping.shapes.Shape;
import me.wobblyyyy.pathfinder.fieldMapping.zones.specialized.RectangleZone;

public class RedTargetA extends RectangleZone {
    @Override
    public String getName() {
        return "RedTargetA";
    }

    @Override
    public Shape getParentShape() {
        return RedShapes.RedTargetA;
    }

    @Override
    public int getZonePriority() {
        return 2;
    }
}
