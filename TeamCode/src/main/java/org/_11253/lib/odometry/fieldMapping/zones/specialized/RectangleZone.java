package org._11253.lib.odometry.fieldMapping.zones.specialized;

import org._11253.lib.odometry.fieldMapping.components.Coordinate;
import org._11253.lib.odometry.fieldMapping.components.countable.Line;
import org._11253.lib.odometry.fieldMapping.zones.specialized.HighLevelZone;

public abstract class RectangleZone extends HighLevelZone {
    @Override
    public boolean isPointInZone(Coordinate<Double> point) {
        return getParentShape().isPointInShape(point);
    }

    @Override
    public boolean doesLineEnterZone(Line line) {
        return getParentShape().doesLineEnterShape(line);
    }
}
