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

import org._11253.lib.Global;

/**
 * Template class for components, used internally in org._11253.lib.robot.phys.components package
 *
 * @author Colin Robertson
 */
public class Component {
    /**
     * The component itself.
     *
     * <p>
     * This is, in case you couldn't tell, an object - meaning it can
     * shapeshift, evolve, level up, shift into... whatever you want to
     * say it does... a FTC compatible component, such as our lovely
     * DcMotor or even Servo. Yay!
     * </p>
     */
    public Object component;

    /**
     * Creates a new component, takes a class (component type) as well as
     * a name (hardware name) for the component.
     * 
     * <p>
     * It's probably worth noting that any component which is initalized
     * (or constructed, or whatever term you prefer to use) is
     * initalized immediately - as soon as this function is called,
     * actually. This means that, in order to initalize a component,
     * the hardware map MUST be initalized, and, if it's not, everything
     * will... well, fail.
     * </p>
     * <p>
     * It's unlikely this will ever fail if the user is using a template
     * op mode - if it somehow does fail, the user probably has fewer
     * brain cells than I do testicles (which is two, by the way) and
     * shouldn't be writing code in the first place. However, if the user
     * is, for a reason I could not possibly imagine, using this class and 
     * NOT using any other portions of the _1125c library, we give them 
     * a friendly little reminder that a. they're dumb as fuck, and b.
     * you have to make sure to manually set the global hardware map.
     * </p>
     *
     * @param c    the class name (ie. DcMotor.class)
     * @param name the name of the device, as it appears on the HardwareMap
     */
    public Component(Class<?> c, String name) {
        if (Global.getHwMap() == null) {
            throw new NullPointerException(
              "You encountered an error while attempting to initalize " +
              "the component \"" + name + "\" of the class " +
              c.getSimpleName() + ". " +
              "You want to be sure that you're initalizing components " +
              "(writing, ie. frontRight = new Motor(fr_name)) AFTER " +
              "the global hardware map has been initalized. If you're " +
              "using one of _1125c's included OpMode templates, this " +
              "should happen before start, so you likely won't have to " +
              "ever worry about it. However, if you're using this class " +
              "as a base, without using one of the template OpModes, " +
              "you may have forgotten to set the Global hardware map. " +
              "Good luck, brave adventurer! :D"
            );
        }

        component = Global.getHwMap().get(c, name);
    }
}
