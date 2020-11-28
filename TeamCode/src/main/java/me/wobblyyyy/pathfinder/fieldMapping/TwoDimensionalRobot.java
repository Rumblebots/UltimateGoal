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

    /** How wide, in terms of X, the robot is. */
    private final double widthX;
    /** How wide, in terms of Y, the robot is. */
    private final double widthY;
    /** The heading at which the robot begins. */
    private final double initialHeading;
    /** HALF of how wide, in terms of X, the robot is. */
    private final double halfX;
    /** HALF of how wide, in terms of Y, the robot is. */
    private final double halfY;

    /** How far (absolute) the FR corner is from the center. */
    private final Coordinate<Double> frTrail;
    /** How far (absolute) the FL corner is from the center. */
    private final Coordinate<Double> flTrail;
    /** How far (absolute) the BR corner is from the center. */
    private final Coordinate<Double> brTrail;
    /** How far (absolute) the BL corner is from the center. */
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
     * @param widthX how wide the robot is, in terms of x
     * @param widthY how wide the robot is, in terms of y
     * @param initialHeading the heading the robot starts facing at
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
     * @param pose the RoadRunner pose which the robot is currently in.
     *             RoadRunner poses basically consist of X, Y, and
     *             HEADING - not much more.
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
    }
}
