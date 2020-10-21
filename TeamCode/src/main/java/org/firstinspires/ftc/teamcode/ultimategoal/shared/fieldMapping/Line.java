package org.firstinspires.ftc.teamcode.ultimategoal.shared.fieldMapping;

import org._11253.lib.utils.math.Math;

/**
 * A line class, used most frequently to determine
 * whether or not collisions happen.
 *
 * <p>
 * I don't understand what the hell is going on (probably), so
 * for further reading, check this out.
 * https://www.topcoder.com/community/competitive-programming/tutorials/geometry-concepts-line-intersection-and-its-applications/
 * </p>
 */
public class Line {
    public Coordinate<Double> a;
    public Coordinate<Double> b;
    public double length;
    public double angle;

    public Line(Coordinate<Double> a,
                Coordinate<Double> b) {
        this.a = a;
        this.b = b;
        length = calculateDistance(a, b);
        double distanceX = b.getX() - a.getX();
        double distanceY = b.getY() - a.getY();
        // Implement a method of determining the angle from here.
    }

    public Line(Coordinate<Double> a,
                double length,
                double angle) {
        // Implement a way to do cool things here.
    }

    public static double calculateDistance(double x1,
                                           double y1,
                                           double x2,
                                           double y2) {
        return Math.sqrt(
                ((y2 - y1) * (y2 - y1)) +
                        ((x2 - x1) * (x2 - x1))
        );
    }

    public static double calculateDistance(Coordinate<Double> a,
                                           Coordinate<Double> b) {
        double x1 = a.getX();
        double y1 = a.getY();
        double x2 = b.getX();
        double y2 = b.getY();
        return calculateDistance(x1, y1, x2, y2);
    }

    /**
     * Check whether or not two lines collide at any point.
     *
     * <p>
     * Prior to reaching this stage of virtual collision detection,
     * check to make sure none of the following conditions are true...
     * <ul>
     *     <li>Lines are parallel.</li>
     *     <li>Line intersection is theoretically impossible due to distance.</li>
     *     <li>Line intersection is, for another reason, theoretically impossible.</li>
     * </ul>
     * </p>
     * <p>
     * A, B, and C are numbers which define each of the lines. Given two sets
     * of points, (x1, y1) and (x2, y2) we can determine the point of intersection
     * between the two lines. After finding this point of intersection, however,
     * we still have to make sure that that point of intersection is actually on
     * both of the lines, ensuring a virtual collision is indeed happening.
     * <pre>
     * A = y2-y1;
     * B = x1-x2;
     * C = Ax1+By1;
     * </pre>
     * </p>
     *
     * @param a the first line
     * @param b the second line
     * @return whether or not the lines intersect
     */
    public static boolean doesIntersect(Line a,
                                        Line b) {
        double a_a = a.b.getY() - a.a.getY();
        double a_b = a.a.getX() - a.b.getX();
        double a_c = (a_a * a.a.getX()) + (a_b * a.a.getY());
        double b_a = b.b.getY() - b.a.getY();
        double b_b = b.a.getX() - b.b.getX();
        double b_c = (b_a * b.a.getX()) + (b_b * b.a.getY());
        double det = (a_a * b_b) - (b_a * a_b);
        if (det == 0) {
            // Lines are parallel, no point in continuing.
            return false;
        } else {
            // Steps...
            // 1. Determine point of intersection with these formulas.
            // 2. Check if the X and Y coordinates exist on each of the lines.
            // 3. That's it! Yay!
            double i_x = ((b_b * a_c) - (a_b * b_c)) / det;
            double i_y = ((a_a * b_c) - (b_a * a_c)) / det;
            Coordinate<Double> pointOfIntersection = new Coordinate<>(i_x, i_y);
            return a.isPointOnLine(pointOfIntersection) &&
                    b.isPointOnLine(pointOfIntersection);
        }
    }

    /**
     * Check whether or not a given ordered pair lies on
     * the instanced line.
     *
     * <p>
     * "If your line segment goes from (x1, y1) to (x2, y2), then to
     * check if (x, y) is on that segment, you just need to check that
     * min(x1, x2) <= x <= max(x1, x2), and do the same thing for Y."
     * </p>
     *
     * @param point a given pair
     * @return whether or not that point is on this line
     */
    public boolean isPointOnLine(Coordinate<Double> point) {
        boolean xPass =
                Math.min(a.getX(), b.getX()) <= point.getX() &&
                        point.getX() <= Math.max(a.getX(), b.getX());
        boolean yPass =
                Math.min(a.getY(), b.getY()) <= point.getY() &&
                        point.getY() <= Math.max(a.getY(), b.getY());
        return xPass && yPass;
    }
}
