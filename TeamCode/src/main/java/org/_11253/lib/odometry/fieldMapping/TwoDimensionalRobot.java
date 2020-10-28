package org._11253.lib.odometry.fieldMapping;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import org._11253.lib.odometry.fieldMapping.components.Coordinate;
import org._11253.lib.odometry.fieldMapping.shapes.Rectangle;
import org._11253.lib.odometry.fieldMapping.zones.commonZones.Robot;
import org._11253.lib.odometry.fieldMapping.zones.Zone;

/**
 * A two-dimensional robot. It's very cool, I know.
 *
 * <p>
 * For further reading, see below.
 * https://en.wikipedia.org/wiki/Rotation_matrix
 * Basically, let's see.
 * <pre>
 * Rv = [ cos(theta), -sin(theta) ] * [ x ] = [ x cos(theta) - y sin(theta) ]
 *      [ sin(theta),  cos(theta) ]   [ y ]   [ x sin(theta) + y cos(theta) ]
 * x prime = x cos(theta) - y sin(theta)
 * y prime = x sin(theta) + y cos(theta)
 * </pre>
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
     * If you'd like more information on the innter workings
     * of the 2d robot's hitbox, go ahead and check out the
     * rectangle class - it's very cool, I promise.
     * </p>
     */
    public Rectangle hitbox;
    public Robot robot;

    public Zone zone;

    public Pose2d pose;
    public Coordinate<Double> position;
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
    public void update(Pose2d pose) {
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
