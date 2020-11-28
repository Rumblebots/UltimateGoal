package me.wobblyyyy.pathfinder.fieldMapping.zones.commonZones;

import me.wobblyyyy.pathfinder.fieldMapping.shapes.Rectangle;
import me.wobblyyyy.pathfinder.fieldMapping.shapes.Shape;
import me.wobblyyyy.pathfinder.fieldMapping.zones.specialized.RectangleZone;

/**
 * A wrapper zone for the robot itself.
 */
public class Robot extends RectangleZone {
    Rectangle rectangle;

    public Robot(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    @Override
    public String getName() {
        return "2dRobot";
    }

    @Override
    public Shape getParentShape() {
        return rectangle;
    }
}
