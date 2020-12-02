package me.wobblyyyy.pathfinder_ftc.configs._3;

import me.wobblyyyy.pathfinder_ftc.threeWheel.ThreeWheelConfig;

/**
 * Odometry configuration for the Rumblebots as of:
 * December 1st, 2020 (12/1/2020)
 */
public class _2020_12_1 implements ThreeWheelConfig {
    /**
     * Counts per rotation.
     */
    @Override
    public double getCpr() {
        return 1440.000;
    }

    /**
     * Diameter of the odometry wheels.
     */
    @Override
    public double getWheelDiameter() {
        return 2.500;
    }

    /**
     * Left offset.
     */
    @Override
    public double getLeftOffset() {
        return 7.830;
    }

    /**
     * Right offset.
     */
    @Override
    public double getRightOffset() {
        return 7.830;
    }

    /**
     * Front/back offset.
     */
    @Override
    public double getFrontBackOffset() {
        return 5.414;
    }
}
