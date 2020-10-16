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

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import org._11253.lib.utils.math.Math;

/**
 * An implementation of the default CRServo.
 * Adds some additional functionality over it, including
 * CRServo smoothing / rounding (curving the next CRServo power based on what it is currently
 * and what the next target value is).
 *
 * @author Colin Robertson
 */
public class CRServo extends Component {
    /**
     * A boolean which determines whether or not the
     * values of the component should be adjusted very
     * suddenly or more slowly, based on averaging.
     */
    public boolean isRound;
    com.qualcomm.robotcore.hardware.CRServo crServoComponent;

    public CRServo(String name) {
        super(com.qualcomm.robotcore.hardware.CRServo.class, name);
        crServoComponent = (com.qualcomm.robotcore.hardware.CRServo) component;
    }

    /**
     * Gets the power from the servo component.
     *
     * @return the servo component's value.
     */
    public double getPower() {
        return crServoComponent.getPower();
    }

    /**
     * Sets the servo component's power based on input.
     * <p>
     * isRound can toggle whether or not it should be rounded.
     * </p>
     *
     * @param power the new power level
     */
    public void setPower(double power) {
        if (!isRound) {
            crServoComponent.setPower(power);
        } else {
            crServoComponent.setPower(Math.average(power, getPower()));
        }
    }

    /**
     * Gets the servo's current direction.
     *
     * @return the servo's current direction
     */
    public DcMotorSimple.Direction getDirection() {
        return crServoComponent.getDirection();
    }

    /**
     * Sets the servo's current direction
     *
     * @param direction the next direction for the servo.
     */
    public void setDirection(DcMotorSimple.Direction direction) {
        crServoComponent.setDirection(direction);
    }

    public com.qualcomm.robotcore.hardware.CRServo getCrServoComponent() {
        return crServoComponent;
    }
}
