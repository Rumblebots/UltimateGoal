package me.wobblyyyy.pathfinder.core;

/**
 * Stores two maps...
 * <ul>
 *     <li>The original map.</li>
 *     <li>The new, prepared map.</li>
 * </ul>
 *
 * @author Colin Robertson
 */
public class DualMapHolder {
    /**
     * The original map, without any modifications.
     */
    public MapHolder original;

    /**
     * The prepared map, now featuring larger shapes,
     * just to ensure the robot doesn't end up hitting anything.
     */
    public MapHolder prepared;
}
