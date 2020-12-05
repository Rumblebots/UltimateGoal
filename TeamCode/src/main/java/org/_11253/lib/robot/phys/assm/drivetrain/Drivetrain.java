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
import org._11253.lib.utils.Timed;
import org._11253.lib.utils.async.event.StringEvents;

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
     * The string name for the async safety functionality, designed to
     * make sure nothing can go miserably wrong.
     */
    private static String safety = "_1125c drivetrain safety system";

    /**
     * The duration of the safety event.
     */
    private static long duration = 8000;

    /**
     * The delay before the safety event starts.
     */
    private static long delay = 0;

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
     * @param power the power to set to the robot.
     */
    public void setPower(MotorPower power) {
        setPower(new SourcedMotorPower(
                power.frontRightPower,
                power.frontLeftPower,
                power.backRightPower,
                power.backLeftPower,
                SourceType.USER
        ));
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
     * @param power the power to set to the robot.
     */
    public void setPower(SourcedMotorPower power) {
        // Determine the type of input we're dealing with.
        SourceType type =
                power.getType() == null ?
                        SourceType.USER :
                        power.getType();

        // Set motor power if the motor power can be set.
        // Who cares what that meant - if the code works, just let it be. Yay!
        switch (type) {
            case USER:
                if (isUserControlled) {
                    setPower(
                            power.frontRightPower,
                            power.frontLeftPower,
                            power.backRightPower,
                            power.backLeftPower
                    );
                }
                break;
            case NON_USER:
                if (isNonUserControlled) {
                    setPower(
                            power.frontRightPower,
                            power.frontLeftPower,
                            power.backRightPower,
                            power.backLeftPower
                    );
                }
                break;
            default:
                setPower(
                        0,
                        0,
                        0,
                        0
                );
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
     * Enable the safety system, designed to protect the robot from losing
     * control entirely.
     */
    private void enableSafety(final SourceType type) {
        // Clear the even first - "resetting the timer."
        StringEvents.clear(safety);

        // Schedule the safety event.
        StringEvents.schedule(
                safety,
                duration,
                delay,
                new Timed() {
                    @Override
                    public Runnable open() {
                        return new Runnable() {
                            @Override
                            public void run() {
                                switch (type) {
                                    case USER:
                                        enableUserControl();
                                        break;
                                    case NON_USER:
                                        enableNonUserControl();
                                        break;
                                }
                            }
                        };
                    }
                },
                false
        );
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
        enableSafety(SourceType.USER);
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
        enableSafety(SourceType.NON_USER);
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
