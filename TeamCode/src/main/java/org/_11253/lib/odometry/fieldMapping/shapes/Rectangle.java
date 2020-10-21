package org._11253.lib.odometry.fieldMapping.shapes;

import org._11253.lib.odometry.fieldMapping.Coordinate;
import org._11253.lib.odometry.fieldMapping.Line;
import org._11253.lib.utils.math.Math;

/**
 * The most basic of the geometric shapes used in
 * the field mapping portion of this code.
 *
 * <p>
 * The four lines; top, right, bottom, and left, are
 * used for collision detection. Mostly everything else
 * in this class is fairly useless.
 * </p>
 */
public class Rectangle implements Shape{
    final Coordinate<Double> frontRight;
    final Coordinate<Double> backRight;
    final Coordinate<Double> frontLeft;
    final Coordinate<Double> backLeft;

    final Line top;
    final Line right;
    final Line bottom;
    final Line left;

    public Rectangle(Corners drawCorner,
                     Corners rotateCorner,
                     Coordinate<Double> startingPoint,
                     double xDraw,
                     double yDraw,
                     double rotationalAngle) {
        double xfr, xfl, xbr, xbl, yfr, yfl, ybr, ybl;
        double x = startingPoint.getX();
        double y = startingPoint.getY();
        switch (drawCorner) {
            case FRONT_RIGHT:
                xfr = x; yfr = y;
                xfl = x - xDraw; yfl = y;
                xbr = x; ybr = y - yDraw;
                xbl = x - xDraw; ybl = y - yDraw;
                break;
            case FRONT_LEFT:
                xfr = x + xDraw; yfr = y;
                xfl = x; yfl = y;
                xbr = x + xDraw; ybr = y - yDraw;
                xbl = x; ybl = y - yDraw;
                break;
            case BACK_RIGHT:
                xfr = x; yfr = y + yDraw;
                xfl = x - xDraw; yfl = y + yDraw;
                xbr = x; ybr = y;
                xbl = x - xDraw; ybl = y;
                break;
            case BACK_LEFT:
                xfr = x + xDraw; yfr = y + yDraw;
                xfl = x; yfl = y + yDraw;
                xbr = x + xDraw; ybr = y;
                xbl = x; ybl = y;
                break;
            default:
                throw new IllegalArgumentException("Invalid corner!");
        }
        // The order of coordinates is as follows. It's consistent with
        // all of the other code in this section...
        // 1. Front Right
        // 2. Front Left
        // 3. Back Right
        // 4. Back Left
        // Using array rotations, we can get a grand total of four
        // different possible orders, which are as follows...
        // 1. fr, fl, br, bl (zero rotation) (start)
        // 2. bl, fr, fl, br (one rotations)
        // 3. br, bl, fr, fl (two rotations)
        // 4. fl, br, bl, fr (three rotations)
        // 5. fr, fl, br, bl (four rotations) (back to base)
        // For each of those combinations (1, 2, 3, and 4), everything
        // is rotated around the first point, which is, in order...
        // Front right, back left, back right, front left.
        // And finally, in order to rotate everything back until
        // we've completed a full loop and everything is in the
        // correct position, we have to counter each and every one of
        // any of the potential rotations as so:
        // 1 rotation needs 3 more rotations.
        // 2 rotations needs 2 more rotations.
        // 3 rotations needs 1 more rotation.
        // 4 rotations is how many we need to complete a full loop.
        Coordinate<Double>[] positions = new Coordinate[] {
                new Coordinate<>(xfr, yfr),
                new Coordinate<>(xfl, yfl),
                new Coordinate<>(xbr, ybr),
                new Coordinate<>(xbl, ybl)
        };
        switch (rotateCorner) {
            case FRONT_RIGHT:
                // Front right is still the first position.
                // We don't need to rotate this back, as it stays in
                // our so-called "default" position the whole entire
                // time.
                positions = rotateArray(positions, 0);
                rotateAllPointsAroundFirstPoint(positions, rotationalAngle);
                break;
            case BACK_LEFT:
                // The array has been rotated once, making the
                // first position back left.
                // Needs 3 rotations to go back.
                positions = rotateArray(positions, 1);
                rotateAllPointsAroundFirstPoint(positions, rotationalAngle);
                positions = rotateArray(positions, 3);
                break;
            case BACK_RIGHT:
                // Two rotations - back right.
                // Needs two more rotations to go back.
                positions = rotateArray(positions, 2);
                rotateAllPointsAroundFirstPoint(positions, rotationalAngle);
                positions = rotateArray(positions, 2);
                break;
            case FRONT_LEFT:
                // Finally, front left.
                // Needs just a single rotation to get back to normal.
                positions = rotateArray(positions, 3);
                rotateAllPointsAroundFirstPoint(positions, rotationalAngle);
                positions = rotateArray(positions, 1);
                break;
            case CENTER:
                Coordinate<Double> midpoint = new Coordinate<>(
                        Math.average(positions[0].getX(), positions[3].getX()),
                        Math.average(positions[0].getY(), positions[3].getY())
                );
                positions[0] = rotatePoint(midpoint, positions[0], rotationalAngle);
                positions[1] = rotatePoint(midpoint, positions[1], rotationalAngle);
                positions[2] = rotatePoint(midpoint, positions[2], rotationalAngle);
                positions[3] = rotatePoint(midpoint, positions[3], rotationalAngle);
                break;
            default:
                throw new IllegalArgumentException("Invalid corner!");
        }
        // Now that all of our coordinates are back in the same place, we
        // have to set all of the final variables at the top part of this
        // file - most notably lines, so we can work with collision detection
        // later on when we get to more complex field mapping.
        frontRight = positions[0];
        frontLeft = positions[1];
        backRight = positions[2];
        backLeft = positions[3];
        // We still have one very important thing to do here - you guessed it...
        // Coming up with lines! Luckily for us, all four of those lines can
        // be defined as so.
        // |-----------|-----------------|
        // | LINE NAME | REQUIRED POINTS |
        // |-----------|-----------------|
        // | TOP       | FL, FR          |
        // | RIGHT     | FR, BR          |
        // | BOTTOM    | BR, BL          |
        // | LEFT      | BL, FL          |
        // |-----------|-----------------|
        top = new Line(frontLeft, frontRight);
        right = new Line(frontRight, backRight);
        bottom = new Line(backRight, backLeft);
        left = new Line(backLeft, frontLeft);
    }

    /**
     * Rotate a point around another point by a specified angle,
     * notated in radians. (Or at least I think so.)
     *
     * @param center the center point to rotate around - in other words,
     *               this is the "axis" or "origin."
     * @param point the point which should be rotated around the center.
     * @param angle the angle, in radians, depicting how far the point
     *              should be rotated around the center point.
     * @return the freshly rotated point.
     */
    public Coordinate<Double> rotatePoint(Coordinate<Double> center,
                                          Coordinate<Double> point,
                                          double angle) {
        double c_x = center.getX();
        double c_y = center.getY();
        double p_x = point.getX();
        double p_y = point.getY();
        // Apply the rotation matrix or whatever the FUCK it's called.
        // Rv = [ cos(theta), -sin(theta) ] * [ x ] = [ x cos(theta) - y sin(theta) ]
        //      [ sin(theta),  cos(theta) ]   [ y ]   [ x sin(theta) + y cos(theta) ]
        // x prime = x cos(theta) - y sin(theta)
        // y prime = x sin(theta) + y cos(theta)
        double x1 = p_x - c_x;
        double y1 = p_y - c_y;
        double x2 = x1 * Math.cos(angle) - y1 * Math.sin(angle);
        double y2 = x1 * Math.sin(angle) + y1 * Math.cos(angle);
        return new Coordinate<>(
                x2 + center.getX(),
                y2 + center.getY()
        );
    }

    private Coordinate<Double>[] rotateAllPointsAroundFirstPoint(Coordinate<Double>[] points,
                                                                double rotationFactor) {
        return new Coordinate[]{
                points[0],
                rotatePoint(points[0], points[1], rotationFactor),
                rotatePoint(points[0], points[2], rotationFactor),
                rotatePoint(points[0], points[3], rotationFactor)
        };
    }

    private Coordinate<Double>[] rotateArray(Coordinate<Double>[] arr,
                                             int reps) {
        if (reps > 3 || reps < 0) return null;
        while (reps > 0) {
            Coordinate<Double>[] newArr = new Coordinate[] {
                    arr[3],
                    arr[0],
                    arr[1],
                    arr[2]
            };
            arr = newArr;
            reps--;
        }
        return arr;
    }

    /**
     * Used to determine whether a given point is inside of the rectangle.
     *
     * @param point the point to check
     * @return whether or not the point is inside the rectangle
     */
    public boolean isPointInShape(Coordinate<Double> point) {
        double p_x = point.getX();
        double p_y = point.getY();
        double maxX = Math.max(
                Math.max(frontRight.getX(), frontLeft.getX()),
                Math.max(backRight.getX(), backLeft.getX())
        );
        double minX = Math.min(
                Math.min(frontRight.getX(), frontLeft.getX()),
                Math.min(backRight.getX(), backLeft.getX())
        );
        double maxY = Math.max(
                Math.max(frontRight.getY(), frontLeft.getY()),
                Math.max(backRight.getY(), backLeft.getY())
        );
        double minY = Math.min(
                Math.min(frontRight.getY(), frontLeft.getY()),
                Math.min(backRight.getY(), backLeft.getY())
        );
        boolean xValid = minX <= p_x && p_x <= maxX;
        boolean yValid = minY <= p_y && p_y <= maxY;
        return xValid && yValid;
    }

    public enum Corners {
        FRONT_RIGHT,
        BACK_RIGHT,
        FRONT_LEFT,
        BACK_LEFT,
        CENTER
    }
}
