package me.wobblyyyy.pathfinder.fieldMapping.shapes;

import me.wobblyyyy.pathfinder.fieldMapping.components.Coordinate;

/**
 * All squares are rectangles, but not all rectangles are squares.
 *
 * <p>
 * Even though there's only one less parameter - who cares? If you're confused
 * about what's going on here, go check out the Rectangle class, not this one.
 * </p>
 */
public class Square extends Rectangle {
    /**
     * The one and only... square constructor!
     *
     * @param drawCorner           which corner the rectangle should be drawn from. This is NOT
     *                             always the same corner which the rectangle will be rotated from,
     *                             however - just the corner it'll be drawn from. X and Y are relative
     *                             to this corner, meaning top right's Y draw would have a different
     *                             impact than bottom left's Y draw - one (top right) would be negative,
     *                             and the other (bottom left) would be positive.
     * @param rotateCorner         the corner which the rectangle will be rotated from. You don't need
     *                             to rotate the rectangle, by the way - it's an entirely optional step.
     *                             If you don't want to rotate the rectangle, you can use any corner
     *                             (CENTER or maybe your drawCorner) as the corner of rotation, and set
     *                             the rotation angle to 0, representing a net change of zero rotation.
     * @param startingPoint        the coordinate where the shape will be drawn from. This point is
     *                             directly correlative to drawCorner - having a drawCorner of top right
     *                             means that this code will interpret the starting point as the top right
     *                             corner of the rectangle, and thus draw the rectangle as so. Make sure that
     *                             this is the correct corner. Assuming you're familiar with something such as
     *                             JavaScript's "canvas" functionality, you will (most often) want to use the
     *                             top left corner as a starting point and figure out the coordinate of
     *                             said corner.
     * @param draw                how far, in the X dimension, the rectangle should be drawn. Note that this
     *                             is relative to which corner the rectangle is being drawn from. Having a draw
     *                             corner of top left means that both X and Y draws are negative.
     * @param rotationalAngle      the angle at which the entire rectangle should be rotate from. I believe that
     *                             this angle is in radians, and any code you write using this angle should reflect
     *                             that. If you have an angle which is in degrees, Java's native math class should
     *                             include a function for converting degrees to radians.
     * @param isCollidableExterior whether or not the exterior of the rectangle is collidable. A collidable shape
     *                             is recognized by the collision detection system, and the robot will intentionally
     *                             not steer right into an exterior collidable object.
     * @param isCollidableInterior whether or not the robot can still move while inside a shape. For example, the main
     *                             field of the FTC challenge has a non-collidable interior, meaning the robot can
     *                             still move while inside of the shape / zone.
     */
    public Square(Corners drawCorner,
                  Corners rotateCorner,
                  Coordinate<Double> startingPoint,
                  double draw,
                  double rotationalAngle,
                  boolean isCollidableExterior,
                  boolean isCollidableInterior) {
        super(
                drawCorner,
                rotateCorner,
                startingPoint,
                draw,
                draw,
                rotationalAngle,
                isCollidableExterior,
                isCollidableInterior
        );
    }
}
