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
 * Pretty much a wrapper for FTC's data.
 * <p>
 * Contains CAPTION,
 * SEPARATOR, and
 * DATA,
 * ... all of which can be edited through
 * functions included here.
 * From the Telemetry class' more generalized
 * telemetry manager this should be a lot easier to do
 * and you probably won't even have to do this in the first
 * place, but hey. It's still here.
 * </p>
 *
 * @author Colin Robertson
 */
public class Data extends Telem {
    /**
     * The CAPTION the data has.
     */
    private String caption = "Sample caption";

    /**
     * The SEPARATOR contained in the data.
     */
    private String separator = ": ";

    /**
     * The DATA portion of the data.
     */
    private String data = "Sample data";

    /**
     * Get a combination of cap, sep, & data
     *
     * @return new message for Telem to handle
     */
    private String composeMessage() {
        return caption + separator + data;
    }

    /**
     * Run every time any of the values are updated
     * Sets the Telem's message
     */
    private void setComposedMessage() {
        setMessage(composeMessage());
    }

    /**
     * Gets the Data's caption
     *
     * @return caption
     */
    public final String getCaption() {
        return caption;
    }

    public final void setCaption(Object newCaption) {
        setCaption(newCaption, true);
    }

    /**
     * Sets the Data's caption
     *
     * @param newCaption the new caption
     */
    public final void setCaption(Object newCaption, boolean shouldUpdate) {
        this.caption = newCaption.toString();
        if (shouldUpdate) {
            setComposedMessage();
        }
    }

    /**
     * Gets the Data's separator
     *
     * @return separator
     */
    public final String getSeparator() {
        return separator;
    }

    public final void setSeparator(Object newSeparator) {
        setSeparator(newSeparator, true);
    }

    /**
     * Sets the Data's separator
     *
     * @param newSeparator the new separator
     */
    public final void setSeparator(Object newSeparator, boolean shouldUpdate) {
        separator = newSeparator.toString();
        if (shouldUpdate) {
            setComposedMessage();
        }
    }

    /**
     * Gets the Data's data
     *
     * @return data
     */
    public final String getData() {
        return data;
    }

    public final void setData(Object newData) {
        setData(newData, true);
    }

    /**
     * Sets the Data's data
     *
     * @param newData the new data
     */
    public final void setData(Object newData, boolean shouldUpdate) {
        this.data = newData.toString();
        if (shouldUpdate) {
            setComposedMessage();
        }
    }
}
