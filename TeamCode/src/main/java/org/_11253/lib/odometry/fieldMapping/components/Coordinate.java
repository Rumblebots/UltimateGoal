package org._11253.lib.odometry.fieldMapping.components;

/**
 * Coordinate, used to represent a position on a two-dimensional
 * and cartesian coordinate plane.
 *
 * <p>
 * This class is parameterized in case the user of said class, for whatever reason, wants to use
 * an integer, or a double, or... whatever else - I don't give a flying fuck, honestly.
 * </p>
 *
 * @param <E>
 */
public class Coordinate<E> {
    private E x;
    private E y;

    public Coordinate(E x, E y) {
        this.x = x;
        this.y = y;
    }

    public void setX(E x) {
        this.x = x;
    }

    public void setY(E y) {
        this.y = y;
    }

    public E getX() {
        return x;
    }

    public E getY() {
        return y;
    }

    public static Coordinate<Double> addCoords(Coordinate<Double> a,
                                               Coordinate<Double> b) {
        return new Coordinate<>(
                a.getX() + b.getX(),
                a.getY() + b.getY()
        );
    }
}
