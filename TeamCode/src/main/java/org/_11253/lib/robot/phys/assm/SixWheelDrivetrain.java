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

package org._11253.lib.robot.phys.assm;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import org._11253.lib.Global;
import org._11253.lib.motors.SixWheelDrive;
import org._11253.lib.robot.phys.components.Motor;

/**
 * Template drivetrian in case your team
 * would rather use a six wheel drive.
 * <p>
 * Look, I don't judge. I'm just here to write code.
 * You do you, man. I'm just saying, four-wheel meccanum
 * is obviously superior, but you're free to make whatever
 * wrong decisions you'd like to.
 * </p>
 * <p>
 * This is really similar to the default drivetrain.
 * Go read the documentation there if you'd like to learn
 * more about how this all works. Note that you can
 * change the name of the motors in the configuration or
 * here by just modifying these strings.
 * </p>
 *
 * @author Colin Robertson
 * @see Drivetrain
 */
public class SixWheelDrivetrain {
    public static Motor frontRight;
    public static Motor midRight;
    public static Motor backRight;
    public static Motor frontLeft;
    public static Motor midLeft;
    public static Motor backLeft;

    public String frontRightName = "frontRight";
    public String midRightName = "midRight";
    public String backRightName = "backRight";
    public String frontLeftName = "frontLeft";
    public String midLeftName = "midLeft";
    public String backLeftName = "backLeft";

    public boolean isRound = false;

    /**
     * Get the power of all of the motors.
     * <p>
     * If for some reason you'd like to get the power of
     * each and every single one of the motors, this returns
     * a new SixWheelDrive object, containing the power
     * of each and every motor included here.
     * </p>
     *
     * @return a new SixWheelDrive object with motor powers
     */
    public SixWheelDrive getPower() {
        return new SixWheelDrive() {
            double frontRightPower = frontRight.getPower();
            double midRightPower = midRight.getPower();
            double backRightPower = backRight.getPower();
            double frontLeftPower = frontLeft.getPower();
            double midLeftPower = midLeft.getPower();
            double backLeftPower = backLeft.getPower();
        };
    }

    /**
     * Set power using a SixWheelDrive object.
     *
     * @param motorPower updated power to use
     */
    public void setPower(SixWheelDrive motorPower) {
        frontRight.setPower(motorPower.frontRightPower);
        midRight.setPower(motorPower.midRightPower);
        frontLeft.setPower(motorPower.frontLeftPower);
        backRight.setPower(motorPower.backRightPower);
        midLeft.setPower(motorPower.midLeftPower);
        backLeft.setPower(motorPower.backLeftPower);
    }

    /**
     * This is run to init the drivetrain. That's all.
     * <p>
     * Good talk, I know.
     * </p>
     */
    public void init() {
        if (Global.getHwMap() == null) {
            throw new NullPointerException(
                    "Global hardware map has to be initialized " +
                            "before attempting to use it. " +
                            "Did you forget to call init?"
            );
        }

        frontRight = new Motor(frontRightName);
        midRight = new Motor(midRightName);
        backRight = new Motor(backRightName);
        frontLeft = new Motor(frontLeftName);
        midLeft = new Motor(midLeftName);
        backLeft = new Motor(backLeftName);

        frontRight.isRound = isRound;
        midRight.isRound = isRound;
        backRight.isRound = isRound;
        frontLeft.isRound = isRound;
        backLeft.isRound = isRound;

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        midLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
    }
}
