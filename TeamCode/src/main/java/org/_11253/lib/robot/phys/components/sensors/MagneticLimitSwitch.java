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

package org._11253.lib.robot.phys.components.sensors;

/**
 * Extends touch sensor functionality and renames it.
 * <p>
 * This doesn't really do much, or, for that matter,
 * anything at all, but it renames touch sensor
 * so your code can be better organized and more clean,
 * or something. I don't know man. If you don't like it,
 * don't use it, it's really that simple.
 * </p>
 *
 * @author Colin Robertson
 */
public class MagneticLimitSwitch extends Touch {
    public MagneticLimitSwitch(String name) {
        super(name);
    }
}
