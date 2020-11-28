package org._11253.lib.odometry.fieldMapping.zones.commonZones;

import org._11253.lib.odometry.fieldMapping.shapes.Rectangle;
import org._11253.lib.odometry.fieldMapping.shapes.Shape;
import org._11253.lib.odometry.fieldMapping.zones.specialized.RectangleZone;

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
