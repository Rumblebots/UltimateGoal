package me.wobblyyyy.pathfinder_ftc.configs._3;

import me.wobblyyyy.pathfinder_ftc.threeWheel.ThreeWheelConfig;

/**
 * A generic and empty frame for a three wheel configuration.
 */
public class Empty implements ThreeWheelConfig {
    @Override
    public double getCpr() {
        return 0;
    }

    @Override
    public double getWheelDiameter() {
        return 0;
    }

    @Override
    public double getLeftOffset() {
        return 0;
    }

    @Override
    public double getRightOffset() {
        return 0;
    }

    @Override
    public double getFrontBackOffset() {
        return 0;
    }
}
