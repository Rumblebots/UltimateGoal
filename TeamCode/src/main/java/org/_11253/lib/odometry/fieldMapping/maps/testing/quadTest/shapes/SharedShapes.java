package org._11253.lib.odometry.fieldMapping.maps.testing.quadTest.shapes;

import org._11253.lib.odometry.fieldMapping.Geometry;
import org._11253.lib.odometry.fieldMapping.shapes.Rectangle;

public class SharedShapes {
    public static Rectangle SharedBlueSide = new Rectangle(
            Rectangle.Corners.BACK_LEFT,
            Rectangle.Corners.CENTER,
            Geometry.bottomLeft,
            72,
            144,
            0,
            false,
            false
    );
    public static Rectangle SharedRedSide = new Rectangle(
            Rectangle.Corners.BACK_LEFT,
            Rectangle.Corners.CENTER,
            Geometry.midBottom,
            72,
            144,
            0,
            false,
            false
    );
    public static Rectangle SharedFarSide = new Rectangle(
            Rectangle.Corners.BACK_LEFT,
            Rectangle.Corners.CENTER,
            Geometry.midLeft,
            144,
            72,
            0,
            false,
            false
    );
    public static Rectangle SharedCloseSide = new Rectangle(
            Rectangle.Corners.BACK_LEFT,
            Rectangle.Corners.CENTER,
            Geometry.bottomLeft,
            144,
            72,
            0,
            false,
            false
    );
}
