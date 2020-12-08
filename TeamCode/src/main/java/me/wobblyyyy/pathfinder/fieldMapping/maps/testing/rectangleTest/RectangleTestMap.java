package me.wobblyyyy.pathfinder.fieldMapping.maps.testing.rectangleTest;

import me.wobblyyyy.pathfinder.fieldMapping.Map;
import me.wobblyyyy.pathfinder.fieldMapping.components.Coordinate;
import me.wobblyyyy.pathfinder.fieldMapping.shapes.Rectangle;
import me.wobblyyyy.pathfinder.fieldMapping.shapes.Shape;
import me.wobblyyyy.pathfinder.fieldMapping.zones.specialized.RectangleZone;

public class RectangleTestMap extends Map {
    public RectangleTestMap() {
        fieldZones.add(
                new RectangleZone() {
                    @Override
                    public String getName() {
                        return "Rotated Zone 1";
                    }

                    @Override
                    public Shape getParentShape() {
                        return new Rectangle(
                                Rectangle.Corners.FRONT_LEFT,
                                Rectangle.Corners.CENTER,
                                new Coordinate<>(10.0, 10.0),
                                72,
                                -72,
                                0.25 * Math.PI,
                                true,
                                true
                        );
                    }
                }
        );
    }
}
