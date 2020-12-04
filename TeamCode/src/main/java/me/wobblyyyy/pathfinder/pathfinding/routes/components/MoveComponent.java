package me.wobblyyyy.pathfinder.pathfinding.routes.components;

import me.wobblyyyy.intra.ftc2.utils.math.Math;
import me.wobblyyyy.pathfinder.fieldMapping.Geometry;
import me.wobblyyyy.pathfinder.fieldMapping.components.Coordinate;
import me.wobblyyyy.pathfinder.fieldMapping.components.HeadingCoordinate;
import me.wobblyyyy.pathfinder.fieldMapping.components.countable.Line;
import me.wobblyyyy.pathfinder.localizer.Odometry;
import me.wobblyyyy.pathfinder.localizer.PfMotorPower;

/**
 * Component used for moving the robot.
 *
 * @author Colin Robertson
 */
public class MoveComponent implements ComponentCore {
    private final Odometry odometry;
    private final Coordinate<Double> start;
    private final Coordinate<Double> end;

    /**
     * Constructor for movement.
     *
     * @param odometry low-level odometry system in use.
     * @param start the start position.
     * @param end the end position.
     */
    public MoveComponent(Odometry odometry,
                         HeadingCoordinate<Double> start,
                         HeadingCoordinate<Double> end) {
        this.odometry = odometry;
        this.start = start.getCoordinate();
        this.end = end.getCoordinate();
    }

    @Override
    public PfMotorPower execute() {
        Line line = new Line(start, end);
        Line fromRobot = new Line(odometry.getPosition().getCoordinate(), end);
        double angle = Math.atan2(
                end.getY() - odometry.getPosition().getY(),
                end.getX() - odometry.getPosition().getX()
        );
        // Let's use a PID control so the robot is nice and accurate.
        // The max value should be 1, which is when the robot goes the
        // fastest. The min value should be as close to 0 as possible,
        // which is when the robot should go slowest.
        double magnitude = fromRobot.length / line.length;
        // fr bl = Front Right & Back Left
        double frbl = Math.sin(angle - (Math.PI / 4)) * magnitude;
        // fl br = Front Left & Back Right
        double flbr = Math.sin(angle + (Math.PI / 4)) * magnitude;
        double adjustment = Math.max(frbl, flbr);
        frbl /= adjustment;
        flbr /= adjustment;
        return new PfMotorPower(
                frbl,
                flbr,
                frbl,
                flbr
        );
    }

    @Override
    public boolean startCondition() {
        return true;
    }

    // TODO possibly adjust tolerance - once again, not really all that sure here
    @Override
    public boolean finishCondition() {
        return Geometry.isNearPoint(
                odometry.getPosition().getCoordinate(),
                end,
                1.5
        );
    }
}
