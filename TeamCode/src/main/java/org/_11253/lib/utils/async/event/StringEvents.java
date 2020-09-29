/*
 * **
 *
 * Copyright (c) 2020
 * Copyright last updated on 6/10/20, 10:52 PM
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

package org._11253.lib.utils.async.event;

import org._11253.lib.utils.Timed;
import org._11253.lib.utils.telem.Telemetry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * Provides a method of interacting with events using a string key.
 * <p>
 * Basically, use this or I'll shoot your entire extended family.
 * I'm a little tired right now, so these docs might not be entirely
 * coherent, but... good luck. That's all I'm saying.
 * </p>
 *
 * @author Colin Robertson
 */
public class StringEvents {
    /**
     * The actual map of string to events.
     * <p>
     * String represents a STRING key for the event scheduler,
     * and Events represents an instance of the event scheduler.
     * </p>
     * <p>
     * In general, you'll only want to assign one event, whether
     * repeating or not, to each string / key. This makes it so you
     * can easily schedule and then cancel events, even if
     * they're repeating.
     * </p>
     */
    public static HashMap<String, Events> events = new HashMap<>();

    /**
     * Tick function which ticks all the event schedulers.
     * <p>
     * Every time this is run, all of the event handler's contained
     * within the 'events' HashMap are 'ticked.' It also adds telemetry
     * saying how many handles whatever thing has.
     * </p>
     */
    public static void tick() {
        for (HashMap.Entry<String, Events> entry : events.entrySet()) {
            entry.getValue().tick();
            Telemetry.addData("StringEvents_handles_" + entry.getKey(),
                    "Handles of " + entry.getKey(),
                    entry.getValue().events.size() + "");
        }
    }

    /**
     * Schedules an event.
     * <p>
     * This essentially just interfaces with one of the event
     * schedulers, based on whichever string key you choose to use.
     * In general, I would suggest you use something generic, such
     * as "scheduler" or something simple.
     * </p>
     *
     * @param name         the string name of the event scheduler you'd like to access
     * @param duration     how long the event should last
     * @param delay        the delay before the event takes place
     * @param timed        the actual timed element which should be executed
     * @param shouldRepeat whether or not the event should repeat itself
     */
    public static void schedule(final String name,
                                final long duration,
                                final long delay,
                                final Timed timed,
                                final boolean shouldRepeat) {
        if (events.containsKey(name)) {
            Events ev = events.get(name);
            assert ev != null;
            ev.schedule(duration, (int) delay, timed, shouldRepeat);
        } else {
            Events ev = new Events();
            ev.schedule(duration, (int) delay, timed, shouldRepeat);
            events.put(name, ev);
        }
    }

    /**
     * Delete a string key thingy.
     * <p>
     * This makes it so none of the events contained within that
     * specific event scheduler do anything at all.
     * </p>
     * <p>
     * You can use this to 'cancel' an event you've scheduled
     * in the past.
     * </p>
     *
     * @param name the key to delete
     */
    public static void clear(final String name) {
        events.remove(name);
    }

    /**
     * Return a single event based on a name string.
     * <p>
     * This just returns the first element in the array list
     * of all of the elements, gathered from {@link StringEvents#queryAll(String)}
     * </p>
     *
     * @param name the key to query for
     * @return the first scheduled Timed under a certain query.
     */
    public static Timed query(final String name) {
        return queryAll(name).get(0);
    }

    /**
     * Get an array list of all the scheduled events under a key.
     * <p>
     * If you only want to get a single Timed, which is, most
     * of the time, the case, you should instead use the
     * {@link StringEvents#query(String)} method.
     * </p>
     *
     * @param name the key you'd like to query.
     * @return an ArrayList of Timed elements from the key
     */
    public static ArrayList<Timed> queryAll(final String name) {
        ArrayList<Timed> list = new ArrayList<>();
        if (events.containsKey(name)) {
            for (HashMap.Entry<Long, Timed> entry : Objects.requireNonNull(
                    events.get(name)).events.entrySet()) {
                list.add(entry.getValue());
            }
        }
        return list;
    }
}
