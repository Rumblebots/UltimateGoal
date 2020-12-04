package me.wobblyyyy.pathfinder_ftc.pathfinder;

import me.wobblyyyy.pathfinder.localizer.Odometry;

/**
 * Manager for all of the pathfinder-related things going on here.
 *
 * @author Colin Robertson
 */
public class PfManager {
    /**
     * The manager for the field's map.
     */
    public static PfMapManager map;

    /**
     * The manager for the robot's drivetrain.
     */
    public static PfDrivetrainManager drivetrain;

    /**
     * The odometry system that's in use.
     */
    public static Odometry odometry;
}
