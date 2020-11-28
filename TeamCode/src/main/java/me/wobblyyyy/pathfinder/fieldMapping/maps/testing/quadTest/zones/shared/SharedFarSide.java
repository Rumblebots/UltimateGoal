package me.wobblyyyy.pathfinder.fieldMapping.maps.testing.quadTest.zones.shared;

import me.wobblyyyy.pathfinder.fieldMapping.maps.testing.quadTest.shapes.SharedShapes;
import me.wobblyyyy.pathfinder.fieldMapping.shapes.Shape;
import me.wobblyyyy.pathfinder.fieldMapping.zones.specialized.RectangleZone;

public class SharedFarSide extends RectangleZone {
    @Override
    public String getName() {
        return "SharedFarSide";
    }

    @Override
    public Shape getParentShape() {
        return SharedShapes.SharedFarSide;
    }

    @Override
    public int getZonePriority() {
        return 1;
    }
}
