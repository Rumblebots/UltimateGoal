package org.firstinspires.ftc.teamcode.testCode.fmTesting;

import me.wobblyyyy.pathfinder.localizer.ThreeWheelCore;

public class OdometrySystem extends ThreeWheelCore {
    public OdometrySystem(double cpr,
                          double wheelDiameter,
                          double leftOffset,
                          double rightOffset,
                          double frontBackOffset) {
        super(cpr, wheelDiameter, leftOffset, rightOffset, frontBackOffset);
    }
}
