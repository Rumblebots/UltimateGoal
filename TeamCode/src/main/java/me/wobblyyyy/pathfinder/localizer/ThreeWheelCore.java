package me.wobblyyyy.pathfinder.localizer;

import com.tejasmehta.OdometryCore.OdometryCore;
import com.tejasmehta.OdometryCore.localization.EncoderPositions;
import com.tejasmehta.OdometryCore.localization.HeadingUnit;
import com.tejasmehta.OdometryCore.localization.OdometryPosition;
import me.wobblyyyy.pathfinder.fieldMapping.components.HeadingCoordinate;

/**
 * Core for three-wheel based positional tracking.
 *
 * <p>
 * This doesn't actually interact with the encoders or anything like that - all of the
 * work regarding the encoders still needs to be handled externally. All this does is
 * provide a simple interface for interacting with the odometry math code that Tejas
 * wrote. I don't really understand any of it, so I'm relying on him to get that done.
 * You can just use this beautiful code and enjoy. Have fun.
 * </p>
 */
public class ThreeWheelCore implements Odometry {
    /**
     * Left encoder position.
     *
     * <p>
     * As with all encoder positions, this has absolutely nothing to do with the actual
     * position of the robot - rather, this is the position of one of the encoders.
     * </p>
     */
    public int leftPos;

    /**
     * Left encoder position.
     *
     * <p>
     * As with all encoder positions, this has absolutely nothing to do with the actual
     * position of the robot - rather, this is the position of one of the encoders.
     * </p>
     */
    public int rightPos;

    /**
     * Left encoder position.
     *
     * <p>
     * As with all encoder positions, this has absolutely nothing to do with the actual
     * position of the robot - rather, this is the position of one of the encoders.
     * </p>
     */
    public int frontBackPos;

    /**
     * Currently stored {@link OdometryPosition} position of the robot.
     *
     * <p>
     * This is updated whenever the {@link #update(int, int, int)} method is invoked.
     * Obviously, you want to invoke that method whenever you can - that way, super
     * cool things end up happening, instead of not super cool things.
     * </p>
     */
    private OdometryPosition position;

    /**
     * Used near exclusively for odometry mathematics, and the handling thereof.
     *
     * <p>
     * I'll be the first to admit I understand just about none of what's going on when it
     * comes to the math behind somehow keeping track of the position of the robot. You should
     * check out a. the code, b. the JavaDocs for the odometry math if that's something that
     * you're really motivated to check out.
     * </p>
     */
    private OdometryCore core;

    /**
     * Counts per rotation.
     */
    private final double cpr;

    /**
     * The diameter of each of the odometry wheels.
     */
    private final double wheelDiameter;

    /**
     * The offset (inches) of the left odometry wheel.
     */
    private final double leftOffset;

    /**
     * The offset (inches) of the right odometry wheel.
     */
    private final double rightOffset;

    /**
     * The offset (inches) of the front/back odometry wheel.
     */
    private final double frontBackOffset;

    /**
     * A lovely constructor to get started with a three-wheel odometry system.
     *
     * @param cpr counts per rotation.
     * @param wheelDiameter wheel diameter (inches).
     * @param leftOffset offset of left wheel (inches).
     * @param rightOffset offset of right wheel (inches).
     * @param frontBackOffset offset of front/back wheel (inches).
     */
    public ThreeWheelCore(final double cpr,
                          final double wheelDiameter,
                          final double leftOffset,
                          final double rightOffset,
                          final double frontBackOffset) {
        this.cpr = cpr;
        this.wheelDiameter = wheelDiameter;
        this.leftOffset = leftOffset;
        this.rightOffset = rightOffset;
        this.frontBackOffset = frontBackOffset;
    }

    /**
     * Initialize the odometry system.
     *
     * <p>
     * This exists entirely independently of the hardware-based initialization for
     * the robot's physical components. This just initializes an instance of the
     * odometry math handling system.
     * </p>
     */
    public final void init() {
        OdometryCore.initialize(
                cpr,
                wheelDiameter,
                leftOffset,
                rightOffset,
                frontBackOffset
        );
        core = OdometryCore.getInstance();
        position = new OdometryPosition(0, 0, 0, HeadingUnit.RADIANS);
    }

    /**
     * Update the position of the robot based on encoder counts.
     *
     * @param leftPos the left encoder's count.
     * @param rightPos the right encoder's count.
     * @param frontBackPos the front/back encoder's count.
     */
    public void update(int leftPos,
                       int rightPos,
                       int frontBackPos) {
        this.leftPos = leftPos;
        this.rightPos = rightPos;
        this.frontBackPos = frontBackPos;

        position = core.getCurrentPosition(new EncoderPositions(
                leftPos,
                rightPos,
                frontBackPos
        ));
    }

    /**
     * Get the current position of the robot.
     *
     * @return a {@link HeadingCoordinate} (double, of course) representing the position of the robot.
     */
    @Override
    public HeadingCoordinate<Double> getPosition() {
        return Implementation.fromOdometryPosition(position);
    }
}
