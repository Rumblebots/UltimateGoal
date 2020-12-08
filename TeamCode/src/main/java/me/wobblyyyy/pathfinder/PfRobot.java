package me.wobblyyyy.pathfinder;

import me.wobblyyyy.pathfinder.core.Core;
import me.wobblyyyy.pathfinder.fieldMapping.Map;
import me.wobblyyyy.pathfinder.localizer.Odometry;

/**
 * Manager for the robot.
 *
 * @author Colin Robertson
 */
public class PfRobot {
    private Core core;
    private PfRoute route;

    public PfRobot(Map map,
                   Odometry odometry,
                   PfRoute route) {
        core = new Core(map, odometry);
        this.route = route;
    }
}
