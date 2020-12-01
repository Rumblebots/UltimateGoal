package me.wobblyyyy.pathfinder_ftc.threeWheel;

/**
 * A basic interface for configuring odometry systems.
 *
 * <p>
 * Rather than taking the COMPLICATED way out of things, I'm
 * just going to use interfaces like this to quickly set up
 * different odometry configurations.
 * </p>
 *
 * <p>
 * If you're confused right now, fear not! I am as well. Anyways,
 * you're going to want to create a class that implements this
 * interface (OdometryConfiguration) and set up all of the values
 * correctly. This ensures that your configurations aren't lacking
 * and are fully set up.
 * </p>
 *
 * @author Colin Robertson
 */
public interface ThreeWheelConfig {
    /**
     * Counts per rotation.
     *
     * @return CPR.
     */
    double getCpr();

    /**
     * The diameter of a wheel.
     *
     * @return wheel diameter.
     */
    double getWheelDiameter();

    /**
     * Left offset (inches).
     *
     * @return offset, in inches.
     */
    double getLeftOffset();

    /**
     * Right offset (inches).
     *
     * @return offset, in inches.
     */
    double getRightOffset();

    /**
     * Front/back offset (inches).
     *
     * @return offset, in inches.
     */
    double getFrontBackOffset();
}
