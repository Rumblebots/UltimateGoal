package me.wobblyyyy.pathfinder.fieldMapping.maps.ultimategoal.zones.blue;

import me.wobblyyyy.pathfinder.fieldMapping.maps.ultimategoal.shapes.BlueShapes;
import me.wobblyyyy.pathfinder.fieldMapping.shapes.Shape;
import me.wobblyyyy.pathfinder.fieldMapping.zones.specialized.CircleZone;

public class BlueStartingStack extends CircleZone {
    @Override
    public String getName() {
        return "BlueStartingStack";
    }

    @Override
    public Shape getParentShape() {
        return BlueShapes.BlueStartingStack;
    }

    @Override
    public int getZonePriority() {
        return 0;
    }
}
