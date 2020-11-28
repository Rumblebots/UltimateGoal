package me.wobblyyyy.pathfinder.fieldMapping.maps.testing.quadTest.zones.shared;

import me.wobblyyyy.pathfinder.fieldMapping.maps.testing.quadTest.shapes.SharedShapes;
import me.wobblyyyy.pathfinder.fieldMapping.shapes.Shape;
import me.wobblyyyy.pathfinder.fieldMapping.zones.specialized.RectangleZone;

public class SharedRedSide extends RectangleZone {
    @Override
    public String getName() {
        return "SharedRedSide";
    }

    @Override
    public Shape getParentShape() {
        return SharedShapes.SharedRedSide;
    }

    @Override
    public int getZonePriority() {
        return 1;
    }
}
