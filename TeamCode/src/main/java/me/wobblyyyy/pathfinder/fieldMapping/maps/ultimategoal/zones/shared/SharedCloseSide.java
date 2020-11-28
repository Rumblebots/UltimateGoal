package me.wobblyyyy.pathfinder.fieldMapping.maps.ultimategoal.zones.shared;

import me.wobblyyyy.pathfinder.fieldMapping.maps.ultimategoal.shapes.SharedShapes;
import me.wobblyyyy.pathfinder.fieldMapping.shapes.Shape;
import me.wobblyyyy.pathfinder.fieldMapping.zones.specialized.RectangleZone;

public class SharedCloseSide extends RectangleZone {
    @Override
    public String getName() {
        return "SharedCloseSide";
    }

    @Override
    public Shape getParentShape() {
        return SharedShapes.SharedCloseSide;
    }

    @Override
    public int getZonePriority() {
        return 1;
    }
}
