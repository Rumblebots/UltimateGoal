package me.wobblyyyy.pathfinder.fieldMapping.maps.ultimategoal.zones.blue;

import me.wobblyyyy.pathfinder.fieldMapping.maps.ultimategoal.shapes.BlueShapes;
import me.wobblyyyy.pathfinder.fieldMapping.shapes.Shape;
import me.wobblyyyy.pathfinder.fieldMapping.zones.specialized.RectangleZone;

public class BlueTargetB extends RectangleZone {
    @Override
    public String getName() {
        return "BlueTargetB";
    }

    @Override
    public Shape getParentShape() {
        return BlueShapes.BlueTargetB;
    }

    @Override
    public int getZonePriority() {
        return 2;
    }
}
