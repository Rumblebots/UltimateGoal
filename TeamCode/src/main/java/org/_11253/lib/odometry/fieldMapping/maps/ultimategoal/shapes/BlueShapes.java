package org._11253.lib.odometry.fieldMapping.maps.ultimategoal.shapes;

import org._11253.lib.odometry.fieldMapping.Geometry;
import org._11253.lib.odometry.fieldMapping.components.Coordinate;
import org._11253.lib.odometry.fieldMapping.shapes.RadiusCircle;
import org._11253.lib.odometry.fieldMapping.shapes.Rectangle;

public class BlueShapes {
    public static Rectangle BlueTargetA = new Rectangle(
            Rectangle.Corners.BACK_LEFT,
            Rectangle.Corners.CENTER,
            new Coordinate<>(0.0, 72.0),
            Geometry.tileSide,
            Geometry.tileSide,
            0,
            false,
            false
    );
    public static Rectangle BlueTargetB = new Rectangle(
            Rectangle.Corners.BACK_LEFT,
            Rectangle.Corners.CENTER,
            new Coordinate<>(Geometry.tileSide, 72.0 + Geometry.tileSide),
            Geometry.tileSide,
            Geometry.tileSide,
            0,
            false,
            false
    );
    public static Rectangle BlueTargetC = new Rectangle(
            Rectangle.Corners.BACK_LEFT,
            Rectangle.Corners.CENTER,
            new Coordinate<>(0.0, 72.0 + (Geometry.tileSide * 2)),
            Geometry.tileSide,
            Geometry.tileSide,
            0,
            false,
            false
    );
    public static RadiusCircle BlueWobbleGoalLeft = new RadiusCircle(
            new Coordinate<>(24.0, 24.0),
            4.0,
            4.1,
            true,
            true
    );
    public static RadiusCircle BlueWobbleGoalRight = new RadiusCircle(
            new Coordinate<>(48.0, 24.0),
            4.0,
            4.1,
            true,
            true
    );
    public static RadiusCircle BlueStartingStack = new RadiusCircle(
            new Coordinate<>(36.0, 48.0),
            2.5,
            2.6,
            true,
            true
    );;
}
