///*
// * **
// *
// * Copyright (c) 2020
// * Copyright last updated on 6/10/20, 10:58 PM
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
//package org._11253.lib.robot.phys.assm;
//
//import org._11253.lib.utils.gen.id.Id;
//import org._11253.lib.utils.gen.id.IdRegister;
//
///**
// * Abstract class with the (very small) amount of things required for a subsystem.
// * That's really all there is to it.
// * <p>
// * While creating a sub-system, making sure that components do not have
// * a value set until the init function runs.
// * </p>
// *
// * @author Colin Robertson
// */
//public abstract class Subsystem {
//    /*
//     * The Id of the subsystem, used for doing super cool stuff, of course.
//     */
//    public Id subsystemId;
//
//    /*
//     * Whenever a new subsystem is created, get a new ID.
//     */
//    public Subsystem() {
//        subsystemId = IdRegister.getNextId();
//    }
//
//    /**
//     * Required function for all sub-systems.
//     * This is where any declared components should have a value assigned to them.
//     */
//    public abstract void init();
//}
