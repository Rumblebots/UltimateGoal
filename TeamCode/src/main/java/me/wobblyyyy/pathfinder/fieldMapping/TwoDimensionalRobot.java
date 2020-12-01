package me.wobblyyyy.pathfinder.fieldMapping;

import me.wobblyyyy.pathfinder.fieldMapping.components.Coordinate;
import me.wobblyyyy.pathfinder.fieldMapping.components.HeadingCoordinate;
import me.wobblyyyy.pathfinder.fieldMapping.shapes.Rectangle;
import me.wobblyyyy.pathfinder.fieldMapping.zones.Zone;
import me.wobblyyyy.pathfinder.fieldMapping.zones.commonZones.Robot;

/**
 * A two-dimensional robot. It's very cool, I know.
 *
 * <p>
 * For lack of better words to describe it, this is a glorified rectangle with
 * a special name. Every instance of TwoDimensionalRobot has a zone named
 * "2dRobot."
 * </p>
 *
 * @author Colin Robertson
 */
public class TwoDimensionalRobot {
    /**
     * A rectangle, updated whenever the robot moves, that
     * represents where, on our virtual field map, the robot
     * is at all times.
     *
     * <p>
     * This rectangle is named hitbox for one reason - it
     * represents the physical dimensions of the robot, and
     * at what point said dimensions will get in the way of
     * the robot moving any further in a specific direction.
     * </p>
     * <p>
     * If you'd like more information on the inner workings
     * of the 2d robot's hitbox, go ahead and check out the
     * rectangle class - it's very cool, I promise.
     * </p>
     */
    public Rectangle hitbox;

    /**
     * Robot/specialized robot zone.
     *
     * <p>
     * This is yet another layer of abstraction of everything that's going on.
     * Robot is just a special zone named the robot.
     * </p>
     */
    public Robot robot;

    /**
     * The rectangular zone itself.
     */
    public Zone zone;

    /**
     * The robot's pose.
     */
    public HeadingCoordinate<Double> pose;

    /**
     * The robot's current position, without heading.
     */
    public Coordinate<Double> position;

    /**
     * The robot's current heading.
     */
    public double heading;

    /**
     * How wide, in terms of X, the robot is.
     */
    private final double widthX;
    /**
     * How wide, in terms of Y, the robot is.
     */
    private final double widthY;
    /**
     * The heading at which the robot begins.
     */
    private final double initialHeading;
    /**
     * HALF of how wide, in terms of X, the robot is.
     */
    private final double halfX;
    /**
     * HALF of how wide, in terms of Y, the robot is.
     */
    private final double halfY;

    /**
     * How far (absolute) the FR corner is from the center.
     */
    private final Coordinate<Double> frTrail;
    /**
     * How far (absolute) the FL corner is from the center.
     */
    private final Coordinate<Double> flTrail;
    /**
     * How far (absolute) the BR corner is from the center.
     */
    private final Coordinate<Double> brTrail;
    /**
     * How far (absolute) the BL corner is from the center.
     */
    private final Coordinate<Double> blTrail;

    /**
     * Create a new instance of the mobile 2d robot.
     *
     * <p>
     * This can still be moved after it's created! It moves by creating a new
     * zone every time, but that's not the point. The point is, this is probably
     * the only implementation of a zone you can move that's included in this
     * library. That's pretty fucking cool, right? I know it is.
     * </p>
     *
     * @param widthX         how wide the robot is, in terms of x. This is measured in INCHES.
     * @param widthY         how wide the robot is, in terms of y. This is measured in INCHES.
     * @param initialHeading the heading the robot starts facing at. Headings are, as with most
     *                       of this library, notated in RADIANS, not degrees. If you don't know
     *                       how radians work, and you don't really want to learn, you can use
     *                       the {@link Geometry#toRadians(double)} method to convert degrees
     *                       into radians, thus making it a bit easier to work with.
     */
    public TwoDimensionalRobot(double widthX,
                               double widthY,
                               double initialHeading) {
        this.widthX = widthX;
        this.widthY = widthY;
        this.initialHeading = initialHeading;
        halfX = widthX / 2;
        halfY = widthY / 2;

        frTrail = new Coordinate<>(halfX, halfY);
        flTrail = new Coordinate<>(-halfX, halfY);
        brTrail = new Coordinate<>(halfX, -halfY);
        blTrail = new Coordinate<>(-halfX, -halfY);
        update(new HeadingCoordinate<Double>(0.0, 0.0, 0.0));
    }

    /**
     * Update the position, and thus the hitbox, of the robot.
     *
     * <p>
     * There's a few main components of updating the robot's position.
     * Assuming we already have a pose which roughly represents the
     * robot's position, and we've passed that pose, as a parameter,
     * to this function, we have to...
     * <ul>
     *     <li>Extract the X coordinate from the pose.</li>
     *     <li>Extract the Y coordinate from the pose.</li>
     *     <li>Extract the heading from the pose.</li>
     *     <li>Create a new rectangle with this information.</li>
     *     <li>Set "hitbox" to this newly created rectangle.</li>
     *     <li>Update the publically-accessible coordinate.</li>
     *     <li>Update the publically-accessible heading.</li>
     *     <li>Update the publically-accessible pose.</li>
     * </ul>
     * </p>
     * <p>
     * X and Y are used in a fairly linear matter. Heading is used to rotate
     * each of the points on the robot around a single point.
     * </p>
     *
     * @param pose the HeadingCoordinate which the robot is at. Remember, all of
     *             the geometry-related math in this project uses RADIANS, not
     *             degrees. HeadingCoordinate(s) should report values in radians, and
     *             not values in degrees. If you use degrees instead of radians, a
     *             lot of math will be wrong, and you'll likely be very confused
     *             as to what you did wrong.
     */
    public void update(HeadingCoordinate<Double> pose) {
        double x = pose.getX();
        double y = pose.getY();
        position = new Coordinate<>(x, y);
        heading = pose.getHeading();
        hitbox = new Rectangle(
                Rectangle.Corners.FRONT_RIGHT,
                Rectangle.Corners.CENTER,
                Coordinate.addCoords(position, frTrail),
                widthX,
                widthY,
                heading,
                true,
                true
        );
        robot = new Robot(hitbox);
        zone = robot;
    }
}
