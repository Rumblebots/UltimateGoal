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

package org._11253.lib;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Contains a couple things (hardware map,telem, and gamepads) to be used anywhere.
 *
 * @author Colin Robertson
 */
public class Global {
    /**
     * Hardware map which should be used globally.
     * This has a getter and setter method so you shouldn't actually be touching this at any point.
     */
    private static HardwareMap hwMap;

    /**
     * Telem to be used globally. Not really needed, but it's
     * cool or something, and makes a lot of things quite a bit easier.
     */
    private static Telemetry telem;

    /**
     * Global instance of gamepad1
     */
    private static Gamepad gamepad1;

    /**
     * Global instance of gamepad2
     */
    private static Gamepad gamepad2;

    /**
     * Getter method for gamepad1
     *
     * @return gamepad1
     */
    public static Gamepad getGamepad1() {
        return gamepad1;
    }

    /**
     * Setter method for gamepad1
     *
     * @param gamepad1 new gamepad
     */
    public static void setGamepad1(Gamepad gamepad1) {
        Global.gamepad1 = gamepad1;
    }

    /**
     * Getter method for gamepad2
     *
     * @return gamepad2
     */
    public static Gamepad getGamepad2() {
        return gamepad2;
    }

    /**
     * Setter method for gamepad2
     *
     * @param gamepad2 new gamepad
     */
    public static void setGamepad2(Gamepad gamepad2) {
        Global.gamepad2 = gamepad2;
    }

    /**
     * Getter method for telem
     *
     * @return global instance of telem
     */
    public static Telemetry getTelem() {
        return telem;
    }

    /**
     * Setter method for telem
     *
     * @param telem new telem
     */
    public static void setTelem(Telemetry telem) {
        Global.telem = telem;
    }

    /**
     * Getter method for hardware map
     *
     * @return instance of hardware map
     */
    public static HardwareMap getHwMap() {
        return hwMap;
    }

    /**
     * Setter method for hardware map
     *
     * @param hwMap new hardware map
     */
    public static void setHwMap(HardwareMap hwMap) {
        Global.hwMap = hwMap;
    }
}
