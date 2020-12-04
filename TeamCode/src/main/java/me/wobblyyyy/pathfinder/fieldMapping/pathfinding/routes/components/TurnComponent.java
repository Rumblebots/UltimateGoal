package me.wobblyyyy.pathfinder.fieldMapping.pathfinding.routes.components;

import me.wobblyyyy.pathfinder.fieldMapping.Geometry;
import me.wobblyyyy.pathfinder.fieldMapping.components.HeadingCoordinate;
import me.wobblyyyy.pathfinder.localizer.Odometry;
import me.wobblyyyy.pathfinder.localizer.PfMotorPower;

/**
 * Component used for turning the robot.
 *
 * @author Colin Robertson
 */
public class TurnComponent implements ComponentCore {
    private final Odometry odometry;
    private final double initialHeading;
    private final double targetHeading;

    /**
     * Init a new turn component.
     *
     * @param odometry      the low-level odometry system in use.
     * @param targetHeading the target heading.
     */
    public TurnComponent(Odometry odometry,
                         double targetHeading) {
        this.odometry = odometry;
        this.initialHeading = odometry.getPosition().getHeading();
        this.targetHeading = targetHeading;
    }

    /**
     * Execute the turn.
     *
     * <p>
     * This is ideally run several times per second to make sure the most precise
     * values are always at play here.
     * </p>
     */
    @Override
    public PfMotorPower execute() {
        double current = odometry.getPosition().getHeading();
        double dist = targetHeading - current;
        double adjustment = dist / initialHeading;
        return new PfMotorPower(
                adjustment,
                -adjustment,
                adjustment,
                -adjustment
        );
    }

    /**
     * The condition that needs to be met for this component to start executing.
     *
     * @return true - this can start at any time.
     */
    @Override
    public boolean startCondition() {
        return true;
    }

    /**
     * The condition that needs to be met for this component to stop executing.
     *
     * @return whether or not the condition has been met yet.
     */
    // TODO tolerance might need to be adjusted here - run some tests and check it out.
    @Override
    public boolean finishCondition() {
        return Geometry.areHeadingsClose(
                odometry.getPosition(),
                new HeadingCoordinate<>(
                        odometry.getPosition().getX(),
                        odometry.getPosition().getY(),
                        targetHeading
                ),
                0.25
        );
    }
}
