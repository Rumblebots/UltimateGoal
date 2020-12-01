package me.wobblyyyy.pathfinder.fieldMapping.components;

import org.jetbrains.annotations.NotNull;

/**
 * A two-dimensional coordinate with a Heading.
 *
 * @param <E>
 * @author Colin Robertson
 */
public class HeadingCoordinate<E> {
    /**
     * The X value of the coordinate.
     */
    private final E x;

    /**
     * The Y value of the coordinate.
     */
    private final E y;

    /**
     * The heading of the coordinate.
     *
     * <p>
     * Please note that all headings in the Pathfinder library SHOULD BE notated in
     * radians and not degrees.
     * </p>
     */
    private final E heading;

    /**
     * Create a new HeadingCoordinate of a given type.
     *
     * @param x       the x value.
     * @param y       the y value.
     * @param heading the heading (almost always in radians).
     */
    public HeadingCoordinate(E x, E y, E heading) {
        this.x = x;
        this.y = y;
        this.heading = heading;
    }

    /**
     * Get the X value.
     *
     * @return the x value.
     */
    public E getX() {
        return x;
    }

    /**
     * Get the Y value.
     *
     * @return the y value.
     */
    public E getY() {
        return y;
    }

    /**
     * Get the heading.
     *
     * @return the heading.
     */
    public E getHeading() {
        return heading;
    }

    /**
     * Get the basic coordinate (no heading).
     *
     * @return the basic coordinate without a heading.
     */
    public Coordinate<E> getCoordinate() {
        return new Coordinate<E>(x, y);
    }

    /**
     * A custom toString method which overrides Java's default toString method.
     *
     * @return the coordinate (with heading) as a string.
     */
    @NotNull
    @Override
    public String toString() {
        // Format:
        // { +
        // X: (x) +
        // Y: (y) +
        // H: (h) +
        // }
        // Example:
        // {X: 10, Y: 12, H: 22.2}
        return "{" +
                "X: " +
                x.toString() + " " +
                "Y: " +
                y.toString() + " " +
                "H: " +
                heading.toString() + "}";
    }
}
