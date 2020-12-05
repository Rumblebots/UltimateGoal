package me.wobblyyyy.pathfinder.fieldMapping.shapes;

import me.wobblyyyy.intra.ftc2.utils.jrep.ListWrapper;
import me.wobblyyyy.pathfinder.fieldMapping.Geometry;
import me.wobblyyyy.pathfinder.fieldMapping.components.Component;
import me.wobblyyyy.pathfinder.fieldMapping.components.Coordinate;
import me.wobblyyyy.pathfinder.fieldMapping.components.countable.GigaArc;
import me.wobblyyyy.pathfinder.fieldMapping.components.countable.Line;

import java.util.ArrayList;

/**
 * An implementation of GigaArc, which is, as you probably
 * already guessed, just a sneaky way of saying "circle."
 *
 * <p>
 * Circles are a lot more complex mathematically than their linear
 * counterparts. I'm writing a lot of code that doesn't really do much
 * right now and I'm not near any pencils and paper so I don't feel like
 * working out the math to figure out how to detect things like line-circle
 * collisions and even circle-circle collisions, but I'm sure I'll get
 * around to that somewhere in the near enough future.
 * </p>
 * <p>
 * If you'll notice, this circle actually has two component circles contained
 * within it - one labelled "circle," and the other; "hitbox." My reasoning
 * behind doing this has nothing to do with liking to type out GigaArc (GigaArc
 * GigaArc GigaArc GigaArc GigaArc GigaArc) and more to do with the fact that
 * circles are a tad bit finicky and I have a feeling the hitbox might
 * come in handy for future collision detection (and hopefully avoidance).
 * </p>
 *
 * @author Colin Robertson
 */
public class RadiusCircle implements Shape {
    private final GigaArc circle;
    private final GigaArc hitbox;

    private final boolean _ICE;
    private final boolean _ICI;

    public final Coordinate<Double> center;
    public final double radius;
    public final double hitboxRadius;

    public RadiusCircle(Coordinate<Double> center,
                        double radius,
                        double hitboxRadius,
                        boolean isCollidableExterior,
                        boolean isCollidableInterior) {
        circle = new GigaArc(center, radius);
        hitbox = new GigaArc(center, hitboxRadius);

        _ICE = isCollidableExterior;
        _ICI = isCollidableInterior;

        this.center = center;
        this.radius = radius;
        this.hitboxRadius = hitboxRadius;
    }

    @Override
    public boolean isCollidableExterior() {
        return _ICE;
    }

    @Override
    public boolean isCollidableInterior() {
        return _ICI;
    }

    /**
     * See whether or not a point is inside of a shape.
     *
     * @param point the point to check. This could be, for example, one of the end points
     *              of a rectangle, a square, the mid-point of a circle, the mid-point
     *              (or even quarter-point (or even eighth-point)) of a line.
     * @return whether the point is in the circle.
     */
    @Override
    public boolean isPointInShape(Coordinate<Double> point) {
        return hitbox.isPointInCircle(point);
    }

    /**
     * See whether or not a point is near (within the hitbox of) a
     * certain circle.
     *
     * @param point the point to test.
     * @return whether or not the point is within the circle's hitbox area.
     */
    public boolean isPointNearShape(Coordinate<Double> point) {
        return hitbox.isPointInCircle(point);
    }

    /**
     * Get whether or not a given point is within a certain distance
     * of a circle.
     *
     * @param point     the point to check for.
     * @param tolerance how far away the point can be (max).
     * @return whether the point meets our qualifications and wins a grand prize.
     */
    public boolean isPointNearShape(Coordinate<Double> point,
                                    double tolerance) {
        return circle.isPointNearCircle(point, tolerance) ||
                circle.isPointInCircle(point);
    }

    /**
     * A method to determine whether or not a line intersects with a
     * circle.
     *
     * <p>
     * Just to pre-write some ideas for future use, this could possibly be
     * attempted by doing the following, assuming math is out of the question.
     * <pre>
     * 1. Create another (larger) hitbox circle to determine if it's worth
     *    even looking for an intersection in the first place.
     * 2. Find the closest point on the line to the radius. If that point is
     *    less than or equal to the radius of the circle (not the hitbox),
     *    then that line must be touching.
     * 3. Check on the line to make sure the point in question is in fact
     *    on the line and not floating off in space. We'll have to see how
     *    this plays out, however - oh well.
     * </pre>
     * </p>
     *
     * @return whether or not a line intersects with a circle.
     */
    public boolean doesLineIntersectWithCircle(Line line) {
        Line workingLine = line;
        int checkLimit = 15;
        double tolerance = 0.1;
        while (checkLimit > 0) {
            Coordinate<Double> a = workingLine.a,
                    b = workingLine.b,
                    midpoint = workingLine.midpoint;
            // If any of the points - whether it be the midpoint,
            // or either of the end points - are inside of the
            // circle, we automatically know that the line must
            // intersect with the circle at some point - otherwise,
            // how exactly would that point get there?
            if (isPointNearShape(a, tolerance) ||
                    isPointNearShape(b, tolerance) ||
                    isPointNearShape(midpoint, tolerance)) {
                return true;
            }
            // We have two possible conditions coming up.
            // Distance from A is the greatest.
            // Distance from B is the greatest.
            // Regardless of what it is, we're going to make another
            // line out of whichever is closer and the current line's
            // midpoint and set workingLine to that and then repeat the
            // process all over again. Exciting, right?!
            double distanceFromA = Geometry.getDistance(a, circle.center);
            double distanceFromB = Geometry.getDistance(b, circle.center);
            if (distanceFromA > distanceFromB) {
                // A is greater - we want to use B.
                workingLine = new Line(b, midpoint);
            } else {
                // B is greater - we want to use A.
                workingLine = new Line(a, midpoint);
            }
            checkLimit--;
        }
        return false;
    }

    /**
     * Check whether or not a given line enters the shape in question.
     *
     * <p>
     * This can be solved pretty simply as well. Hopefully.
     * </p>
     *
     * @param line the line to check.
     * @return whether or not the line enters.
     */
    @Override
    public boolean doesLineEnterShape(Line line) {
        return false;
    }

    @Override
    public Coordinate<Double> getCenterPoint() {
        return circle.center;
    }

    /**
     * Gets how many components (circles) there are.
     *
     * @return 2
     */
    @Override
    public int getComponentCount() {
        return 2;
    }

    /**
     * Gets (all two) of the lovely circles used for this shape.
     *
     * @return a ListWrapper with our two circles.
     */
    @Override
    public ListWrapper<Component> getComponents() {
        return new ListWrapper<>(
                new ArrayList<Component>() {{
                    add(circle);
                    add(hitbox);
                }}
        );
    }
}
