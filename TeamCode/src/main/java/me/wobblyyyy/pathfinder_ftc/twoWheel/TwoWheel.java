package me.wobblyyyy.pathfinder_ftc.twoWheel;

import me.wobblyyyy.pathfinder.fieldMapping.components.HeadingCoordinate;
import me.wobblyyyy.pathfinder.localizer.Odometry;
import me.wobblyyyy.pathfinder.localizer.ThreeWheelCore;
import me.wobblyyyy.pathfinder_ftc.AbstractOdometry;
import org._11253.lib.robot.phys.components.Motor;

/**
 * A custom implementation of a two-wheel odometry system.
 *
 * <p>
 * PLEASE NOTE!!! Two-wheel odometry requires the usage of a gyroscope,
 * such as an IMU. Most phones have an IMU. The Control Hub even has an
 * IMU. You just need to get that set up before proceeding. Best of luck.
 * </p>
 *
 * <p>
 * This class contains a bunch of different utilities that can be used
 * specifically in the FTC competition. Rather than using something like
 * {@link ThreeWheelCore}, which would definitely provide the same results,
 * I've ever-so-graciously provided you the wonderful opportunity of using
 * some obviously absolutely fantastic code. On a more serious note, this
 * allows you to get an odometry system up and running reliably and quickly
 * without having to spend tons of time digging into code and math.
 * </p>
 *
 * @author Colin Robertson
 */
public class TwoWheel implements AbstractOdometry {
    @Override
    public Odometry getOdometry() {
        return null;
    }

    @Override
    public Motor getLeft() {
        return null;
    }

    @Override
    public Motor getRight() {
        return null;
    }

    @Override
    public Motor getFrontBack() {
        return null;
    }

    @Override
    public HeadingCoordinate<Double> getPosition() {
        return null;
    }
}
