package me.wobblyyyy.pathfinder.fieldMapping.maps.ultimategoal.zones.red;

import me.wobblyyyy.pathfinder.fieldMapping.maps.ultimategoal.shapes.RedShapes;
import me.wobblyyyy.pathfinder.fieldMapping.shapes.Shape;
import me.wobblyyyy.pathfinder.fieldMapping.zones.specialized.CircleZone;

public class RedStartingStack extends CircleZone {
    @Override
    public String getName() {
        return "RedStartingStack";
    }

    @Override
    public Shape getParentShape() {
        return RedShapes.RedStartingStack;
    }

    @Override
    public int getZonePriority() {
        return 0;
    }
}
