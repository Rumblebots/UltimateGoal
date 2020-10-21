package org._11253.lib.odometry.fieldMapping.shapes;

import org._11253.lib.odometry.fieldMapping.Coordinate;

/**
 * Low-level interface which all shapes should implement.
 *
 * <p>
 * Used to mandate that each and every shape has a couple
 * required features that make shapes function in the way
 * this implementation of field mapping requires them to.
 * </p>
 */
public interface Shape {
    /**
     * Is this shape a collidable object?
     *
     * <p>
     * If this value is true, the robot will register that
     * it is in contact (or will be in contact) with a solid
     * object. If this is false, even if the robot is right
     * in the middle of a shape's boundaries, the robot will
     * not register that it's in contact with a shape.
     * </p>
     */
    boolean isCollidable = true;

    /**
     * Is a given point contained within the shape?
     *
     * @param point the point to check
     * @return whether or not the point is inside the shape
     */
    boolean isPointInShape(Coordinate<Double> point);
}
