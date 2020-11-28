package me.wobblyyyy.pathfinder.fieldMapping.maps.ultimategoal.zones.blue;

import me.wobblyyyy.pathfinder.fieldMapping.maps.ultimategoal.shapes.BlueShapes;
import me.wobblyyyy.pathfinder.fieldMapping.shapes.Shape;
import me.wobblyyyy.pathfinder.fieldMapping.zones.specialized.RectangleZone;

public class BlueTargetA extends RectangleZone {
    @Override
    public String getName() {
        return "BlueTargetA";
    }

    @Override
    public Shape getParentShape() {
        return BlueShapes.BlueTargetA;
    }

    @Override
    public int getZonePriority() {
        return 2;
    }
}
