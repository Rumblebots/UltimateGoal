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

package org._11253.lib.utils.telem;

import org._11253.lib.Global;
import org._11253.lib.utils.Timed;
import org._11253.lib.utils.async.event.StringEvents;

import java.util.HashMap;

/**
 * A high-level abstraction of FTC's telemetry.
 * <p>
 * This utility class contains two different types
 * of telemetry.
 *     <ul>
 *         <li>
 *             <b>Temporary</b>
 *             Temporary telemetry is wiped away every single time
 *             the telemetry data currently stored is printed. This functions
 *             exactly like FTC's default telemetry system - in order for this
 *             to be displayed, it must be assigned a value every single time
 *             telemetry is printed. Temporary telemetry IS accessible. addLine
 *             and addData still return with String keys, which are in the format
 *             of "temp_#." Although I'm not entirely sure for what reason you'd want
 *             to have access to temporary telemetry, it's there anyway.
 *         </li>
 *         <li>
 *             <b>Permanent</b>
 *             Permanent telemetry is NOT wiped away whenever all of the
 *             telemetry data is printed. This is useful for storing values
 *             which you don't want to have to manually update the value of
 *             every single time you want telemetry to be printed. Permanent data
 *             works with a key-based system, meaning you have to use String keys
 *             to refer to or access Permanent telemetry. All of the permanent telemetry
 *             related functions return Strings, which are the keys that you're using.
 *         </li>
 *     </ul>
 *     I'm sure as you can tell, based on how incredibly
 *     shitty & poorly written this code is, I'm not exactly an
 *     expert with HashMaps just yet, but I hope this will suffice.
 *     I mean, it makes sense to me, so it's okay, right?
 *     I'll probably be the only person who's going to be using
 *     this anyway, so....
 * </p>
 * <p>
 *     As a suggestion for a potential use case, this could be used
 *     for debugging purposes. Hypothetically, let's say you need
 *     to determine the last value of EVERY single thing on the entire
 *     controller or something absolutely absurd. Look, I really don't know.
 *     But anyway, you could use the permanent telemetry to display every
 *     value at all times, regardless of what it's current value is.
 *     Which could be nice, or something, honestly, I'm not too sure.
 *     Look, if you're seriously planning on using this library, you're
 *     probably clinically insane enough to be reading this right now
 *     and be understanding every single word of it anyway.
 * </p>
 * <p>
 *     Here's a pretty (not really) cool fact - telemetry only updates every 250ms! FTC, by default, includes a
 *     throttling system to ensure there's not too much traffic between the robot phone and driver phone.
 *     According to the JavaDocs in the RobotController, there's a variable named "msTransmissionInterval." As you could
 *     probably guess, this is the interval (in ms) between telemetry transmissions. Look here!
 *     <code>
 *     "Actual transmission to the driver station is throttled to avoid use of excessive bandwidth. By default,
 *     transmission will occur at most every 250ms. This interval can be controlled with setMsTransmissionInterval().
 *     Any update()s which occur more frequently will not be transmitted if superseded by a subsequent update() before
 *     the transmission interval expires."
 *     </code>
 *     In order to not use up too much processing power, telemetry will (by default) (probably) be limited to only
 *     updating every 125ms (half of the 250ms FTC uses by default, just to ensure we're as updated as we can be
 *     without overdoing it). Of course, this can be changed. I'm not entirely sre why you'd want to change it, but...
 *     oh well.
 * </p>
 *
 * @author Colin Robertson
 */
public class Telemetry {
    /**
     * An internally-used HashMap containing Telem objects.
     * <p>
     * This should only be used internally.
     * </p>
     * <p>
     * Each Telem object is iterated over while printing
     * telemetry data. Note that each contained Telem object
     * can be either DATA or LINE, which both extend the default
     * Telem, meaning there will always be a getMessage method.
     * </p>
     */
    public static HashMap<String, Telem> telemetry = new HashMap<String, Telem>();

    /**
     * The highest number temp there is / can be
     * or whatever the hell it's supposed to be.
     */
    private static int currentTemporaryCap = 0;

    /**
     * A boolean, indicating whether or not the automatic
     * updating system hsa been scheduled in the StringEvents
     * system.
     */
    private static boolean hasUpdaterBeenScheduled = false;

    /**
     * How often, in ms, should telemetry be updated? I set
     * this to 125 by default - half of the native telemetry
     * transmission interval, just to ensure data is always as
     * updated as possible without using too much processing
     * power on our robot.
     */
    private static int updateInterval = 125;

    /**
     * Temporary access for telemetry, specifically the
     * data portion. Use this when you just want to throw in
     * a disposable telemetry data message thingy.
     *
     * @param caption the caption you want
     * @param data    the data you want
     * @return the key of the newly created thingy
     */
    public static String addData(String caption, String data) {
        int number = currentTemporaryCap;
        String name = "temp_" + number;
        addData(name, caption, data);
        currentTemporaryCap++;
        return name;
    }

    /**
     * Permanent access with a key.
     * <p>
     * You'll probably end up using this version the most - it doesn't
     * have a field for SEPARATOR, which, quite likely, you're not
     * going to be editing anyway.
     * </p>
     *
     * @param key     the key you'd like to use
     * @param caption the caption of the data
     * @param data    the data portion of the data
     * @return the key you're using
     */
    public static String addData(String key, String caption, String data) {
        return addData(key, caption, ": ", data);
    }

    /**
     * Big boy function which actually creates a new Data TELEM object
     * and does all the cool stuff with it.
     *
     * @param key       the key you'd like to use
     * @param caption   the caption of the data
     * @param separator the separator used in the message
     * @param data      the actual data portion of the data
     * @return the key you're using
     */
    public static String addData(String key, String caption, String separator, String data) {
        Data dataTelem;
        if (telemetry.containsKey(key)) {
            dataTelem = (Data) telemetry.get(key);
        } else {
            dataTelem = new Data();
        }
        dataTelem.setCaption(caption, false);
        dataTelem.setSeparator(separator, false);
        dataTelem.setData(data);
        telemetry.put(key, dataTelem);
        return key;
    }

    /**
     * Temporary access to adding a line.
     *
     * @param line the text you're using.
     * @return the String key.
     */
    public static String addLine(Object line) {
        int number = currentTemporaryCap;
        String name = "temp_" + number;
        currentTemporaryCap++;
        return addLine(name, line);
    }

    /**
     * Permanent access to use when adding a line.
     * <p>
     * This one contains a key as well as text,
     * meaning you'll get more permanent access to it.
     * Once again, very cool, I know.
     * </p>
     *
     * @param key  the key you're using
     * @param line the text of the line
     * @return the key which you're using
     */
    public static String addLine(String key, Object line) {
        Line lineTelem;
        if (telemetry.containsKey(key)) {
            lineTelem = (Line) telemetry.get(key);
        } else {
            lineTelem = new Line();
        }
        lineTelem.setMessage(line.toString());
        return key;
    }

    /**
     * Removes a bit of data.
     *
     * @param key the key of the data you'd like to remove.
     * @return the key you're using
     */
    public static String removeData(String... key) {
        return remove(key);
    }

    /**
     * Removes a line.
     *
     * @param key the key of the line you'd like to remove.
     * @return the key you're using
     */
    public static String removeLine(String... key) {
        return remove(key);
    }

    /**
     * Overload for removing something so no errors are thrown.
     *
     * @return string
     */
    public static String remove() {
        return remove("");
    }

    /**
     * Actual remove function
     *
     * @param key varargs argument so you can remove a lot of things.
     * @return the very first key you're removing
     */
    public static String remove(String... key) {
        for (String k : key) {
            telemetry.remove(k);
        }
        return key[0];
    }

    /**
     * Clear all of the telemetry.
     */
    public static void clear() {
        telemetry = new HashMap<String, Telem>();
        currentTemporaryCap = 0;
    }

    /**
     * Prints the telemetry.
     * <p>
     * This is analogous to the default update method included
     * in FTC's implementation of telemetry, except this does it
     * 69% better. And trust me, I know, that's a real mature number
     * to use.
     * </p>
     */
    public static void printTelemetry() {
        org.firstinspires.ftc.robotcore.external.Telemetry t = Global.getTelem();
        for (HashMap.Entry<String, Telem> entry : telemetry.entrySet()) {
            t.addLine(entry.getValue().getMessage());
            if (entry.getKey().contains("temp_")) {
                telemetry.remove(entry.getKey());
                currentTemporaryCap--;
            }
        }
        t.update();
    }

    /**
     * Modify how long in between updates our telemetry
     * software should go.
     * @param ms a delay, in ms
     */
    public static void setUpdateInterval(int ms) {
        updateInterval = ms;
    }

    /**
     * Schedule automated telemetry printing to optimize CPU
     * usage in a <b>WONDERFUL</b> balance with keeping all of
     * the data that could possibly be used updated.
     */
    public static void scheduleTelemetryUpdater() {
        StringEvents.schedule(
                "_1125c_AUTOTELEMETRY",
                updateInterval,
                0,
                new Timed() {
                    @Override
                    public Runnable open() {
                        return new Runnable() {
                            @Override
                            public void run() {
                                printTelemetry();
                            }
                        };
                    };
                },
                true
        );
    }

    /**
     * Literally just unschedule that event. This will
     * presumably only be used to modify the delay between
     * telemetry updates, but hey... you do you.
     */
    public static void unscheduleTelemetryUpdater() {
        StringEvents.clear("_1125c_AUTOTELENETRY");
    }

    /**
     * A list of all the possible types.
     */
    public enum Telems { // A list of the different types of telems which are available.
        DATA,     // Contains caption - value pair.
        LINE,     // Contains just a single line of text.
        TELEM,    // The most simple version.
    }
}
