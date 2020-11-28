package me.wobblyyyy.pathfinder.fieldMapping.zones.specialized;

import me.wobblyyyy.pathfinder.fieldMapping.components.Coordinate;
import me.wobblyyyy.pathfinder.fieldMapping.components.countable.Line;

public abstract class CircleZone extends HighLevelZone {
    @Override
    public int getComponents() {
        return 2;
    }

    @Override
    public boolean doesLineEnterZone(Line line) {
        return getParentShape().doesLineEnterShape(line);
    }

    @Override
    public boolean isPointInZone(Coordinate<Double> point) {
        return getParentShape().isPointInShape(point);
    }
}
