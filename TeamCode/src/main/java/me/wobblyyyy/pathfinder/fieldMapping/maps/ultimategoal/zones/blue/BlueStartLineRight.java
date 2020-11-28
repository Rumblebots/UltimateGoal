package me.wobblyyyy.pathfinder.fieldMapping.maps.ultimategoal.zones.blue;

import me.wobblyyyy.pathfinder.fieldMapping.shapes.Shape;
import me.wobblyyyy.pathfinder.fieldMapping.zones.specialized.RectangleZone;

public class BlueStartLineRight extends RectangleZone {
    @Override
    public String getName() {
        return "BlueStartLineRight";
    }

    @Override
    public Shape getParentShape() {
        return null;
    }

    @Override
    public int getZonePriority() {
        return 0;
    }
}
