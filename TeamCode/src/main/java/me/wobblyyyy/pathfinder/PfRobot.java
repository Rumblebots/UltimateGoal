package me.wobblyyyy.pathfinder;

import me.wobblyyyy.pathfinder.core.Core;
import me.wobblyyyy.pathfinder.fieldMapping.Map;
import me.wobblyyyy.pathfinder.fieldMapping.components.HeadingCoordinate;
import me.wobblyyyy.pathfinder.localizer.Odometry;

/**
 * Manager for the robot.
 *
 * @author Colin Robertson
 */
public class PfRobot {
    /**
     * The pathfinding core of the robot.
     *
     * <p>
     * Interested in how this works? Check out the documentation in the core file.
     * As usual, I'm kind enough to provide a link as to where you should go!
     * {@link Core}
     * </p>
     */
    public final Core core;

    /**
     * The route manager of the robot.
     */
    private final PfRoute route;

    /**
     * Create a new instance of a PfRobot.
     *
     * @param map      the map which the robot should use.
     * @param odometry the odometry system the robot should use. Note that the odometry interface
     *                 does nothing except for provide a getPosition method.
     * @param route    the pf route manager which the robot should manage.
     */
    public PfRobot(Map map,
                   Odometry odometry,
                   PfRoute route) {
        core = new Core(map, odometry);
        this.route = route;
    }

    /**
     * Update the power of the pathfinding core.
     */
    public void updatePower() {
        core.updatePower(
                core.getPosition(),
                new HeadingCoordinate<Double>(
                        route.getNextTarget().getX(),
                        route.getNextTarget().getY(),
                        core.getPosition().getHeading()
                )
        );
    }
}
