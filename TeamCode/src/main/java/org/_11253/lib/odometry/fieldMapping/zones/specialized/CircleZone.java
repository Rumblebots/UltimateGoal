package org._11253.lib.odometry.fieldMapping.zones.specialized;

import org._11253.lib.odometry.fieldMapping.components.Coordinate;
import org._11253.lib.odometry.fieldMapping.components.countable.Line;

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
