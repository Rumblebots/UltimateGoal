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
        return cmp;
//        if (manager.isUserControlled()) return cmp;
//        else return IA_DOUBLE;
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

    protected boolean a = false,
            b = false,
            x = false,
            y = false,
            dpad_up = false,
            dpad_down = false,
            dpad_left = false,
            dpad_right = false,
            start = false,
            select = false,
            right_bumper = false,
            left_bumper = false;

    protected double right_trigger = 0.0,
            left_trigger = 0.0,
            right_stick_x = 0.0,
            left_stick_x = 0.0,
            right_stick_y = 0.0,
            left_stick_y = 0.0;

    /**
     * Create a new controller and a new controller map.
     *
     * @param gamepad the gamepad which the controller should be created off of.
     */
    public Controller(Gamepad gamepad) {
        this.gamepad = gamepad;
        map = new ControllerMap(gamepad, this);
        manager = new ControllerManager();
    }

    /**
     * Get the left stick's x value
     *
     * @return left stick's x value
     */
    public final double getLeftX() {
        return left_stick_x;
    }

    /**
     * Get the left stick's inverted x value
     *
     * @return right stick's y value
     */
    public final double getInvLeftX() {
        return -left_stick_x;
    }

    /**
     * Get the left stick's y value
     *
     * @return left stick's y value
     */
    public final double getLeftY() {
        return left_stick_y;
    }

    /**
     * Get the left stick's inverted y value
     *
     * @return right stick's y value
     */
    public final double getInvLeftY() {
        return -left_stick_y;
    }

    /**
     * Get the right stick's x value
     *
     * @return right stick's x value
     */
    public final double getRightX() {
        return right_stick_x;
    }

    /**
     * Get the right stick's x value
     *
     * @return right stick's y value
     */
    public final double getInvRightX() {
        return -right_stick_x;
    }

    /**
     * Get the right stick's y value
     *
     * @return right stick's y value
     */
    public final double getRightY() {
        return right_stick_y;
    }

    /**
     * Get the right stick's y value
     *
     * @return right stick's y value
     */
    public final double getInvRightY() {
        return -right_stick_y;
    }

    /**
     * Get the right trigger's current value
     *
     * @return the right trigger's value
     */
    public final double getRightTrigger() {
        return right_trigger;
    }

    /**
     * Get the left trigger's current value
     *
     * @return the left trigger's value
     */
    public final double getLeftTrigger() {
        return left_trigger;
    }

    /**
     * Boolean whether or not button is pressed down.
     *
     * @return whether the button is pressed down or not (true = yes, false = no)
     */
    public final boolean getA() {
        return a;
    }

    /**
     * Boolean whether or not button is pressed down.
     *
     * @return whether the button is pressed down or not (true = yes, false = no)
     */
    public final boolean getB() {
        return b;
    }

    /**
     * Boolean whether or not button is pressed down.
     *
     * @return whether the button is pressed down or not (true = yes, false = no)
     */
    public final boolean getX() {
        return x;
    }

    /**
     * Boolean whether or not button is pressed down.
     *
     * @return whether the button is pressed down or not (true = yes, false = no)
     */
    public final boolean getY() {
        return y;
    }

    /**
     * Boolean whether or not button is pressed down.
     *
     * @return whether the button is pressed down or not (true = yes, false = no)
     */
    public final boolean getDpadUp() {
        return dpad_up;
    }

    /**
     * Boolean whether or not button is pressed down.
     *
     * @return whether the button is pressed down or not (true = yes, false = no)
     */
    public final boolean getDpadRight() {
        return dpad_right;
    }

    /**
     * Boolean whether or not button is pressed down.
     *
     * @return whether the button is pressed down or not (true = yes, false = no)
     */
    public final boolean getDpadDown() {
        return dpad_down;
    }

    /**
     * Boolean whether or not button is pressed down.
     *
     * @return whether the button is pressed down or not (true = yes, false = no)
     */
    public final boolean getDpadLeft() {
        return dpad_left;
    }

    /**
     * Boolean whether or not button is pressed down.
     *
     * @return whether the button is pressed down or not (true = yes, false = no)
     */
    public final boolean getRightBumper() {
        return right_bumper;
    }

    /**
     * Boolean whether or not button is pressed down.
     *
     * @return whether the button is pressed down or not (true = yes, false = no)
     */
    public final boolean getLeftBumper() {
        return left_bumper;
    }

    /**
     * Boolean whether or not button is pressed down.
     *
     * @return whether the button is pressed down or not (true = yes, false = no)
     */
    public final boolean getStart() {
        return start;
    }

    /**
     * Boolean whether or not button is pressed down.
     *
     * @return whether the button is pressed down or not (true = yes, false = no)
     */
    public final boolean getSelect() {
        return select;
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

    public void setGamepad(Gamepad gamepad) {
        this.gamepad = gamepad;
    }

    public void update() {
        a = gamepad.a;
        b = gamepad.b;
        x = gamepad.x;
        y = gamepad.y;
        right_bumper = gamepad.right_bumper;
        left_bumper = gamepad.left_bumper;
        right_trigger = gamepad.right_trigger;
        left_trigger = gamepad.left_trigger;
        dpad_up = gamepad.dpad_up;
        dpad_down = gamepad.dpad_down;
        dpad_left = gamepad.dpad_left;
        dpad_right = gamepad.dpad_right;
        start = gamepad.start;
        select = gamepad.guide;
        right_stick_x = gamepad.right_stick_x;
        right_stick_y = gamepad.right_stick_y;
        left_stick_x = gamepad.left_stick_x;
        left_stick_y = gamepad.left_stick_y;
    }

    public void setA(boolean a) {
        this.a = a;
    }

    public void setB(boolean b) {
        this.b = b;
    }

    public void setX(boolean x) {
        this.x = x;
    }

    public void setY(boolean y) {
        this.y = y;
    }

    public void setDpadUp(boolean dpad_up) {
        this.dpad_up = dpad_up;
    }

    public void setDpadDown(boolean dpad_down) {
        this.dpad_down = dpad_down;
    }

    public void setDpadLeft(boolean dpad_left) {
        this.dpad_left = dpad_left;
    }

    public void setDpadRight(boolean dpad_right) {
        this.dpad_right = dpad_right;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public void setRightBumper(boolean right_bumper) {
        this.right_bumper = right_bumper;
    }

    public void setLeftBumper(boolean left_bumper) {
        this.left_bumper = left_bumper;
    }

    public void setRightTrigger(double right_trigger) {
        this.right_trigger = right_trigger;
    }

    public void setLeftTrigger(double left_trigger) {
        this.left_trigger = left_trigger;
    }

    public void setRightStickX(double right_stick_x) {
        this.right_stick_x = right_stick_x;
    }

    public void setLeftStickX(double left_stick_x) {
        this.left_stick_x = left_stick_x;
    }

    public void setRightStickY(double right_stick_y) {
        this.right_stick_y = right_stick_y;
    }

    public void setLeftStickY(double left_stick_y) {
        this.left_stick_y = left_stick_y;
    }
}
