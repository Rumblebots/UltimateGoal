package me.wobblyyyy.pathfinder.fieldMapping.components.countable;

import me.wobblyyyy.intra.ftc2.utils.math.Math;
import me.wobblyyyy.pathfinder.fieldMapping.components.Component;
import me.wobblyyyy.pathfinder.fieldMapping.components.Coordinate;

/**
 * A line class, used most frequently to determine
 * whether or not collisions happen.
 *
 * <p>
 * Field mapping isn't a concept that there's been a lot
 * of research or information on among the FTC community, so 
 * I can't say I'm sure that this is the most optimal way
 * to do what we're trying to do here. Building an array/list
 * of different shapes & lines and iterating over the entire list,
 * manually checking for collisions with the robot, might be a
 * little bit too math-intensive for our poor control hub to
 * handle, but we'll see.
 * </p>
 * <p>
 * I don't understand what the hell is going on (probably), so
 * for further reading, check this out.
 * https://www.topcoder.com/community/competitive-programming/tutorials/geometry-concepts-line-intersection-and-its-applications/
 * </p>
 */
public class Line implements Component {
    public final Coordinate<Double> a;
    public final Coordinate<Double> b;
    public final Coordinate<Double> midpoint;
    public final double length;
    public final double angle = 0; // TODO implement this

    public Line(Coordinate<Double> a,
                Coordinate<Double> b) {
        this.a = a;
        this.b = b;
        length = calculateDistance(a, b);
        double distanceX = b.getX() - a.getX();
        double distanceY = b.getY() - a.getY();
        midpoint = new Coordinate<>(
                Math.average(a.getX(), b.getX()),
                Math.average(a.getY(), b.getY())
        );
        // Implement a method of determining the angle from here.
    }

    /**
     * A function, using the well-known and
     * not-very-well-liked distance formula, designed
     * to calculate... well, distance.
     *
     * @param x1 coordinate 1's x value 
     * @param y1 coordinate 1's y value 
     * @param x2 coordinate 2's x value
     * @param y2 coordinate 2's y value 
     */
    public static double calculateDistance(double x1,
                                           double y1,
                                           double x2,
                                           double y2) {
        return Math.sqrt(
                ((y2 - y1) * (y2 - y1)) +
                        ((x2 - x1) * (x2 - x1))
        );
    }

    /**
     * Overloaded function so we don't have to put
     * in as many parameters for the regular calculateDistance
     * method.
     *
     * @param a the first Coordinate
     * @param b the second Coordinate
     */
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
        // I'm sure none of the below code makes
        // any sense at all, but I'll do my best
        // to ensure I explain at least a little
        // bit of what's going on here.
        // Note that these arent' my formulas; so
        // if anything is wrong, it's not my fault.
        // Each of the first six doubles is a part
        // of the A, B, C trio mentioned in the
        // JavaDoc for this method.
        // The first letter, such as (A_a) or (B_a)
        // indicates whether the next letter, following
        // the underscore, is a part of the FIRST (a) or
        // the SECOND (b) group of A, B, and C.
        double a_a = a.b.getY() - a.a.getY();
        double a_b = a.a.getX() - a.b.getX();
        double a_c = (a_a * a.a.getX()) + (a_b * a.a.getY());
        double b_a = b.b.getY() - b.a.getY();
        double b_b = b.a.getX() - b.b.getX();
        double b_c = (b_a * b.a.getX()) + (b_b * b.a.getY());
        // "det" is a pretty cool number. I have absolutely
        // no clue what the fuck "det" means, and I'm way
        // too lazy to figure out the meaning of it - but,
        // anyways...
        // If det is zero, both of the lines are parallel and
        // do not intersect at any point. If they're parallel,
        // there's no point in continuing, so we just return
        // false automatically.
        double det = (a_a * b_b) - (b_a * a_b);
        if (det == 0) {
            // Lines are parallel, no point in continuing.
            return false;
        } else {
            // Steps...
            // 1. Determine point of intersection with these formulas.
            // 2. Check if the X and Y coordinates exist on each of the lines.
            // 3. That's it! Yay!
            // Once again, these formulas are not mine - I
            // can't and don't accept any responsibility if
            // this doesn't work. However, in the unlikely event
            // that this ends up working on the first try, this
            // was all code I came up with while I was sleeping.
            // That's right - I'm so good at coding I can do math
            // I learned in 9th grade and forgot about later while
            // I'm not even awake.
            double i_x = ((b_b * a_c) - (a_b * b_c)) / det; // x coord 
            double i_y = ((a_a * b_c) - (b_a * a_c)) / det; // y coord
            // Create a new coordinate based on our X and Y values. 
            Coordinate<Double> pointOfIntersection = new Coordinate<>(i_x, i_y);
            // Evaluate whether the point we've found is on
            // both of our lines. If it is, there is a collision.
            // If it's not, there is no collision/intersection.
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
     * <p>
     * In order to check whether or not two lines collide at any
     * point, we have to first ensure that there is indeed a point
     * of collision. This may, in the near future, need to be adjusted
     * using comparators to allow for certain degrees of tolerance
     * to ensure that points are very nearly on the line rather than
     * on the line - simply checking if a point is on a given line could
     * sometimes be inaccurate.
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
