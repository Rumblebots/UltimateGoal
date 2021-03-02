package org.firstinspires.ftc.teamcode.testCode.pfTesting;

import me.wobblyyyy.pathfinder.robot.Encoder;
import me.wobblyyyy.pathfinder.tracking.threeWheel.ThreeWheelChassisTracker;

public class Odometry extends ThreeWheelChassisTracker {
    /**
     * Create a new ThreeWheelChassisTracker instance.
     *
     * <p>
     * As a note, this tracker relies on math from Tejas Mehta's OdometryCore
     * library. Links to important documentation and information about his
     * library are available in the main JavaDoc for this class file.
     * </p>
     *
     * @param left          the left encoder.
     * @param right         the right encoder.
     * @param middle        the front or back encoder. It's named "middle" as
     *                      it's technically in the middle of the robot. It's
     *                      important to mount this wheel as accurately as
     *                      possible, otherwise some positional tracking math
     *                      will/might be wrong.
     * @param wheelDiameter the diameter of each of the encoder's wheels,
     *                      preferably (and hopefully) measured in inches.
     * @param leftOffset    the left wheel's offset from the center of the
     *                      robot. This is ONLY the horizontal offset.
     * @param rightOffset   the right wheel's offset from the center of the
     *                      robot. This is ONLY the horizontal offset.
     * @param middleOffset  the center wheel's offset from the center of the
     */
    public Odometry(Encoder left,
                    Encoder right,
                    Encoder middle,
                    double wheelDiameter,
                    double leftOffset,
                    double rightOffset,
                    double middleOffset) {
        super(
                left,
                right,
                middle,
                wheelDiameter,
                leftOffset,
                rightOffset,
                middleOffset
        );
    }
}
