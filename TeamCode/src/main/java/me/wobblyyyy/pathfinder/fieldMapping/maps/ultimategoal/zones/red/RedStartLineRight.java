package me.wobblyyyy.pathfinder.fieldMapping.maps.ultimategoal.zones.red;

import me.wobblyyyy.pathfinder.fieldMapping.shapes.Shape;
import me.wobblyyyy.pathfinder.fieldMapping.zones.specialized.RectangleZone;

public class RedStartLineRight extends RectangleZone {
    @Override
    public String getName() {
        return "RedStartLineRight";
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
