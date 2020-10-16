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

/**
 * Provides an implementation of FTC's default Servo class.
 * This one is obviously better, mostly because it's way cooler.
 *
 * @author Colin Robertson
 */
public class Servo extends Component {
    com.qualcomm.robotcore.hardware.Servo servoComponent;

    public Servo(String name) {
        super(com.qualcomm.robotcore.hardware.Servo.class, name);
        servoComponent = (com.qualcomm.robotcore.hardware.Servo) component;
    }

    /**
     * Gets the servo's current position.
     *
     * @return the servo's position.
     */
    public double getPosition() {
        return servoComponent.getPosition();
    }

    /**
     * Set the servo's target position
     *
     * @param position the next position to aim for
     */
    public void setPosition(double position) {
        servoComponent.setPosition(position);
    }

    public com.qualcomm.robotcore.hardware.Servo getServoComponent() {
        return servoComponent;
    }
}
