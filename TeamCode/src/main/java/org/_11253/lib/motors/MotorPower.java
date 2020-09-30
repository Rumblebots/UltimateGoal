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

package org._11253.lib.motors;

/**
 * Basic class / object like thing that contains data on the power for
 * all four drive motors on a typical drive system.
 *
 * @author Colin Robertson
 */
public class MotorPower {
    public double frontRightPower;
    public double frontLeftPower;
    public double backRightPower;
    public double backLeftPower;

    public MotorPower() {
        this(0, 0, 0, 0);
    }

    public MotorPower(double power) {
        frontRightPower = power;
        frontLeftPower = power;
        backRightPower = power;
        backLeftPower = power;
    }

    public MotorPower(double fr, double fl, double br, double bl) {
        frontRightPower = fr;
        frontLeftPower = fl;
        backRightPower = br;
        backLeftPower = bl;
    }
}
