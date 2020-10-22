package org._11253.lib.odometry.fieldMapping.components;

public class HeadingCoordinate<E> {
    private final E x;
    private final E y;
    private final E heading;

    public HeadingCoordinate(E x, E y, E heading) {
        this.x = x;
        this.y = y;
        this.heading = heading;
    }

    public E getX() {
        return x;
    }

    public E getY() {
        return y;
    }

    public E getHeading() {
        return heading;
    }

    public Coordinate<E> getCoordinate() {
        return new Coordinate<E>(x, y);
    }
}
