package me.wobblyyyy.pathfinder_ftc;

import me.wobblyyyy.pathfinder.fieldMapping.components.HeadingCoordinate;
import me.wobblyyyy.pathfinder.localizer.Odometry;
import org._11253.lib.robot.phys.components.Motor;

/**
 * An interface designed to keep all of the different odometry options
 * included here on the same page.
 *
 * @author Colin Robertson
 */
public interface AbstractOdometry {
    /**
     * Get the actual odometry system itself.
     *
     * @return the odometry instance in use.
     */
    Odometry getOdometry();

    /**
     * Get the left encoder.
     *
     * @return the left encoder/motor.
     */
    Motor getLeft();

    /**
     * Get the right encoder.
     *
     * @return the right encoder/motor.
     */
    Motor getRight();

    /**
     * Get the front/back encoder.
     *
     * @return the front/back encoder/motor.
     */
    Motor getFrontBack();

    /**
     * Get the position of the robot.
     *
     * @return the position of the robot, represented as a HeadingCoordinate.
     */
    HeadingCoordinate<Double> getPosition();
}
