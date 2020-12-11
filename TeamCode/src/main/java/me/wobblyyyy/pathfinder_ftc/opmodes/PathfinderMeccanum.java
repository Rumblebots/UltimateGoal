package me.wobblyyyy.pathfinder_ftc.opmodes;

import me.wobblyyyy.pathfinder.fieldMapping.Map;
import me.wobblyyyy.pathfinder_ftc.threeWheel.ThreeWheelComplete;
import me.wobblyyyy.pathfinder_ftc.threeWheel.ThreeWheelConfig;
import org._11253.lib.drives.Meccanum;

/**
 * A pathfinder-enabled meccanum drive.
 *
 * <p>
 * You'd probably serve yourself well if you were to read the documentation,
 * specifically the documentation that's linked here. Of course, you don't
 * to read the docs, but in that case, I'm not really willing to help you.
 * Sucks for you, I guess. Your loss. I don't know... I'm in a "mean" mood
 * right know - you know the feeling?
 * <ul>
 *     <li>{@link me.wobblyyyy.pathfinder.Pathfinder}</li>
 *     <li>{@link me.wobblyyyy.pathfinder_ftc.pathfinder.PfInterface}</li>
 *     <li>{@link me.wobblyyyy.pathfinder_ftc.threeWheel.ThreeWheel}</li>
 *     <li>{@link me.wobblyyyy.pathfinder_ftc.threeWheel.ThreeWheelComplete}</li>
 * </ul>
 * </p>
 *
 * <p>
 * Please, please, please please please - read the docs or watch a guide if you
 * want to get this working optimally. This is an ABSTRACT class, meaning you're
 * going to need to set some values up here. If you feel confident in your keyboard
 * smashing abilities, you can go ahead and proceed with your coding - however,
 * not reading the docs all but ensures that you'll mess something up - somewhere.
 * </p>
 *
 * @author Colin Robertson
 */
public abstract class PathfinderMeccanum extends Meccanum {
    /**
     * The combination odometry and pathfinder system that's in use here.
     *
     * <p>
     * I don't really feel like copy-pasting all the documentation from that
     * class, so you're probably better off just going ahead and reading the
     * docs. Because I'm an incredibly nice person, I'm kind enough to link
     * the documentation right here. {@link ThreeWheelComplete}
     * </p>
     */
    public ThreeWheelComplete twc;

    /**
     * The configuration which the robot uses.
     *
     * <p>
     * I don't really feel like copy-pasting all the documentation from that
     * class, so you're probably better off just going ahead and reading the
     * docs. Because I'm an incredibly nice person, I'm kind enough to link
     * the documentation right here. {@link ThreeWheelConfig}
     * </p>
     */
    public ThreeWheelConfig config;

    /**
     * The name of the left encoder.
     *
     * <p>
     * Encoders share ports with motors, meaning there's a very good
     * probability that this will share a name with another motor.
     * Plugging an encoder into a port (without a motor) hasn't exactly
     * worked out very well (based on my previous experiences), but you
     * can feel free to try whatever you'd like.
     * </p>
     */
    public String leftEncoderName;

    /**
     * The name of the right encoder.
     *
     * <p>
     * Encoders share ports with motors, meaning there's a very good
     * probability that this will share a name with another motor.
     * Plugging an encoder into a port (without a motor) hasn't exactly
     * worked out very well (based on my previous experiences), but you
     * can feel free to try whatever you'd like.
     * </p>
     */
    public String rightEncoderName;

    /**
     * The name of the front (or back) encoder.
     *
     * <p>
     * Encoders share ports with motors, meaning there's a very good
     * probability that this will share a name with another motor.
     * Plugging an encoder into a port (without a motor) hasn't exactly
     * worked out very well (based on my previous experiences), but you
     * can feel free to try whatever you'd like.
     * </p>
     */
    public String frontBackEncoderName;

    /**
     * The map which the robot, odometry, and pathfinding system all
     * end up making use of.
     *
     * <p>
     * A list of many zones composing a single map.
     * </p>
     *
     * <p>
     * This should (obviously) be extended by other classes. I'd suggest
     * that you add zones to the fieldZone list wrapper by using a constructor,
     * but honestly, I could give less of a shit.
     * </p>
     *
     * <p>
     * I don't even know why you're looking at this, actually. You can (and
     * should) just use an already existing map, as this should be update-to-date.
     * </p>
     * </p>
     */
    public Map map;

    public PathfinderMeccanum() {
        twc = new ThreeWheelComplete(
                leftEncoderName,
                rightEncoderName,
                frontBackEncoderName,
                config,
                drivetrain,
                map
        );
    }
}
