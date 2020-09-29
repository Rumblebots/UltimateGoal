///*
// * **
// *
// * Copyright (c) 2020
// * Copyright last updated on 6/4/20, 9:11 PM
// * Part of the _1125c library
// *
// * **
// *
// * Permission is granted, free of charge, to any person obtaining
// * a copy of this software and / or any of it's related source code or
// * documentation ("Software") to copy, merge, modify, publish,
// * distribute, sublicense, and / or sell copies of Software.
// *
// * All Software included is provided in an "as is" state, without any
// * type or form of warranty. The Authors and Copyright Holders of this
// * piece of software, documentation, or source code waive all
// * responsibility and shall not be liable for any claim, damages, or
// * other forms of liability, regardless of the form it may take.
// *
// * Any form of re-distribution of Software is required to have this same
// * copyright notice included in any source files or forms of documentation
// * which have stemmed or branched off of the original Software.
// *
// * **
// *
// */
//
//package org._11253.examples;
//
//import org._11253.lib.robot.phys.assm.Subsystem;
//import org._11253.lib.robot.phys.components.CRServo;
//import org._11253.lib.robot.phys.components.Motor;
//
///**
// * Here's an example subsystem. That's really all there is to it.
// * Hope you enjoy. Let's get it.
// */
//public class ExampleSubsystem extends Subsystem {
//    /*
//     * Here we declare a bunch of motors and servos.
//     * Note how the value of them is NOT set - these have to be
//     * set to new Components later on (during init or after)
//     */
//    public static Motor lifterMotor1;
//    public static Motor lifterMotor2;
//    public static CRServo lifterServo1;
//    public static CRServo lifterServo2;
//
//    /*
//     * Names of all of the items
//     * These are the names that appear on the configuration of the robot
//     * This is optional, but is useful because it makes it easier to change
//     * the name of the Components in the code.
//     */
//    public String lifterMotor1Name = "lifterMotor1";
//    public String lifterMotor2Name = "lifterMotor2";
//    public String lifterServo1Name = "lifterServo1";
//    public String lifterServo2Name = "lifterServo2";
//
//    /**
//     * Required init function
//     * <p>
//     * This function sets the value of the motors and servos
//     * to new components with the hardware names.
//     * </p>
//     */
//    public void init() {
//        /*
//         * Note how all of the Components are set to new Components of the correct type
//         * HERE instead of earlier. This is because the hardware map is not accessible before.
//         * Init must be called before anything else is.
//         */
//        lifterMotor1 = new Motor(lifterMotor1Name);
//        lifterMotor2 = new Motor(lifterMotor2Name);
//        lifterServo1 = new CRServo(lifterServo1Name);
//        lifterServo2 = new CRServo(lifterServo2Name);
//
//        /*
//         * This just sets the isRound property of each of the components to true.
//         * All this does is smooth the speeds to make it slowly approach zero the new power,
//         * rather than suddenly jumping to it.
//         * To be entirely honest, this isn't super useful, but it's here anyway.
//         */
//        lifterMotor1.isRound = true;
//        lifterMotor2.isRound = true;
//        lifterServo1.isRound = true;
//        lifterServo2.isRound = true;
//    }
//
//    /**
//     * Set the power to all of the lifter's Components
//     *
//     * @param power the new power to set to
//     */
//    public void setLifterPower(double power) {
//        lifterMotor1.setPower(power);
//        lifterMotor2.setPower(power);
//        lifterServo1.setPower(power);
//        lifterServo2.setPower(power);
//    }
//
//    /**
//     * Stop the lifter.
//     * This really just sets power to 0 for everything.
//     */
//    public void stopLifter() {
//        setLifterPower(0);
//    }
//}
