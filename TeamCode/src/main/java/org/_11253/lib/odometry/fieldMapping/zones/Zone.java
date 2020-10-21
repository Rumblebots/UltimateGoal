package org._11253.lib.odometry.fieldMapping.zones;

import org._11253.lib.odometry.fieldMapping.shapes.Shape;

/**
 * A zone is any two-dimensional area in which another two-dimensional entity
 * can be within.
 *
 * <p>
 * Zones are immobile, meaning once they are defined, they can not be
 * re-defined without entirely deleting the zone and creating a new one.
 * Because zones are generally just different areas of the field, they
 * shouldn't need to be redefined anyways. But, if, for whatever reason,
 * you have a very strong desire to have them redefined, you can just go
 * ahead and make another one.
 * </p>
 * <p>
 * In the context of field mapping, zones allow us to do some
 * pretty cool things, such as...
 * <ul>
 *     <li>Dynamically adjust speed multipliers based on location.</li>
 *     <li>Automatically perform certain operations when near targets.</li>
 *     <li>Show the driver, via telemetry, where they are.</li>
 *     <li>Automatically line the robot up to perform certain tasks.</li>
 * </ul>
 * ... and much more. I'm not here to tell you what to do with zones - rather,
 * I'm here to give you some god-awful documentation as to how they function,
 * so you can use them to the best of your ability. You poor soul.
 * </p>
 */
public interface Zone {
    /**
     * Get the name of the specific zone.
     *
     * @return the name of the zone.
     */
    String getName();

    /**
     * Get the "parent shape" of the zone.
     *
     * <p>
     * A "parent shape" is quite literally just a regular shape. Zones are
     * based on shapes, and parent shapes are those shapes. You could, for
     * example, have a rectangular zone name "Zone_A." Zone A's parent shape
     * would be considered the rectangle which Zone A uses to define it's
     * boundaries.
     * </p>
     *
     * @return a parent shape.
     */
    Shape getParentShape();

    /**
     * The priority of the zone - read the rest of this JavaDoc to understand.
     *
     * <p>
     * Each and every zone has a priority. A priority is just how important that
     * zone is compared to other zones. Higher priorities are more important than
     * lower ones - for example, if the robot is in two zones at once (the main
     * field zone, and, say, an objective zone) and the main zone has a priority of 0
     * compared to the objective zone's priority of 1, the objective zone's priority
     * will override that of the main zone. If the objective zone has a speed multiplier
     * which is higher or lower than the main zone's multiplier, that new multiplier
     * will override the current one.
     * </p>
     *
     * @return the zone's priority.
     */
    int getZonePriority();

    boolean doesLineEnterZone();
    boolean isPointInZone();
    double getDriveSpeedMultiplier();
}
