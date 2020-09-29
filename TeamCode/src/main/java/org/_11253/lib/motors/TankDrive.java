/*
 * **
 *
 * Copyright (c) 2020
 * Copyright last updated on 6/5/20, 5:43 PM
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

public class TankDrive {
    public double rightPower;
    public double leftPower;

    public TankDrive() {
        this(0, 0);
    }

    public TankDrive(double right,
                     double left) {
        rightPower = right;
        leftPower = left;
    }
}
