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

import com.qualcomm.robotcore.hardware.TouchSensor;
import org._11253.lib.robot.phys.components.Component;

/**
 * Who knew touch sensors were this incredibly simple?
 * <p>
 * Basic implementation of a touch sensor.
 * </p>
 *
 * @author Colin Robertson
 */
public class Touch extends Component {
    /**
     * The sensor itself.
     */
    TouchSensor sensor;

    /**
     * Constructor, with a name, to create
     * all of the stuff we need.
     *
     * @param name the name of the sensor in the configuration file.
     */
    public Touch(String name) {
        super(TouchSensor.class, name);
        sensor = (TouchSensor) component;
    }

    /**
     * See whether or not the sensor is ACTIVE
     *
     * @return if the sensor is active
     */
    public boolean isActive() {
        return sensor.isPressed();
    }

    /**
     * See whether or not the sensor is INACTIVE
     *
     * @return if the sensor is inactive
     */
    public boolean isInactive() {
        return !sensor.isPressed();
    }
}
