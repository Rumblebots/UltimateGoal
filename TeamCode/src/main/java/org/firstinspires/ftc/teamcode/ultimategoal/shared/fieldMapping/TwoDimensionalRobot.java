package org.firstinspires.ftc.teamcode.ultimategoal.shared.fieldMapping;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import org._11253.lib.utils.math.Math;
import org.firstinspires.ftc.teamcode.ultimategoal.shared.fieldMapping.Coordinate;

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
    // These two positions are pretty cool.
    // They don't actually represent the bounds of the robot - rather,
    // they represent the perceived position of the odometry system.
    // "Heading" just represents the heading of the robot.
    public double positionX = 0.0;
    public double positionY = 0.0;
    public double headingDegrees = 0.0;
    public double headingRadians = 0.0;

    public double boundPositiveX = 0.0;
    public double boundNegativeX = 0.0;
    public double boundPositiveY = 0.0;
    public double boundNegativeY = 0.0;

    private final Coordinate<Double> notOrientedFrontRight;
    private final Coordinate<Double> notOrientedFrontLeft;
    private final Coordinate<Double> notOrientedBackRight;
    private final Coordinate<Double> notOrientedBackLeft;

    private final double halfX;
    private final double halfY;

    public Coordinate<Double> position = new Coordinate<>(0.0, 0.0);
    public Coordinate<Double> frontRight = new Coordinate<>(0.0, 0.0);
    public Coordinate<Double> frontLeft = new Coordinate<>(0.0, 0.0);
    public Coordinate<Double> backRight = new Coordinate<>(0.0, 0.0);
    public Coordinate<Double> backLeft = new Coordinate<>(0.0, 0.0);

    public Line front;
    public Line right;
    public Line back;
    public Line left;

    private double robotSizeX;
    private double robotSizeY;

    public TwoDimensionalRobot(double size_x,
                               double size_y) {
        robotSizeX = size_x;
        robotSizeY = size_y;

        halfX = size_x / 2;
        halfY = size_y / 2;

        notOrientedFrontRight = new Coordinate<>(halfX, halfY); // Quadrant I: +, +
        notOrientedFrontLeft = new Coordinate<>(-halfX, halfY); // Quadrant II: -, +
        notOrientedBackLeft = new Coordinate<>(-halfX, -halfY); // Quadrant III: -, -
        notOrientedBackRight = new Coordinate<>(halfX, -halfY); // Quadrant IV: +, -
    }

    public Coordinate<Double> rotateCoordinate(Coordinate<Double> original, double angle) {
        double x = original.getX();
        double y = original.getY();
        double sine = Math.sin(angle);
        double cosine = Math.cos(angle);

        /* Apply the rotation matrix or whatever the FUCK it's called.
         * Rv = [ cos(theta), -sin(theta) ] * [ x ] = [ x cos(theta) - y sin(theta) ]
         *      [ sin(theta),  cos(theta) ]   [ y ]   [ x sin(theta) + y cos(theta) ]
         * x prime = x cos(theta) - y sin(theta)
         * y prime = x sin(theta) + y cos(theta)
         */
        double xPrime = (x * cosine) - (y * sine);
        double yPrime = (x * sine) + (y * cosine);

        return new Coordinate<>(xPrime, yPrime);
    }

    public void updatePosition() {
        // Update the position - the "origin," if you will, and nothing else.
        position = new Coordinate<>(positionX, positionY);
    }

    /**
     * Update the boundaries of a robot - in other words, update
     * the furthest extends which the robot can reach. This is used
     * in virtual collision checking later on and is (probably) at
     * least a little bit important and (definitely) at least a lot
     * a bit cool.
     *
     * <p>
     * This is dependent on rotational matrices, something which I
     * don't have a great foundational understanding of, largely because
     * I'm currently enrolled in a precalculus class. Anyway, using matrix
     * multiplication, this function (should) rotate points around a central
     * axis based on a given heading.
     * </p>
     */
    public void updateBounds() {
        // Here - look at this. I'm going to use my precalculus and geometry skills
        // to some very complex maths right here.
        // The position (x and y) of the robot would be the "origin" of the unit circle
        // or cartesian coordinate plane. Based on the heading, we should be able to
        // calculate how much space the robot is taking up, and in what directions.
        // I'm lazy, and don't feel like implementing a third dimension for height, so
        // any 2d-mapped object is entirely impermeable, no matter what.
        double x = positionX;
        double y = positionY;
        double angle = headingDegrees;

        // Quadrant I   - Positive X, positive Y
        // Quadrant II  - Negative X, positive Y
        // Quadrant III - Negative X, negative Y
        // Quadrant IV  - Positive X, negative Y
        frontRight = new Coordinate<>(x + halfX, y + halfY);
        frontLeft = new Coordinate<>(x - halfX, y - halfY);
        backLeft = new Coordinate<>(x - halfX, y - halfY);
        backRight = new Coordinate<>(x + halfX, y - halfY);

        // Now let's actually rotate all of the points, based on our knowledge of
        // the robot's current position.
        frontRight = rotateCoordinate(frontRight, angle);
        frontLeft = rotateCoordinate(frontLeft, angle);
        backLeft = rotateCoordinate(backLeft, angle);
        backRight = rotateCoordinate(backRight, angle);

        // And finally, update all of the "bounds" values.
        boundPositiveX = frontRight.getX();
        boundPositiveY = frontRight.getY();
        boundNegativeX = frontLeft.getX();
        boundNegativeY = backRight.getY();
    }

    public void updateLines() {
        front = new Line(frontLeft, frontRight);
        right = new Line(frontRight, backRight);
        back = new Line(backRight, backLeft);
        left = new Line(backLeft, frontLeft);
    }

    /**
     * Update both the position and bounds of the robot.
     */
    public void update() {
        updatePosition();
        updateBounds();
        updateLines();
    }

    /**
     * Set the position of the robot based on two cartesian
     * coordinates and a "heading" (or angle, or theta).
     *
     * @param x the x axis offset from the origin
     * @param y the y axis offset from the origin
     * @param heading the angle in which the robot is facing
     */
    public void setPosition(double x,
                            double y,
                            double heading) {
        positionX = x;
        positionY = y;
        headingDegrees = heading;
        headingRadians = Math.toRadians(heading);
        update();
    }

    /**
     * Set the position of a robot based on a pose.
     *
     * @param pose the pose to use
     */
    public void setPosition(Pose2d pose) {
        setPosition(
                pose.getX(),
                pose.getY(),
                pose.getHeading()
        );
        update();
    }
}
