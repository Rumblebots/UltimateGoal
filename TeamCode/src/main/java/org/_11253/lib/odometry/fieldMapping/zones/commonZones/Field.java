package org._11253.lib.odometry.fieldMapping.zones.commonZones;

import org._11253.lib.odometry.fieldMapping.components.Coordinate;
import org._11253.lib.odometry.fieldMapping.Geometry;
import org._11253.lib.odometry.fieldMapping.components.countable.Line;
import org._11253.lib.odometry.fieldMapping.shapes.Rectangle;
import org._11253.lib.odometry.fieldMapping.shapes.Square;
import org._11253.lib.odometry.fieldMapping.shapes.Shape;
import org._11253.lib.odometry.fieldMapping.zones.Zone;
import org._11253.lib.odometry.fieldMapping.zones.specialized.RectangleZone;

/**
 * The game field. This will (obviously) be in any and every field
 * mapping using robot's list of zones, as it is, well, quite literally,
 * the zone everything else takes place in.
 *
 * <p>
 * I can't really say this is a great introduction to how zones
 * in this library function, so go check out some more code.
 * {@link Square}
 * {@link Rectangle}
 * {@link Zone}
 * {@link Line}
 * {@link Coordinate}
 * </p>
 */
public class Field extends RectangleZone {
    public Rectangle field = new Rectangle(
            Rectangle.Corners.BACK_LEFT,
            Rectangle.Corners.CENTER,
            Geometry.origin,
            144,
            144,
            0,
            false,
            false
    );

    @Override
    public String getName() {
        return "Field";
    }

    @Override
    public Shape getParentShape() {
        return field;
    }

    @Override
    public int getZonePriority() {
        return 0;
    }

    @Override
    public boolean doesLineEnterZone(Line line) {
        return true;
    }

    @Override
    public boolean isPointInZone(Coordinate<Double> point) {
        return field.isPointInShape(point);
    }

    @Override
    public double getDriveSpeedMultiplier() {
        return 0.5;
    }
}
