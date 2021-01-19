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
     *
     * <p>
     * Rounded motors will average the last motor power with the next motor
     * power to come up with a current motor power. Rounding motors means that
     * they'll slowly decrement in speed or slowly increment in speed. Rounding
     * motors also means that they won't ever reach 100% of their target speed.
     * </p>
     *
     * <p>
     * Motor rounding was originally implemented in my 2018 season in order to
     * try to make our fairly unwieldy drivetrain a bit easier to control. It's
     * entirely a matter of personal preference. However, it's worth noting that
     * there are some potential issues with motor rounding, most notably issues
     * surrounding the fact that the motor won't ever reach it's target speed.
     * </p>
     */
    public boolean isRound;

    /**
     * A boolean which determines whether or not the
     * motor will run with encoders.
     *
     * <p>
     * Not really all that important, but it's here anyways! Yay!
     * </p>
     */
    public boolean isEncoded;

    /**
     * The actual motor component itself.
     *
     * <p>
     * There's some extensive documentation as to what a DC motor is available
     * under the DC motor file, but I'm not going to write out what it does.
     * I'm sure you know what a DC motor is, I'm sure you know how it works,
     * I'm sure you know all you need to know.
     * </p>
     */
    DcMotor dcMotorComponent;

    /**
     * Last checked encoder count.
     */
    private int count;

    /**
     * Multiplier at which speed the motor can spin at.
     * Up to 1.0, down to -1.0 speed, whatever.
     *
     * <p>
     * Having a multiplier of 1.0 means that the motor will go at exactly
     * the speed that's inputted.
     * </p>
     *
     * <p>
     * Having a multiplier of 0.5 means that the motor will go at exactly
     * half the speed that's inputted.
     * </p>
     *
     * <p>
     * Having a multiplier of 2.0 means that the motor will go at exactly
     * double the speed that's inputted.
     * </p>
     *
     * <p>
     * Please note! Although the multiplier can be whatever number you'd like
     * (yes, that does include 69 and 420) it's worth noting that the motors
     * have a limit to how much power they can actually spin with. This
     * limit is defined as (-1, 1), where 1 is the fastest the motor can spin
     * in the positive direction. Having a multiplier that leads to the motor's
     * input power being above 1 will have no or a negative effect on the motor.
     * I don't know if this is entirely true, but I have been told that sending
     * more than 1 as power to the motor can harm it in the long run.
     * </p>
     */
    public double multiplier = 1.0;

    /**
     * Not really useful. Shocking!
     */
    private int differential;

    /**
     * Create a new Motor.
     *
     * <p>
     * Search for the motor component and then create a new motor.
     * </p>
     *
     * @param name the name of the motor to look for.
     */
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
     *
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
     *
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

    /**
     * Get the DC motor component that's in use here.
     *
     * <p>
     * Unless you have a reason that requires you to directly access the DC
     * motor component, I'd strongly advise against it. The Motor class you're
     * looking at right now serves as a proxy between you and the lowest-level
     * hardware in the FTC. For the purpose of clarity and clean-ness, it's
     * generally advisable to stick to the same standard of coding. Yeah.
     * </p>
     *
     * @return the DC motor component.
     */
    public DcMotor getDcMotorComponent() {
        return dcMotorComponent;
    }
}
