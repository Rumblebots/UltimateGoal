/*
 * **
 *
 * Copyright (c) 2020
 * Copyright last updated on 6/10/20, 10:58 PM
 * Part of the _1125c library
 *
 * **
 *
 * Permission is granted, free of charge, to any person obtaining
 * a copy of this software and / or any of it's related source code or
 * documentation ("Software") to copy, merge, modify, publish,
 * distribute, sublicense, and / or sell copies of Software.
 *
 * All Software included is provided in an "as is" state, without any
 * type or form of warranty. The Authors and Copyright Holders of this
 * piece of software, documentation, or source code waive all
 * responsibility and shall not be liable for any claim, damages, or
 * other forms of liability, regardless of the form it may take.
 *
 * Any form of re-distribution of Software is required to have this same
 * copyright notice included in any source files or forms of documentation
 * which have stemmed or branched off of the original Software.
 *
 * **
 *
 */

package org._11253.lib.robot.phys.assm.drivetrain;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import org._11253.lib.Global;
import org._11253.lib.motors.MotorPower;
import org._11253.lib.motors.SourceType;
import org._11253.lib.motors.SourcedMotorPower;
import org._11253.lib.robot.phys.components.Motor;

/**
 * Template for a drive train.
 * <p>
 * init() must be called before the motors are used, and the
 * name of the motors can be changed via the strings
 *     <ul>
 *         <li>frontRightName</li>
 *         <li>frontLeftName</li>
 *         <li>backRightName</li>
 *         <li>backLeftName</li>
 *     </ul>
 * </p>
 *
 * @author Colin Robertson
 */
public class Drivetrain implements DrivetrainInterface {
    /**
     * Whether or not the drivetrain is currently capable of being
     * controlled via user input.
     */
    private boolean isUserControlled = true;

    /**
     * Whether or not the drivetrain is currently capable of being
     * controlled via non-user input.
     */
    private boolean isNonUserControlled = true;

    /**
     * The front-right motor.
     */
    public static Motor frontRight;

    /**
     * The front-left motor.
     */
    public static Motor frontLeft;

    /**
     * The back-right motor.
     */
    public static Motor backRight;

    /**
     * The back-left motor.
     */
    public static Motor backLeft;

    /**
     * The name of the front-right motor.
     */
    public String frontRightName = "frontRight";

    /**
     * The name of the front-left motor.
     */
    public String frontLeftName = "frontLeft";

    /**
     * The name of the back-right motor.
     */
    public String backRightName = "backRight";

    /**
     * The name of the back-left motor.
     */
    public String backLeftName = "backLeft";

    /**
     * Whether or not smoothing should be applied to inputs.
     *
     * <p>
     * Motor power rounding allows for smoother transitions in speeds - a round
     * motor would move from 1.0 speed to 0.33 speed to 0.11 speed and so on, until
     * it eventually gets close to 0.
     * </p>
     */
    public boolean isRound = false;

    /**
     * Get the current power of the drivetrain.
     *
     * @return the current power of the drivetrain.
     */
    public MotorPower getPower() {
        return new MotorPower(frontRight.getPower(), frontLeft.getPower(), backRight.getPower(), backLeft.getPower());
    }

    /**
     * Set power to the drivetrain based on a {@link MotorPower} object.
     *
     * <p>
     * {@link org._11253.lib.motors.SourcedMotorPower} is a useful and viable
     * option here - it allows the robot to differentiate between user input
     * and non-user input.
     * </p>
     *
     * @param motorPower the power to set to the robot.
     */
    public void setPower(MotorPower motorPower) {
        // Determine the type of input we're dealing with.
        SourceType type;

        // If we have a SourcedMotorPower, not just a regular MotorPower,
        // we can determine the type, and we'll do so.
        // Otherwise, we assume the type of input is user-controlled.
        if (motorPower instanceof SourcedMotorPower) {
            SourcedMotorPower sourced = (SourcedMotorPower) motorPower;
            type = sourced.getType();
        } else {
            type = SourceType.USER;
        }

        // Set motor power if the motor power can be set.
        // Who cares what that meant - if the code works, just let it be. Yay!
        switch (type) {
            case USER:
                if (isUserControlled) {
                    setPower(
                            motorPower.frontRightPower,
                            motorPower.frontLeftPower,
                            motorPower.backRightPower,
                            motorPower.backLeftPower
                    );
                }
                break;
            case NON_USER:
                if (isNonUserControlled) {
                    setPower(
                            motorPower.frontRightPower,
                            motorPower.frontLeftPower,
                            motorPower.backRightPower,
                            motorPower.backLeftPower
                    );
                }
                break;
        }
    }

    /**
     * Internally-used function to set power to the drivetrain.
     *
     * @param fr front-right power.
     * @param fl front-left power.
     * @param br back-right power.
     * @param bl back-left power.
     */
    private void setPower(double fr,
                          double fl,
                          double br,
                          double bl) {
        frontRight.setPower(fr);
        frontLeft.setPower(fl);
        backRight.setPower(br);
        backLeft.setPower(bl);
    }

    public void init() {
        if (Global.getHwMap() == null) {
            throw new NullPointerException("Global hardware map has to be initialized before initializing the drive train.");
        }

        frontRight = new Motor(frontRightName);
        frontLeft = new Motor(frontLeftName);
        backRight = new Motor(backRightName);
        backLeft = new Motor(backLeftName);

        frontRight.isRound = isRound;
        frontLeft.isRound = isRound;
        backRight.isRound = isRound;
        backLeft.isRound = isRound;

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void setMultiplier(double multiplier) {
        frontRight.multiplier = multiplier;
        frontLeft.multiplier = multiplier;
        backRight.multiplier = multiplier;
        backLeft.multiplier = multiplier;
    }

    public void meccanumDrive(MotionType type, double power) {
        switch (type) {
            case FORWARD:
                setPower(power, -power, power, -power);
                break;
            case BACKWARD:
                setPower(-power, power, -power, power);
            case LEFT:
                setPower(-power, power, power, -power);
            case RIGHT:
                setPower(power, -power, -power, power);
            case TURN_CW:
                setPower(power, power, power, power);
            case TURN_CCW:
                setPower(-power, -power, -power, -power);
        }
    }

    /**
     * Get the motor power multiplier.
     *
     * @return the motor power multiplier.
     */
    public double getMultiplier() {
        return frontRight.multiplier;
    }

    /**
     * Enable user control.
     */
    public void enableUserControl() {
        isUserControlled = true;
    }

    /**
     * Disable user control.
     */
    public void disableUserControl() {
        isUserControlled = false;
    }

    /**
     * Is the drivetrain user-controlled?
     *
     * @return if the drivetrain is user-controlled.
     */
    public boolean isUserControlled() {
        return isUserControlled;
    }

    /**
     * Enable non-user control.
     */
    public void enableNonUserControl() {
        isNonUserControlled = true;
    }

    /**
     * Disable non-user control.
     */
    public void disableNonUserControl() {
        isNonUserControlled = false;
    }

    /**
     * Is the drivetrain non-user-controlled?
     *
     * @return if the drivetrain is non-user-controlled.
     */
    public boolean isNonUserControlled() {
        return isNonUserControlled;
    }
}
