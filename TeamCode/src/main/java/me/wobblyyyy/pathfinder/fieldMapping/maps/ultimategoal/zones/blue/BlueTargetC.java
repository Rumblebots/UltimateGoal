package me.wobblyyyy.pathfinder.fieldMapping.maps.ultimategoal.zones.blue;

import me.wobblyyyy.pathfinder.fieldMapping.maps.ultimategoal.shapes.BlueShapes;
import me.wobblyyyy.pathfinder.fieldMapping.shapes.Shape;
import me.wobblyyyy.pathfinder.fieldMapping.zones.specialized.RectangleZone;

public class BlueTargetC extends RectangleZone {
    @Override
    public String getName() {
        return "BlueTargetC";
    }

    @Override
    public Shape getParentShape() {
        return BlueShapes.BlueTargetC;
    }

    @Override
    public int getZonePriority() {
        return 2;
    }
}
