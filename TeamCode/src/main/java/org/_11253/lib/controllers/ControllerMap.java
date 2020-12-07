/*
 * **
 *
 * Copyright (c) 2020
 * Copyright last updated on 7/23/20, 9:26 PM
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

package org._11253.lib.controllers;

import com.qualcomm.robotcore.hardware.Gamepad;
import org._11253.lib.utils.CommandCore;

import java.util.HashMap;
import java.util.Objects;

/**
 * Controller mapping interface to make mapping buttons a little bit smoother.
 *
 * <p>
 * Enum "States" contains all of the possible boolean states a controller can have.
 * The HashMap (states mapped to Runnables) contains mapping between a specific boolean state and
 * a function for it to execute if it comes out true. If the button is not pressed, or if there is
 * nothing mapped to it, don't do anything.
 * </p>
 * <p>
 * Note that multiple states can be active at once - for example, both the
 * A button and B button can be active at the same time.
 * </p>
 *
 * @author Colin Robertson
 */
public class ControllerMap {
    /**
     * A reference to the physical gamepad itself.
     */
    Gamepad gamepad;

    /**
     * A reference to the parent controller class.
     */
    Controller controller;

    /**
     * A map of all of the commands which should be executed.
     * <p>
     * This is interfaced through the functions bind and unbind.
     * </p>
     */
    public HashMap<States, CommandCore> commandMap = new HashMap<>();

    /**
     * Constructor function which sets the controller map's gamepad.
     *
     * @param gamepad the gamepad which should be used.
     */
    public ControllerMap(Gamepad gamepad,
                         Controller controller) {
        this.gamepad = gamepad;
        this.controller = controller;
    }

    /**
     * A different... uhh... name...? of the bind function, just to be cool.
     *
     * @param state   a state of the gamepad
     * @param command a function to execute
     */
    public final void map(States state, CommandCore command) {
        bind(state, command);
    }

    /**
     * Used to map a certain state to a function.
     *
     * <p>
     * If the state is already included, and the user is
     * attempting to overwrite it, the original
     * state has to be deleted, which is why we remove it
     * first. If the state isn't there, nothing happens.
     * </p>
     *
     * @param state   a state of the gamepad. These states are all
     *                enumerated in the States enum. Obviously, it's
     *                unlikely that I'll ever be able to include every
     *                single conceivable controller state. In this case,
     *                you can just map something another way, not using
     *                this library.
     * @param command a {@link CommandCore} to execute
     */
    public final void bind(States state, CommandCore command) {
        commandMap.remove(state);
        commandMap.put(state, command);
    }

    /**
     * Un-map or un-bind a certain CommandCore from another certain
     * controller state.
     *
     * @param state the state to unmap.
     */
    public final void unmap(States state) {
        unbind(state);
    }

    /**
     * Used to unmap a certain state from the ControllerMap.
     *
     * @param state the state which should be unmapped
     */
    public final void unbind(States state) {
        commandMap.remove(state);
    }

    /**
     * Run all of the code which is stored in the map.
     * <p>
     * We check to make sure there that the command map actually contains
     * the state we need. If it does, we go ahead and run the runnables
     * for that certain thingy. However, if it doesn't, we do absolutely
     * nothing. Epic, I know.
     * </p>
     * <p>
     * This should (hopefully) be run once every 'tick' (every cycle of
     * the loop). If you have a slow phone... sucks. Doesn't it?
     * </p>
     */
    public final void runMap() {
        if (commandMap.containsKey(States.START))
            Objects.requireNonNull(commandMap.get(States.START)).getRunnable(controller.start).run();
        if (commandMap.containsKey(States.GUIDE))
            Objects.requireNonNull(commandMap.get(States.GUIDE)).getRunnable(controller.select).run();
        if (commandMap.containsKey(States.A))
            Objects.requireNonNull(commandMap.get(States.A)).getRunnable(controller.a).run();
        if (commandMap.containsKey(States.B))
            Objects.requireNonNull(commandMap.get(States.B)).getRunnable(controller.b).run();
        if (commandMap.containsKey(States.X))
            Objects.requireNonNull(commandMap.get(States.X)).getRunnable(controller.x).run();
        if (commandMap.containsKey(States.Y))
            Objects.requireNonNull(commandMap.get(States.Y)).getRunnable(controller.y).run();
        if (commandMap.containsKey(States.DPAD_UP))
            Objects.requireNonNull(commandMap.get(States.DPAD_UP)).getRunnable(controller.dpad_up).run();
        if (commandMap.containsKey(States.DPAD_RIGHT))
            Objects.requireNonNull(commandMap.get(States.DPAD_RIGHT)).getRunnable(controller.dpad_right).run();
        if (commandMap.containsKey(States.DPAD_DOWN))
            Objects.requireNonNull(commandMap.get(States.DPAD_DOWN)).getRunnable(controller.dpad_down).run();
        if (commandMap.containsKey(States.DPAD_LEFT))
            Objects.requireNonNull(commandMap.get(States.DPAD_LEFT)).getRunnable(controller.dpad_left).run();
        if (commandMap.containsKey(States.RIGHT_BUMPER))
            Objects.requireNonNull(commandMap.get(States.RIGHT_BUMPER)).getRunnable(controller.right_bumper).run();
        if (commandMap.containsKey(States.LEFT_BUMPER))
            Objects.requireNonNull(commandMap.get(States.LEFT_BUMPER)).getRunnable(controller.left_bumper).run();
        if (commandMap.containsKey(States.RIGHT_STICK))
            Objects.requireNonNull(commandMap.get(States.RIGHT_STICK)).getRunnable(controller.right_stick_x != 0 || controller.right_stick_y != 0).run();
        if (commandMap.containsKey(States.LEFT_STICK))
            Objects.requireNonNull(commandMap.get(States.LEFT_STICK)).getRunnable(controller.left_stick_x != 0 || controller.left_stick_y != 0).run();
        if (commandMap.containsKey(States.RIGHT_STICK_X))
            Objects.requireNonNull(commandMap.get(States.RIGHT_STICK_X)).getRunnable(controller.right_stick_x != 0).run();
        if (commandMap.containsKey(States.RIGHT_STICK_Y))
            Objects.requireNonNull(commandMap.get(States.RIGHT_STICK_Y)).getRunnable(controller.right_stick_y != 0).run();
        if (commandMap.containsKey(States.LEFT_STICK_X))
            Objects.requireNonNull(commandMap.get(States.LEFT_STICK_X)).getRunnable(controller.left_stick_x != 0).run();
        if (commandMap.containsKey(States.LEFT_STICK_Y))
            Objects.requireNonNull(commandMap.get(States.LEFT_STICK_Y)).getRunnable(controller.left_stick_y != 0).run();
        if (commandMap.containsKey(States.RIGHT_OR_LEFT_X))
            Objects.requireNonNull(commandMap.get(States.RIGHT_OR_LEFT_Y))
                    .getRunnable(
                            controller.right_stick_y != 0 ||
                                    controller.left_stick_y != 0
                    );
        if (commandMap.containsKey(States.RIGHT_OR_LEFT_Y))
            Objects.requireNonNull(commandMap.get(States.RIGHT_OR_LEFT_X))
                    .getRunnable(
                            controller.right_stick_x != 0 ||
                                    controller.left_stick_x != 0
                    );
        if (commandMap.containsKey(States.STICK))
            Objects.requireNonNull(commandMap.get(States.STICK)).getRunnable(controller.right_stick_x != 0 || controller.right_stick_y != 0 || controller.left_stick_x != 0 || controller.left_stick_y != 0).run();
        if (commandMap.containsKey(States.RIGHT_TRIGGER))
            Objects.requireNonNull(commandMap.get(States.RIGHT_TRIGGER)).getRunnable(controller.right_trigger != 0).run();
        if (commandMap.containsKey(States.LEFT_TRIGGER))
            Objects.requireNonNull(commandMap.get(States.LEFT_TRIGGER)).getRunnable(controller.left_trigger != 0).run();
        if (commandMap.containsKey(States.RIGHT_TRIGGER_HALF))
            Objects.requireNonNull(commandMap.get(States.RIGHT_TRIGGER_HALF)).getRunnable(controller.right_trigger > 0.5);
        if (commandMap.containsKey(States.LEFT_TRIGGER_HALF))
            Objects.requireNonNull(commandMap.get(States.LEFT_TRIGGER_HALF)).getRunnable(controller.left_trigger > 0.5);
        if (commandMap.containsKey(States.RIGHT_TRIGGER_QUARTER))
            Objects.requireNonNull(commandMap.get(States.RIGHT_TRIGGER_QUARTER)).getRunnable(controller.right_trigger > 0.25);
        if (commandMap.containsKey(States.LEFT_TRIGGER_QUARTER))
            Objects.requireNonNull(commandMap.get(States.LEFT_TRIGGER_QUARTER)).getRunnable(controller.left_trigger > 0.25);
        if (commandMap.containsKey(States.RIGHT_TRIGGER_THREE_QUARTERS))
            Objects.requireNonNull(commandMap.get(States.RIGHT_TRIGGER_THREE_QUARTERS)).getRunnable(controller.right_trigger > 0.75);
        if (commandMap.containsKey(States.LEFT_TRIGGER_THREE_QUARTERS))
            Objects.requireNonNull(commandMap.get(States.LEFT_TRIGGER_THREE_QUARTERS)).getRunnable(controller.left_trigger > 0.75);
    }

    /**
     * Enums for every possible "state" the controller can be in.
     *
     * <p>
     * Note that states for stick movements only accept the following conditions:
     *     <ul>
     *         <li>"RIGHT_STICK": Right stick's X <b>or</b> Y is offset by anything at all.</li>
     *         <li>"LEfT_STICK": Left stick's X <b>or</b> Y is offset by anything at all.</li>
     *         <li>"STICK": Right <b>or</b> left stick's X <b>or</b> Y is offset by anything at all</li>
     *     </ul>
     *     This means there is no specific states for left stick offset or right stick offset.
     * </p>
     */
    public enum States {
        START,
        GUIDE,
        A,
        B,
        X,
        Y,
        RIGHT_BUMPER,
        LEFT_BUMPER,
        DPAD_UP,
        DPAD_RIGHT,
        DPAD_DOWN,
        DPAD_LEFT,
        RIGHT_STICK,
        LEFT_STICK,
        RIGHT_STICK_BUTTON,
        LEFT_STICK_BUTTON,
        RIGHT_STICK_X,
        RIGHT_STICK_Y,
        LEFT_STICK_X,
        LEFT_STICK_Y,
        RIGHT_OR_LEFT_X,
        RIGHT_OR_LEFT_Y,
        STICK,
        RIGHT_TRIGGER,
        LEFT_TRIGGER,
        RIGHT_TRIGGER_HALF,
        LEFT_TRIGGER_HALF,
        RIGHT_TRIGGER_QUARTER,
        LEFT_TRIGGER_QUARTER,
        RIGHT_TRIGGER_THREE_QUARTERS,
        LEFT_TRIGGER_THREE_QUARTERS
    }
}
