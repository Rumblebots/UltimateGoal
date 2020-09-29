/*
 * **
 *
 * Copyright (c) 2020
 * Copyright last updated on 6/9/20, 5:49 PM
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

package org._11253.lib.utils.jrep;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Custom implementation of Java's default List.
 * This is primarily used so items can be added as an array of
 * variable arguments instead of having to execute the add function
 * of the default list several times.
 * The list contained inside of this can still be retried, so
 * should, for some reason, you want to access that list, you
 * can do so with 'list name'.list.
 * TODO add a method of getting list values
 *
 * @param <E>
 * @author Colin Robertson
 */
public class ListWrapper<E> {
    public List<E> list;

    public ListWrapper() {
        list = new ArrayList<>();
    }

    public ListWrapper(ArrayList<E> list) {
        this.list = list;
    }

    @SafeVarargs
    public final void add(E... es) {
        list.addAll(Arrays.asList(es));
    }
}
