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

import com.qualcomm.robotcore.hardware.ColorSensor;
import org._11253.lib.robot.phys.components.Component;
import org._11253.lib.utils.async.tasks.RepeatingTask;

/**
 * Component which stores a color sensor.
 * <p>
 * The color sensor's values are updated periodically
 * through an asynchronous task to make sure you're not
 * polling them too much.
 * </p>
 *
 * @author Colin Robertson
 */
public class Color extends Component {
    /**
     * The map which we're actually using to store all the values.
     */
    public ColorMap map;
    /**
     * The actual sensor component which we're going to be using.
     */
    ColorSensor sensor;
    /**
     * An asynchronous task, complete with a runnable.
     * <p>
     * This runnable is executed once every some number
     * of milliseconds. You can always get 'map'
     * to get the most recently updated values.
     * </p>
     */
    RepeatingTask task = new RepeatingTask(new Runnable() {
        @Override
        public void run() {
            update();
        }
    });

    /**
     * Creates a new component, takes a class (component type) as well as
     * a name (hardware name) for the component.
     *
     * @param name the name of the device, as it appears on the HardwareMap
     */
    public Color(String name) {
        super(ColorSensor.class, name);
        sensor = (ColorSensor) component;
        task.scheduleRepeatingTask(100); // TODO: make sure this value is good enough
    }

    /**
     * Get a new ColorMap with the current values.
     *
     * @return a new ColorMap with current sensor values
     */
    private ColorMap read() {
        return new ColorMap(
                sensor.red(),
                sensor.green(),
                sensor.blue(),
                sensor.alpha(),
                sensor.argb()
        );
    }

    /**
     * Update map to be the latest values.
     */
    private void update() {
        map = read();
    }

    /**
     * All the different types of colors we have.
     * <p>
     * To be entirely honest, I'm not exactly sure why I have
     * this here, but out of fear of breaking something, I'll
     * just keep it here.
     * TODO: see if this enum is actually used anywhere
     * </p>
     */
    enum Colors {
        RED,
        GREEN,
        BLUE,
        ALPHA,
        ARGB
    }

    /**
     * Template-ish class which contains colors.
     * <p>
     * This is used to replace a HashMap and hopefully
     * makes things at least a little bit easier to work with.
     * </p>
     */
    public class ColorMap {
        /**
         * All of the variables we need.
         */
        private int Red, Green, Blue, Alpha, Argb;

        /**
         * If no values are supplied, just create all zeros
         */
        public ColorMap() {
            this(0, 0, 0, 0, 0);
        }

        /**
         * Take a bunch of values in and set the color map to have all of those values.
         *
         * @param red   red int
         * @param green green int
         * @param blue  blue int
         * @param alpha alpha int
         * @param argb  argb int
         */
        public ColorMap(int red, int green, int blue, int alpha, int argb) {
            this.Red = red;
            this.Green = green;
            this.Blue = blue;
            this.Alpha = alpha;
            this.Argb = argb;
        }

        /**
         * Return the color map itself. Cool, I know.
         *
         * @return the color map
         */
        public ColorMap getColorMap() {
            return this;
        }

        /**
         * Get red
         *
         * @return red
         */
        public int red() {
            return Red;
        }

        /**
         * Get green
         *
         * @return green
         */
        public int green() {
            return Green;
        }

        /**
         * Get blue
         *
         * @return blue
         */
        public int blue() {
            return Blue;
        }

        /**
         * Get alpha
         *
         * @return alpha
         */
        public int alpha() {
            return Alpha;
        }

        /**
         * Get argb
         *
         * @return argb
         */
        public int argb() {
            return Argb;
        }
    }
}
