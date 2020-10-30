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

package org._11253.lib.controllers;

import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * Wrapper class for FTC's default Gamepad.
 *
 * <p>
 * This provides some additional functionality over the default gamepad. Right below this
 * you'll find some pretty cool information I may or may not have copy-pasted right from
 * the Gamepad class included in the FTC library.
 * </p>
 *
 * <p>
 * Also, this is a bit irrelevant, but I do have to say. The Vim keystrokes required to
 * copy-paste some text are as follows:
 * <code>
 * ESC,
 * SHIFT-V,
 * Y,
 * ESC,
 * P,
 * </code>
 * It looks a lot less interesting when formatted that way, but hey - it's pretty swaggy.
 * </p>
 *
 * <p>
 * The buttons, analog sticks, and triggers are represented a public
 * member variables that can be read from or written to directly.
 * </p>
 *
 * <p>
 * Analog sticks are represented as floats that range from -1.0 to +1.0. They will be 0.0 while at
 * rest. The horizontal axis is labeled x, and the vertical axis is labeled y.
 * </p>
 *
 * <p>
 * Triggers are represented as floats that range from 0.0 to 1.0. They will be at 0.0 while at
 * rest.
 * </p>
 *
 * <p>
 * Buttons are boolean values. They will be true if the button is pressed, otherwise they will be
 * false.
 * </p>
 *
 * <p>
 * The codes KEYCODE_BUTTON_SELECT and KEYCODE_BACK are both be handled as a "back" button event.
 * Older Android devices (Kit Kat) map a Logitech F310 "back" button press to a KEYCODE_BUTTON_SELECT event.
 * Newer Android devices (Marshmallow or greater) map this "back" button press to a KEYCODE_BACK event.
 * Also, the REV Robotics Gamepad (REV-31-1159) has a "select" button instead of a "back" button on the gamepad.
 * </p>
 *
 * <p>
 * The dpad is represented as 4 buttons, dpad_up, dpad_down, dpad_left, and dpad_right
 * </p>
 *
 * @author Colin Robertson
 */
public class Controller {
    /**
     * Inactive double state.
     *
     * <p>
     * IA is short for INACTIVE. When the controller is in a
     * state where user input is disregarded, this state takes
     * the place of whatever readings come from the actual gamepad,
     * in order to make user input not have any value.
     * </p>
     */
    private final double IA_DOUBLE = 0.0;

    /**
     * Inactive boolean state.
     *
     * <p>
     * IA is short for INACTIVE. When the controller is in a
     * state where user input is disregarded, this state takes
     * the place of whatever readings come from the actual gamepad,
     * in order to make user input not have any value.
     * </p>
     */
    private final boolean IA_BOOLEAN = false;

    /**
     * Use either inactive or current double.
     *
     * @param cmp active state
     * @return either active or inactive state
     */
    private double CMP_DOUBLE(double cmp) {
        if (manager.isUserControlled()) return cmp;
        else return IA_DOUBLE;
    }

    /**
     * Use either inactive or current boolean.
     *
     * @param cmp active state
     * @return either active or inactive state
     */
    private boolean CMP_BOOLEAN(boolean cmp) {
        if (manager.isUserControlled()) return cmp;
        else return IA_BOOLEAN;
    }

    /**
     * The controller's map. {@link ControllerMap}
     */
    public ControllerMap map;

    /**
     * The controller's manager. {@link ControllerManager}
     *
     * <p>
     * Managers are a new thing I'm attempting to try out, just to make
     * combining autonomous and manually-operated functionality earlier.
     * Managers basically allow for the robot to control whether or not
     * user inputs have any value or meaning.
     * </p>
     */
    public ControllerManager manager;

    /**
     * The controller's internal gamepad.
     *
     * <p>
     * The Controller class is designed to be a wrapper, abstracting and making
     * it easier to use the default gamepad. There will likely and inevitably be
     * some random and scarcely-used functionality that my Controller wrapper
     * is missing compared to the (not as cool) gamepad.
     * </p>
     */
    private Gamepad gamepad;

    /**
     * Create a new controller and a new controller map.
     *
     * @param gamepad the gamepad which the controller should be created off of.
     */
    public Controller(Gamepad gamepad) {
        this.gamepad = gamepad;
        map = new ControllerMap(gamepad);
        manager = new ControllerManager();
    }

    /**
     * Get the left stick's x value
     *
     * @return left stick's x value
     */
    public final double getLeftX() {
        return CMP_DOUBLE(gamepad.left_stick_x);
    }

    /**
     * Get the left stick's inverted x value
     *
     * @return right stick's y value
     */
    public final double getInvLeftX() {
        return CMP_DOUBLE(-gamepad.left_stick_x);
    }

    /**
     * Get the left stick's y value
     *
     * @return left stick's y value
     */
    public final double getLeftY() {
        return CMP_DOUBLE(gamepad.left_stick_y);
    }

    /**
     * Get the left stick's inverted y value
     *
     * @return right stick's y value
     */
    public final double getInvLeftY() {
        return CMP_DOUBLE(-gamepad.left_stick_y);
    }

    /**
     * Get the right stick's x value
     *
     * @return right stick's x value
     */
    public final double getRightX() {
        return CMP_DOUBLE(gamepad.right_stick_x);
    }

    /**
     * Get the right stick's x value
     *
     * @return right stick's y value
     */
    public final double getInvRightX() {
        return CMP_DOUBLE(-gamepad.right_stick_x);
    }

    /**
     * Get the right stick's y value
     *
     * @return right stick's y value
     */
    public final double getRightY() {
        return CMP_DOUBLE(gamepad.right_stick_y);
    }

    /**
     * Get the right stick's y value
     *
     * @return right stick's y value
     */
    public final double getInvRightY() {
        return CMP_DOUBLE(-gamepad.right_stick_y);
    }

    /**
     * Get the right trigger's current value
     *
     * @return the right trigger's value
     */
    public final double getRightTrigger() {
        return CMP_DOUBLE(gamepad.right_trigger);
    }

    /**
     * Get the left trigger's current value
     *
     * @return the left trigger's value
     */
    public final double getLeftTrigger() {
        return CMP_DOUBLE(gamepad.left_trigger);
    }

    /**
     * Boolean whether or not button is pressed down.
     *
     * @return whether the button is pressed down or not (true = yes, false = no)
     */
    public final boolean getA() {
        return CMP_BOOLEAN(gamepad.a);
    }

    /**
     * Boolean whether or not button is pressed down.
     *
     * @return whether the button is pressed down or not (true = yes, false = no)
     */
    public final boolean getB() {
        return CMP_BOOLEAN(gamepad.b);
    }

    /**
     * Boolean whether or not button is pressed down.
     *
     * @return whether the button is pressed down or not (true = yes, false = no)
     */
    public final boolean getX() {
        return CMP_BOOLEAN(gamepad.x);
    }

    /**
     * Boolean whether or not button is pressed down.
     *
     * @return whether the button is pressed down or not (true = yes, false = no)
     */
    public final boolean getY() {
        return CMP_BOOLEAN(gamepad.y);
    }

    /**
     * Boolean whether or not button is pressed down.
     *
     * @return whether the button is pressed down or not (true = yes, false = no)
     */
    public final boolean getDpadUp() {
        return CMP_BOOLEAN(gamepad.dpad_up);
    }

    /**
     * Boolean whether or not button is pressed down.
     *
     * @return whether the button is pressed down or not (true = yes, false = no)
     */
    public final boolean getDpadRight() {
        return CMP_BOOLEAN(gamepad.dpad_right);
    }

    /**
     * Boolean whether or not button is pressed down.
     *
     * @return whether the button is pressed down or not (true = yes, false = no)
     */
    public final boolean getDpadDown() {
        return CMP_BOOLEAN(gamepad.dpad_down);
    }

    /**
     * Boolean whether or not button is pressed down.
     *
     * @return whether the button is pressed down or not (true = yes, false = no)
     */
    public final boolean getDpadLeft() {
        return CMP_BOOLEAN(gamepad.dpad_left);
    }

    /**
     * Boolean whether or not button is pressed down.
     *
     * @return whether the button is pressed down or not (true = yes, false = no)
     */
    public final boolean getRightBumper() {
        return CMP_BOOLEAN(gamepad.right_bumper);
    }

    /**
     * Boolean whether or not button is pressed down.
     *
     * @return whether the button is pressed down or not (true = yes, false = no)
     */
    public final boolean getLeftBumper() {
        return CMP_BOOLEAN(gamepad.left_bumper);
    }

    /**
     * Boolean whether or not button is pressed down.
     *
     * @return whether the button is pressed down or not (true = yes, false = no)
     */
    public final boolean getStart() {
        return CMP_BOOLEAN(gamepad.start);
    }

    /**
     * Boolean whether or not button is pressed down.
     *
     * @return whether the button is pressed down or not (true = yes, false = no)
     */
    public final boolean getSelect() {
        return CMP_BOOLEAN(gamepad.guide);
    }

    /**
     * Get the internally-used gamepad.
     *
     * <p>
     * I truly don't have a clue what purpose you could possibly have where
     * you'd need to get the comparatively very uncool and un-swaggy FTC gamepad
     * compared to this one, but the option is there regardless.
     * </p>
     *
     * @return this instance of Controller's gamepad.
     */
    public final Gamepad getGamepad() {
        return gamepad;
    }

    /**
     * Set the internally-assigned identification code.
     *
     * <p>
     * This is, by default, set by Android OS. This isn't some FTC thing or
     * whatever, so I truthfully don't have a clue why you might possibly want
     * to even think about changing it. Regardless, the option is there.
     * </p>
     *
     * @param id the new id.
     */
    public void setId(int id) {
        gamepad.setGamepadId(id);
    }

    /**
     * Get the internally-assigned identification code.
     *
     * <p>
     * This is, by default, set by Android OS. This isn't some FTC thing or
     * whatever, so I truthfully don't have a clue why you might possibly want
     * to even think about changing it. Regardless, the option is there.
     * </p>
     *
     * @return the current id.
     */
    public int getId() {
        return gamepad.getGamepadId();
    }

    /**
     * A timestamp, relative to the last time an event was detected.
     *
     * <p>
     * As consistent with a couple other methods towards the bottom of this file,
     * this timestamp isn't something I've ever found a use for. Regardless, it's here,
     * just in case you want it. Hey, you never know, right?
     * </p>
     *
     * @return how long it's been since an event was last detected.
     */
    public long getTimestamp() {
        return gamepad.timestamp;
    }

    /**
     * Set the dead zone of the controller/gamepad joysticks.
     * Read more to learn more.
     *
     * <p>
     * Dead zones, in case you weren't aware, are the zones of the controller where,
     * regardless of whatever reading the controller is getting, 0 is still returned.
     * This is because most controllers will return a value so incredibly small that
     * it might as well be zero. Constantly setting the power of motors to be something
     * non-zero will make them whine, and it'll sound really god damn obnoxious.
     * </p>
     *
     * <p>
     * The default dead zone is most likely good and you probably won't need to change it.
     * </p>
     *
     * @param deadZone the dead zone.
     */
    public void setJoystickDeadZone(float deadZone) {
        gamepad.setJoystickDeadzone(deadZone);
    }
}
