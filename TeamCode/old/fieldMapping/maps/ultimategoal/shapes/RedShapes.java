package org._11253.lib.odometry.fieldMapping.maps.ultimategoal.shapes;

import org._11253.lib.odometry.fieldMapping.Geometry;
import org._11253.lib.odometry.fieldMapping.components.Coordinate;
import org._11253.lib.odometry.fieldMapping.shapes.RadiusCircle;
import org._11253.lib.odometry.fieldMapping.shapes.Rectangle;

public class RedShapes {
    public static Rectangle RedTargetA = new Rectangle(
            Rectangle.Corners.BACK_LEFT,
            Rectangle.Corners.CENTER,
            new Coordinate<>(120.0, 72.0),
            Geometry.tileSide,
            Geometry.tileSide,
            0,
            false,
            false
    );
    public static Rectangle RedTargetB = new Rectangle(
            Rectangle.Corners.BACK_LEFT,
            Rectangle.Corners.CENTER,
            new Coordinate<>(96.0, 72.0 + Geometry.tileSide),
            Geometry.tileSide,
            Geometry.tileSide,
            0,
            false,
            false
    );
    public static Rectangle RedTargetC = new Rectangle(
            Rectangle.Corners.BACK_LEFT,
            Rectangle.Corners.CENTER,
            new Coordinate<>(120.0, 72.0 + (Geometry.tileSide * 2)),
            Geometry.tileSide,
            Geometry.tileSide,
            0,
            false,
            false
    );
    public static RadiusCircle RedWobbleGoalLeft = new RadiusCircle(
            new Coordinate<>(96.0, 24.0),
            4.0,
            4.1,
            true,
            true
    );
    public static RadiusCircle RedWobbleGoalRight = new RadiusCircle(
            new Coordinate<>(120.0, 24.0),
            4.0,
            4.1,
            true,
            true
    );
    public static RadiusCircle RedStartingStack = new RadiusCircle(
            new Coordinate<>(108.0, 48.0),
            2.5,
            2.6,
            true,
            true
    );
}
