package me.wobblyyyy.pathfinder.fieldMapping.maps.ultimategoal.zones.blue;

import me.wobblyyyy.pathfinder.fieldMapping.shapes.Shape;
import me.wobblyyyy.pathfinder.fieldMapping.zones.specialized.RectangleZone;

public class BlueStartLineLeft extends RectangleZone {
    @Override
    public String getName() {
        return "BlueStartLineLeft";
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
