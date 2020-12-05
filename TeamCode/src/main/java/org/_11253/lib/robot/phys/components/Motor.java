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

package org._11253.lib.robot.phys.components;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import org._11253.lib.utils.math.Math;

/**
 * An implementation of the default DcMotor.
 * Adds some additional functionality over it, including
 * motor smoothing / rounding (curving the next motor
 * power based on what it is currently
 * and what the next target value is).
 *
 * @author Colin Robertson
 */
public class Motor extends Component {
    /**
     * A boolean which determines whether or not the
     * values of the component should be adjusted very
     * suddenly or more slowly, based on averaging.
     */
    public boolean isRound;

    /**
     * A boolean which determines whether or not the
     * motor will run with encoders.
     */
    public boolean isEncoded;

    /**
     * The actual motor component itself.
     */
    DcMotor dcMotorComponent;

    /**
     * Last checked encoder count.
     */
    private int count;

    /**
     * Multiplier at which speed the motor can spin at.
     * Up to 1.0, down to -1.0 speed, whatever.
     */
    public double multiplier = 1.0;

    private int differential;

    public Motor(String name) {
        super(DcMotor.class, name);
        dcMotorComponent = (DcMotor) component;
        dcMotorComponent.setMode(DcMotor.RunMode.RUN_USING_ENCODER); // is this required?
    }

    /**
     * Gets the power from the motor component.
     *
     * @return the motor component's value.
     */
    public double getPower() {
        return dcMotorComponent.getPower();
    }

    /**
     * Sets the motor component's power based on input.
     * <p>
     * isRound can toggle whether or not it should be rounded.
     * </p>
     *
     * @param power the new power level
     */
    public void setPower(double power) {
        if (!isRound) {
            dcMotorComponent.setPower(power * multiplier);
        } else {
            dcMotorComponent.setPower(Math.average(power * multiplier, getPower()));
        }
    }

    /**
     * Sets the motor component's direction based on input.
     * <p>
     * Usually, left side motors will be inverted.
     * At least that's what I do.
     * </p>
     *
     * @param direction the new direction
     */
    public void setDirection(DcMotorSimple.Direction direction) {
        dcMotorComponent.setDirection(direction);
    }

    /**
     * Update the encoder count & return it
     *
     * @return the current encoder count
     */
    public int getCount() {
        count = dcMotorComponent.getCurrentPosition() - differential;
        return count;
    }

    /**
     * Set the current encoder count.
     *
     * @param count the count to set to
     */
    public void setCount(int count) {
        this.count = count;
        differential = count - dcMotorComponent.getCurrentPosition();
    }

    /**
     * Reset the motor's encoders.
     */
    public void resetEncoders() {
        dcMotorComponent.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dcMotorComponent.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public DcMotor getDcMotorComponent() {
        return dcMotorComponent;
    }
}
