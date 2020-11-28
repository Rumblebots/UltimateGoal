package me.wobblyyyy.pathfinder.fieldMapping.zones.commonZones;

import me.wobblyyyy.pathfinder.fieldMapping.Geometry;
import me.wobblyyyy.pathfinder.fieldMapping.components.Coordinate;
import me.wobblyyyy.pathfinder.fieldMapping.components.countable.Line;
import me.wobblyyyy.pathfinder.fieldMapping.shapes.Rectangle;
import me.wobblyyyy.pathfinder.fieldMapping.shapes.Shape;
import me.wobblyyyy.pathfinder.fieldMapping.shapes.Square;
import me.wobblyyyy.pathfinder.fieldMapping.zones.Zone;
import me.wobblyyyy.pathfinder.fieldMapping.zones.specialized.RectangleZone;

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

    @Override
    public boolean isField() {
        return true;
    }

    @Override
    public boolean isCollidableWithField() {
        return false;
    }
}
