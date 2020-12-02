package me.wobblyyyy.pathfinder_ftc.pathfinder;

import org._11253.lib.motors.MotorPower;
import org._11253.lib.robot.phys.assm.drivetrain.Drivetrain;

/**
 * Pathfinder-ftc-related drivetrain management system.
 *
 * @author Colin Robertson
 */
public class PfDrivetrainManager {
    public Drivetrain drivetrain;

    public PfDrivetrainManager(Drivetrain drivetrain) {
        this.drivetrain = drivetrain;
    }

    public void setPower(MotorPower motorPower) {
        drivetrain.setPower(motorPower);
    }

    public void brake() {
        drivetrain.setPower(
                new MotorPower(
                        0,
                        0,
                        0,
                        0
                )
        );
    }

    public void enableUserControl() {

    }

    public void disableUserControl() {

    }

    public boolean isUserControlled() {
        return true;
    }
}
