package me.wobblyyyy.pathfinder.fieldMapping.shapes;

import me.wobblyyyy.intra.ftc2.utils.jrep.ListWrapper;
import me.wobblyyyy.pathfinder.fieldMapping.components.Component;
import me.wobblyyyy.pathfinder.fieldMapping.components.Coordinate;
import me.wobblyyyy.pathfinder.fieldMapping.components.countable.Line;

/**
 * Low-level interface which all shapes should implement.
 *
 * <p>
 * Used to mandate that each and every shape has a couple
 * required features that make shapes function in the way
 * this implementation of field mapping requires them to.
 * </p>
 * <p>
 * Any shape used in the context of geometric field mapping
 * should implement this interface, ensuring that they all
 * contain some essential values, and a generic & abstract
 * interface can be used in place of more specific variables.
 * When making a map containing several pre-defined shapes,
 * you might want to use a data structure such as an
 * ArrayList. While you could have one array list for every
 * single type of shape used on the map, or you could use
 * Object, as all non-Object classes share that as a common
 * extension, it's a little bit more convenient to just use
 * our lovely interface fellow, Shape.
 * </p>
 * <p>
 * A shape is very simply defined as any geometric shape.
 * Rectangles, circles, etc, count as shapes. Most shapes will
 * be rectangles, as they are, by far, the most commonly-used
 * shape in FTC field mapping. Circles do exist as well. I haven't
 * gotten around to implementing ovals or whatever else, so, for
 * the most part, just stick to the fucking rectangles.
 * </p>
 */
public interface Shape {
    /**
     * Is this shape a collidable object from the exterior?
     *
     * <p>
     * If this value is true, the robot will register that
     * it is in contact (or will be in contact) with a solid
     * object. If this is false, even if the robot is right
     * in the middle of a shape's boundaries, the robot will
     * not register that it's in contact with a shape.
     * </p>
     */
    boolean isCollidableExterior();

    /**
     * Is this shape a collidable object from the interior?
     *
     * <p>
     * If this value is true, the robot will register that
     * it is in contact (or will be in contact) with a solid
     * object. If this is false, even if the robot is right
     * in the middle of a shape's boundaries, the robot will
     * not register that it's in contact with a shape.
     * </p>
     */
    boolean isCollidableInterior();

    /**
     * Is a given point contained within the shape?
     *
     * <p>
     * The implementation of this function will be different
     * depending on a. the shape, and b. the coder. However,
     * my suggested implementation for some of the most common
     * shapes would be as follows.
     * <ul>
     * <li>Rectangle: get the minimum and maximum for both X
     *     Y values of the rectangle's four bounds, and, from
     *     there, determine whether or not the point is inside
     *     of those four bounds.</li>
     * <li>Square: do the same thing as with the rectangle.</li>
     * <li>Circle: using the center of the circle, find the
     *     radius of said circle. And using that radius, compare
     *     that radius to the distance from the center of the
     *     circle to determine whether or not that given point
     *     could possibly be contained within that circle.</li>
     * </ul>
     * </p>
     *
     * @param point the point to check. This could be, for example, one of the end points
     *              of a rectangle, a square, the mid-point of a circle, the mid-point
     *              (or even quarter-point (or even eighth-point)) of a line.
     * @return whether or not the point is inside the shape
     */
    boolean isPointInShape(Coordinate<Double> point);

    /**
     * Does a specific line enter the shape at any point?
     *
     * @param line the line to check.
     * @return whether or not the line enters the shape.
     */
    boolean doesLineEnterShape(Line line);

    Coordinate<Double> getCenterPoint();

    /**
     * Gets a count (set up by the developer of the shape) that
     * details how many components are contained in that shape.
     *
     * <p>
     * A component is defined as anything that can be represented on
     * a two-dimensional coordinate plane, using two dimensions - both
     * X and Y. Examples of components include...
     * <ul>
     *     <li>A line.</li>
     *     <li>A circle.</li>
     *     <li>An arc.</li>
     *     <li>An oval.</li>
     * </ul>
     * ... or anything not listed above that meets the criteria for
     * defining a component.
     * </p>
     * <p>
     *     It's up to the developer to decide how many components a
     *     part has. These components aren't used for anything important -
     *     mostly just debugging and telemetry purposes, so it doesn't matter
     *     if said developer (most likely me) is 100% accurate, but I guess
     *     it's just... nice to have, you know?
     * </p>
     *
     * @return how many components are in that shape.
     */
    int getComponentCount();

    /**
     * Get all of the components used in the shape.
     *
     * <p>
     * This is useful, and really only useful, in runtime optimizations on
     * maps that have a lot of components on them. Say, for example, you have
     * a grand total of 1,000 rectangles and squares combined. That is, at the
     * very least, 4,000 components on the field. In order to save some processing
     * power, and not have to manually iterate over each and every single one of
     * those components, we can check to see if the component is close enough to
     * the robot to warrant doing anything related to it - thus (hopefully) saving
     * us a fair bit of processing power.
     * </p>
     *
     * @return a list of the components used in the shape.
     */
    ListWrapper<Component> getComponents();
}
