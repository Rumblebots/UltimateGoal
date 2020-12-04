package me.wobblyyyy.pathfinder.pathfinding.paths.instructions;

import me.wobblyyyy.intra.ftc2.utils.math.Math;
import me.wobblyyyy.pathfinder.fieldMapping.Geometry;
import me.wobblyyyy.pathfinder.fieldMapping.TwoDimensionalRobot;
import me.wobblyyyy.pathfinder.fieldMapping.components.Coordinate;
import me.wobblyyyy.pathfinder.fieldMapping.components.HeadingCoordinate;
import me.wobblyyyy.pathfinder.fieldMapping.components.countable.Line;
import me.wobblyyyy.pathfinder.pathfinding.managers.RobotManager;
import me.wobblyyyy.pathfinder.localizer.PfMotorPower;

/**
 * A movement-based instruction.
 *
 * <p>
 * This tells the robot to move. Movement instructions have a 10 second
 * timeout. Each PlannedLinearPath should include a movement instruction.
 * </p>
 * <p>
 * Note that movement instructions work in a linear manner. This isn't because
 * I don't understand the math required to use arcs or circles - it's because
 * I'm really fucking lazy and don't feel like learning the math to use arcs
 * or circles. This might prove itself to be an issue when speed is of the
 * utmost importance, but for now, it will (probably and hopefully) work.
 * </p>
 */
public class MoveInstruction implements Instruction {
    private HeadingCoordinate<Double> start;
    private HeadingCoordinate<Double> end;
    private Coordinate<Double> _start;
    private Coordinate<Double> _end;
    private TwoDimensionalRobot robot;
    private RobotManager robotManager;

    /**
     * Create an instance of a movement-oriented instruction.
     *
     * @param start        the start position, with heading.
     * @param end          the end position, with heading. Any potential turn of the robot is
     *                     performed after the robot has already reached the end position, in terms
     *                     of X and Y.
     * @param robot        a reference to the TwoDimensionalRobot. This is required to keep tabs
     *                     on the position of the robot itself - otherwise, we'd just be left to
     *                     either use complex kinematics I don't want to dive into, or just guess
     *                     where the robot is. Neither of those are very desirable.
     * @param robotManager a reference to the robot's manager, used for controlling the
     *                     drivetrain. Obviously, a movement-oriented instruction won't
     *                     do jack shit if the robot can't move at all because it has no
     *                     access to the movement subsystem of the robot. Oh well.
     */
    public MoveInstruction(HeadingCoordinate<Double> start,
                           HeadingCoordinate<Double> end,
                           TwoDimensionalRobot robot,
                           RobotManager robotManager) {
        this.start = start;
        this.end = end;
        _start = start.getCoordinate();
        _end = end.getCoordinate();
        this.robot = robot;
        this.robotManager = robotManager;
    }

    /**
     * Whether or not the instruction can be cancelled.
     *
     * @return yes, it can be cancelled.
     */
    @Override
    public boolean cancellable() {
        return true;
    }

    @Override
    public boolean isTimed() {
        return true;
    }

    @Override
    public int maxTime() {
        return 10000;
    }

    @Override
    public int minTime() {
        return 0;
    }

    @Override
    public boolean conditionStart() {
        return Geometry.isNearPoint(robot.position, _start, 1.5);
    }

    @Override
    public boolean conditionContinue() {
        return true;
    }

    @Override
    public boolean conditionStop() {
        return Geometry.isNearPoint(robot.position, _end, 1.5) &&
                Geometry.areHeadingsClose(
                        new HeadingCoordinate<Double>(
                                robot.position.getX(),
                                robot.position.getY(),
                                robot.heading
                        ),
                        end,
                        0.5
                );
    }

    /**
     * The execute function. This is where the bulk of the movement code is.
     *
     * <p>
     * First, we check whether or not the robot is (in terms of X and Y, that is) at
     * the target position. If it's not, we need to move there. However, if the robot
     * is already at the position, and we just need to rotate it, we should go right
     * ahead and rotate the robot until it aligns with its target orientation.
     * </p>
     * <p>
     * We use PIDs here (proportional-integral-derivative) to ensure the highest
     * degree of precision. Note that this does remove an element of speed - the robot
     * will take longer to reach far away distances, as the robot will travel more
     * slowly for longer distances of time. However, as the robot gets closer and closer
     * to its target position, the speed at which the robot is moving will slow down to
     * ensure it gets as close as possible to where it's trying to go.
     * </p>
     */
    @Override
    public void execute() {
        // True:
        // The robot isn't near the point. Our first priority is to move
        // the robot, we can turn it later.
        // False:
        // The robot is near the point and now just needs to be turned.
        if (!Geometry.isNearPoint(robot.position, _end, 1.5)) {
            Line line = new Line(_start, _end);
            Line fromRobot = new Line(robot.position, _end);
            double angle = Math.atan2(
                    end.getY() - robot.position.getY(),
                    end.getX() - robot.position.getY()
            );
            // Let's use a PID control so the robot is nice and accurate.
            // The max value should be 1, which is when the robot goes the
            // fastest. The min value should be as close to 0 as possible,
            // which is when the robot should go slowest.
            double magnitude = fromRobot.length / line.length;
            // frbl = Front Right & Back Left
            double frbl = Math.sin(angle - (Math.PI / 4)) * magnitude;
            // flbr = Front Left & Back Right
            double flbr = Math.sin(angle + (Math.PI / 4)) * magnitude;
            double adjustment = Math.max(frbl, flbr);
            frbl /= adjustment;
            flbr /= adjustment;
            robotManager.setPfMotorPower(new PfMotorPower(
                    frbl,
                    flbr,
                    frbl,
                    flbr
            ));
        } else {
            double current = robot.heading;
            double target = end.getHeading();
            double dist = target - current;
            double original = start.getHeading();
            double adjustment = dist / original;
            robotManager.setPfMotorPower(new PfMotorPower(
                    adjustment,
                    -adjustment,
                    adjustment,
                    -adjustment
            ));
        }
    }
}
