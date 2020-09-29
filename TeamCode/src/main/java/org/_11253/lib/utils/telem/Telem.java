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

/**
 * Core telemetry object.
 * <p>
 * This contains a couple key things, like
 * the name of the telemetry (key), and
 * the message of the telemetry (value).
 * This is implemented in other places, which
 * provide higher-level abstractions, for very cool
 * purposes of course.
 * </p>
 *
 * @author Colin Robertson
 */
public class Telem {
    /**
     * The identifier / name / key of the telemetry.
     * <p>
     * This is a private string which is to be accessed through
     * getter / setter methods. It's important for accessing Telem
     * objects, as the main provided implementation of Telemetry
     * uses these keys to reference certain elements of telemetry.
     * </p>
     */
    private String name;

    /**
     * The message which the telemetry contains
     * <p>
     * This a string, likely just a single line. It's further
     * abstracted and extended through other implementations,
     * such as Data's caption, separator, and data model.
     * </p>
     */
    private String message = "";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
