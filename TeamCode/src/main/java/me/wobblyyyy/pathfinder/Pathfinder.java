package me.wobblyyyy.pathfinder;

import me.wobblyyyy.intra.ftc2.utils.Timed;
import me.wobblyyyy.intra.ftc2.utils.async.event.StringEvents;
import me.wobblyyyy.pathfinder.fieldMapping.Map;
import me.wobblyyyy.pathfinder.fieldMapping.components.HeadingCoordinate;
import me.wobblyyyy.pathfinder.localizer.Odometry;
import me.wobblyyyy.pathfinder.localizer.PfMotorPower;

/**
 * @author Colin Robertson
 */
public class Pathfinder {
    private static final String pathfinderName = "Pathfinder";

    private final Map map;
    private final Odometry odometry;
    private final PfRoute pfRoute;
    private final PfRobot pfRobot;
    private final long interval;

    public boolean isActive = false;

    private HeadingCoordinate<Double> position;

    public PfMotorPower pfMotorPower = new PfMotorPower(0, 0, 0, 0);

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

    public HeadingCoordinate<Double> getPosition() {
        return odometry.getPosition();
    }

    public void goToPosition(HeadingCoordinate<Double> end) {
        pfRoute.clear();
        pfRoute.add(
                pfRobot.core.getPath(end.getCoordinate())
        );
    }
}
