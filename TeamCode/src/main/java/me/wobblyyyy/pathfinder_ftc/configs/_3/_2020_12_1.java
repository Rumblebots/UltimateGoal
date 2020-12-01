package me.wobblyyyy.pathfinder_ftc.configs._3;

import me.wobblyyyy.pathfinder_ftc.threeWheel.ThreeWheelConfig;

/**
 * Odometry configuration for the Rumblebots as of:
 * December 1st, 2020 (12/1/2020)
 */
public class _2020_12_1 implements ThreeWheelConfig {
    @Override
    public double getCpr() {
        return 360.000;
    }

    @Override
    public double getWheelDiameter() {
        return 2.500;
    }

    @Override
    public double getLeftOffset() {
        return 7.830;
    }

    @Override
    public double getRightOffset() {
        return 7.830;
    }

    @Override
    public double getFrontBackOffset() {
        return 5.414;
    }
}
