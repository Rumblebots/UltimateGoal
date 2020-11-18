package org._11253.lib.odometry.fieldMapping.pathfinding.robotRegulation;

import org._11253.lib.motors.MotorPower;
import org._11253.lib.robot.phys.assm.drivetrain.Drivetrain;

public class DrivetrainRegulationSystem {
    public Drivetrain drivetrain;

    public void setPower(MotorPower motorPower) {
        drivetrain.setPower(motorPower);
    }
}
