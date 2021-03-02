package org.firstinspires.ftc.teamcode.testCode.pfTesting;

import me.wobblyyyy.pathfinder.drive.meccanum.Meccanum;
import me.wobblyyyy.pathfinder.robot.Motor;

public class Drive extends Meccanum {
    /**
     * Create a new instance of the meccanum drivetrain.
     *
     * @param fr front-right motor.
     * @param fl front-left motor.
     * @param br back-right motor.
     * @param bl back-left motor.
     */
    public Drive(Motor fr,
                 Motor fl,
                 Motor br,
                 Motor bl) {
        super(fr, fl, br, bl);
    }
}
