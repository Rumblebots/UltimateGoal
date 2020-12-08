package me.wobblyyyy.pathfinder.fieldMapping.maps.testing.quadTest;

import me.wobblyyyy.pathfinder.fieldMapping.Map;
import me.wobblyyyy.pathfinder.fieldMapping.maps.ultimategoal.zones.shared.SharedBlueSide;

public class QuadTestMap extends Map {
    public QuadTestMap() {
        fieldZones.add(
                new SharedBlueSide()
        );
    }
}
