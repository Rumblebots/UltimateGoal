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
    public final Core core;
    private final PfRoute route;

    public PfRobot(Map map,
                   Odometry odometry,
                   PfRoute route) {
        core = new Core(map, odometry);
        this.route = route;
    }

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
