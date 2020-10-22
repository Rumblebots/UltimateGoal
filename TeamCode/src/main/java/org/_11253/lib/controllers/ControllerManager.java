package org._11253.lib.controllers;

import org._11253.lib.ints.BLTManager;

public class ControllerManager extends BLTManager {
    @Override
    public boolean isUserControlled() {
        return isActive;
    }

    @Override
    public void enableUserControl() {
        isActive = true;
    }

    @Override
    public void disableUserControl() {
        isActive = false;
    }

    /**
     * The condition which must be true to automatically
     * enable.
     *
     * <p>
     * This is always false, so the robot can't control, without
     * user code, whether or not the driver can control the robot.
     * </p>
     *
     * @return false
     */
    @Override
    public boolean enableCondition() {
        return false;
    }

    /**
     * The condition which must be true to automatically
     * disable.
     *
     * <p>
     * This is always false, so the robot can't control, without
     * user code, whether or not the driver can control the robot.
     * </p>
     *
     * @return false
     */
    @Override
    public boolean disableCondition() {
        return false;
    }
}
