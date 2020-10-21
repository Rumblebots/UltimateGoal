package org._11253.lib.odometry.fieldMapping.components;

/**
 * Coordinate, used to represent a position on a two-dimensional
 * and cartesian coordinate plane.
 *
 * <p>
 * As a good introduction to how field mapping works...
 * We're given a two-dimensional Euclidean space. The bottom right portion of the
 * playing field corresponds with the origin of the coordinate plane which we're
 * working on. These coordinate pairs are used all the time - whether it be denoting
 * the endpoints of a line - who knows. Regardless, each coordinate contains an X and
 * a Y value. These values are final - once set, they can't be changed, unless a new
 * coordinate is made. The very vast majority of interactions you'll have with this
 * library's field mapping utilities will use Coordinate (double), but you can feel
 * free to use anything you'd like. Although coordinates are, due to the nature of
 * the FTC competition and the parts surrounding it, notated in inches, you can convert
 * inches to any other unit you'd like for intermediary processing or even final
 * results. Regardless - who cares. You do you.
 * </p>
 * <p>
 * This class is parameterized in case the user of said class, for whatever reason, wants to use
 * an integer, or a double, or... whatever else - I don't give a flying fuck, honestly.
 * </p>
 *
 * @param <E>
 */
public class Coordinate<E> {
    /**
     * The X value of the given coordinate.
     *
     * <p>
     * This is usually (almost always, actually) a double, but can be any
     * other type of number (or even string probably, honestly) you'd like.
     * X, in a cartesian coordinate plane, usually represents horizontal
     * offset, and that's exactly how we use it here.
     * </p>
     */
    private final E x;

    /**
     * The Y value of the given coordinate.
     *
     * <p>
     * This is usually (almost always, actually) a double, but can be any
     * other type of number (or even string probably, honestly) you'd like.
     * Y, in a cartesian coordinate plane, usually represents vertical
     * offset, and that's exactly how we use it here.
     * </p>
     */
    private final E y;

    /**
     * Create a new coordinate with two given values - X and Y offsets.
     *
     * <p>
     * Remember, everything here is final - once set, it can't be unset
     * without another instance of Coordinate.
     * </p>
     *
     * @param x the horizontal offset from the origin.
     * @param y the vertical offset from the origin.
     */
    public Coordinate(E x, E y) {
        this.x = x;
        this.y = y;
    }

    public E getX() {
        return x;
    }

    public E getY() {
        return y;
    }

    /**
     * Add two coordinates together.
     *
     * <p>
     * Note that this strictly applies to doubles - no other type
     * of coordinate works here, and using one... well, it'll give
     * you an error. If you manage to get past that error, I'm not
     * sure if I should be surprised, scared, or both.
     * </p>
     * <p>
     * This ***IS NOT*** the average of two coordinates! It's just the
     * sum of them both. This can be used for a couple things - the first
     * I can think of would be adjusting relative coordinates.
     * </p>
     *
     * @param a the first coordinate pair.
     * @param b the second coordinate pair.
     * @return the sum of both of the coordinate pairs.
     */
    public static Coordinate<Double> addCoords(Coordinate<Double> a,
                                               Coordinate<Double> b) {
        return new Coordinate<>(
                a.getX() + b.getX(),
                a.getY() + b.getY()
        );
    }
}
