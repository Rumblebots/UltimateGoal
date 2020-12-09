package me.wobblyyyy.pathfinder;

import me.wobblyyyy.intra.ftc2.utils.Timed;
import me.wobblyyyy.intra.ftc2.utils.async.event.StringEvents;
import me.wobblyyyy.pathfinder.fieldMapping.Map;
import me.wobblyyyy.pathfinder.fieldMapping.components.HeadingCoordinate;
import me.wobblyyyy.pathfinder.localizer.Odometry;
import me.wobblyyyy.pathfinder.localizer.PfMotorPower;

/**
 * The highest-level implementation of the Pathfinder library.
 *
 * @author Colin Robertson
 */
public class Pathfinder {
    /**
     * The locally-used name for the pathfinder events.
     */
    private static final String pathfinderName = "Pathfinder";

    /**
     * The field map.
     */
    private final Map map;

    /**
     * The odometry system in use.
     */
    private final Odometry odometry;

    /**
     * Route manager.
     */
    private final PfRoute pfRoute;

    /**
     * Robot manager.
     */
    private final PfRobot pfRobot;

    /**
     * How long in between automatic updates.
     */
    private final long interval;

    /**
     * Is the pathfinder active?
     */
    public boolean isActive = false;

    /**
     * The current position of the robot.
     */
    private HeadingCoordinate<Double> position;

    /**
     * The suggested power for the robot's motors.
     */
    public PfMotorPower pfMotorPower = new PfMotorPower(0, 0, 0, 0);

    /**
     * Create a new Pathfinder instance.
     *
     * @param map      the field map.
     * @param odometry the odometry system to use.
     * @param interval how long between updates.
     */
    public Pathfinder(Map map,
                      Odometry odometry,
                      long interval) {
        this.map = map;
        this.odometry = odometry;
        pfRoute = new PfRoute();
        pfRobot = new PfRobot(
                map,
                odometry,
                pfRoute
        );
        this.interval = interval;
    }

    private void schedulePathfinderUpdating() {
        StringEvents.schedule(
                pathfinderName,
                interval,
                0,
                new Timed() {
                    @Override
                    public Runnable open() {
                        return new Runnable() {
                            @Override
                            public void run() {
                                if (isActive) {
                                    pfRobot.updatePower();
                                }
                            }
                        };
                    }
                },
                true
        );
    }

    /**
     * Get the position of the robot.
     *
     * @return the robot's position.
     */
    public HeadingCoordinate<Double> getPosition() {
        return odometry.getPosition();
    }

    /**
     * Generate a route and start going to a position.
     *
     * @param end the target position to go to.
     */
    public void goToPosition(HeadingCoordinate<Double> end) {
        pfRoute.clear();
        pfRoute.add(
                pfRobot.core.getPath(end.getCoordinate())
        );
    }
}
