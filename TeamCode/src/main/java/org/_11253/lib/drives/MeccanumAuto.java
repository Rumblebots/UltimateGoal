/****
 * Made by Tejas Mehta
 * Made on Tuesday, September 29, 2020
 * File Name: MeccanumAuto
 * Package: org._11253.lib.drives*/
package org._11253.lib.drives;

import org._11253.lib.motors.MotorPower;
import org._11253.lib.op.Auton;
import org._11253.lib.robot.phys.assm.drivetrain.Drivetrain;
import org._11253.lib.utils.telem.Telemetry;

enum DriveDirection {
    FORWARD,
    BACKWARD,
    LEFT,
    RIGHT,
}

public class MeccanumAuto extends Auton {
    public Drivetrain drivetrain = new Drivetrain();

    public void meccanumDrive(DriveDirection direction, double power) {
        switch (direction) {
            case FORWARD:
                drivetrain.setPower(new MotorPower(power));
                break;
            case BACKWARD:
                drivetrain.setPower(new MotorPower(-power));
                break;
            case LEFT:
                drivetrain.setPower(new MotorPower(power, -power, -power, power));
                break;
            case RIGHT:
                drivetrain.setPower(new MotorPower(-power, power, power, -power));
                break;
        }
    }

    public MeccanumAuto() {
        beforeStart.add(new Runnable() {
            @Override
            public void run() {
                drivetrain.init();
            }
        });
        onStart.add(new Runnable() {
            @Override
            public void run() {
                Telemetry.addData("Autonomous", "Running Mecannum Autonomous...", "");
            }
        });
    }
}
