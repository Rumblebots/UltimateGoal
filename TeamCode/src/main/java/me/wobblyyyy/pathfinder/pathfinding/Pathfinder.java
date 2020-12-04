package me.wobblyyyy.pathfinder.pathfinding;

import me.wobblyyyy.pathfinder.fieldMapping.Map;
import me.wobblyyyy.pathfinder.fieldMapping.TwoDimensionalRobot;
import me.wobblyyyy.pathfinder.fieldMapping.components.HeadingCoordinate;
import me.wobblyyyy.pathfinder.pathfinding.managers.InstructionManager;
import me.wobblyyyy.pathfinder.pathfinding.managers.MapManager;
import me.wobblyyyy.pathfinder.pathfinding.managers.PathManager;
import me.wobblyyyy.pathfinder.pathfinding.managers.RobotManager;
import me.wobblyyyy.pathfinder.pathfinding.paths.DoubleLinearPath;
import me.wobblyyyy.pathfinder.pathfinding.paths.PlannedLinearPath;
import me.wobblyyyy.pathfinder.pathfinding.paths.instructions.Instruction;
import me.wobblyyyy.pathfinder.pathfinding.paths.instructions.MoveInstruction;
import me.wobblyyyy.pathfinder.pathfinding.robotRegulation.DrivetrainRegulationSystem;
import me.wobblyyyy.pathfinder.pathfinding.robotRegulation.OdometryRegulationSystem;
import me.wobblyyyy.pathfinder.pathfinding.robotRegulation.UserInputRegulationSystem;
import me.wobblyyyy.pathfinder.localizer.PfMotorPower;

import java.util.ArrayList;

/**
 * The highest-level abstraction of the pathfinding system.
 *
 * <p>
 * Pathfinding, as I'm attempting to implement, is a little bit
 * convoluted, but I can try my best to explain what I have going
 * on inside my head.
 * First, a description of all of the files in the "pathfinding"
 * package, and what they're supposed to do.
 * <pre>
 * - gen
 *  - ParameterPathGenerator
 *  - SimplePathGenerator
 * - managers
 *  - InstructionManager
 *  - MapManager
 *  - PathManager: manage all of the paths which the robot is currently "thinking" of doing. In case you didn't yet
 *                 know, paths are simply a single movement-based objective.
 *  - RobotManager: manage all of the physical aspects of the robot. This is a manager which controls all of the
 *                  aspects listed in the robotRegulation folder - drivetrain, odometry, user input, etc.
 * - paths
 *  - apis
 *      - pathfinders
 *          - AbstractPathfinder
 *          - FastPathfinder
 *          - Pathfinder
 *          - PrecisePathfinder
 *          - SimplePathfinder
 *      - SimplePathfinderAPI
 *  - instructions
 *      - ControlInstruction
 *      - HaltInstruction
 *      - Instruction
 *      - InstructionSet
 *      - MoveInstruction
 * -
 *  - PlannedLinearPath
 *  - PlannedNonlinearPath
 *  - PlannedPath
 * - robotRegulation
 *  - DrivetrainRegulationSystem
 *  - OdometryRegulationSystem
 *  - UserInputRegulationSystem
 * - PrimitiveDirections
 * </pre>
 * </p>
 */
public class Pathfinder {
    /**
     * A reference to the virtualized 2d robot.
     */
    public TwoDimensionalRobot twoDimensionalRobot;

    /**
     * A map. I wonder why a map might be needed... hmm?
     */
    public Map map;

    /**
     * System used to regulate the drivetrain.
     */
    public DrivetrainRegulationSystem drivetrain = new DrivetrainRegulationSystem();

    /**
     * System used to regulate odometry.
     */
    public OdometryRegulationSystem odometry = new OdometryRegulationSystem();

    /**
     * System used to regulate user input.
     */
    public UserInputRegulationSystem user = new UserInputRegulationSystem();

    /**
     * An instruction manager, designed to handle and manage all of the instructions
     * the robot might have to deal with at some given point.
     */
    public InstructionManager instructionManager = new InstructionManager();

    /**
     * A map manager.
     */
    public MapManager mapManager;

    /**
     * A path manager - not really useful (or used).
     */
    public PathManager pathManager = new PathManager();

    /**
     * A robot manager, which manages the whole ass robot.
     * Heck yeah. Gamer power.
     */
    public RobotManager robotManager = new RobotManager(
            drivetrain,
            odometry,
            user,
            twoDimensionalRobot,
            this
    );

    /**
     * PfMotorPower instance.
     *
     * <p>
     * This is constantly updated to provide the most accurate estimate of the
     * power each of the motors should have to get to a target position.
     * </p>
     */
    private PfMotorPower pfMotorPower;

    /**
     * Create a new instance of a pathfinder.
     */
    public Pathfinder() {
    }

    /**
     * Find a path to a certain position and make the robot go to it.
     *
     * <p>
     * As this library's whole pathfinding system is rather rudimentary, these
     * paths aren't guaranteed to be the fastest ones possible. There isn't going
     * to be any zooming around the field at super high speeds, using precise curves
     * and complex quadratics to figure out the most ideal possible path. Rather,
     * simple triangles and lines are created and refined to figure out A path,
     * (not always the fastest one, mind you) to a position. The robot should work,
     * it should still go to the position, it should still avoid collisions - it's
     * just not as blazing fast as it could be.
     * </p>
     *
     * <p>
     * This pathfinder should work very simply. The following is a preliminary outline of
     * how the pathfinder should function, in order (kinda).
     * <ul>
     *     <li>
     *         <b>Straight line.</b>
     *         Straight lines will always be the fastest way from point A to B. This is the
     *         preferred method of pathfinding. If a straight-line path is available (meaning
     *         there are no detected collisions), we should use this path instead of any other
     *         path. Straight lines cover the least distance, and are thus the fastest.
     *     </li>
     *     <li>
     *         <b>Right triangle (with X bias).</b>
     *         Create a right triangle, with an X bias. This basically means that the "triangle"
     *         goes to the X position before the Y one. This is the second fastest method that
     *         doesn't involve spending decades writing super complex pathfinding code.
     *     </li>
     *     <li>
     *         <b>Right triangle (with Y bias).</b>
     *         Create a right triangle, with an Y bias. This basically means that the "triangle"
     *         goes to the Y position before the X one. This is the second fastest method that
     *         doesn't involve spending decades writing super complex pathfinding code.
     *     </li>
     *     <li>
     *         <b>Stroke the fuck out.</b>
     *         I'm not an expert in writing pathfinding code. If we can't find a very easy path
     *         after this point, our best solution is to keep re-trying the previous two, with
     *         ever-smaller triangles. We can make use of multi-frames, as we have a huge performance
     *         overhead. This math shouldn't take up even a fraction of the available processing
     *         power which the robot has, so this is most likely a solid option for getting
     *         all this beautiful stuff to work. Heck to the yeah.
     *     </li>
     * </ul>
     * </p>
     *
     * @param target the target position. Turns, etc, can be performed using this. Note
     *               that paths shouldn't be incredibly far away - moving across the field,
     *               for the purpose of testing if the pathfinder works, for example, might
     *               cause a time-out issue. The instruction processing system included in
     *               this library uses timeouts to make sure that code doesn't execute for
     *               too long, so if the movement takes more than 10,000 ms (10 seconds),
     *               it'll be cancelled and the robot will just chill there.
     */
    public void goToPosition(final HeadingCoordinate<Double> target) {
        boolean hasPathBeenFound = false;
        ArrayList<PlannedLinearPath> paths = new ArrayList<>();
        ArrayList<Instruction> instructions = new ArrayList<>();
        ArrayList<HeadingCoordinate<Double>> targets = new ArrayList<HeadingCoordinate<Double>>() {{
            add(target);
        }};
        while (hasPathBeenFound) {
            DoubleLinearPath doublePath = new DoubleLinearPath(
                    new HeadingCoordinate<>(
                            robotManager.twoDimensionalRobot.position.getX(),
                            robotManager.twoDimensionalRobot.position.getY(),
                            robotManager.twoDimensionalRobot.heading
                    ),
                    target
            );
            PlannedLinearPath a = doublePath.a;
            PlannedLinearPath b = doublePath.b;
            paths.add(a);
            paths.add(b);
            hasPathBeenFound = true;
            // Line aLine = a.direct;
            // Line bLine = b.direct;
            // if (map.doesLineCollide(aLine)) {
            //     targets.add(new HeadingCoordinate<>(
            //             aLine.midpoint.getX(),
            //             aLine.midpoint.getY(),
            //             target.getHeading()
            //     ));
            // } else if (map.doesLineCollide(bLine)) {
            //     targets.add(new HeadingCoordinate<>(
            //             bLine.midpoint.getX(),
            //             bLine.midpoint.getY(),
            //             target.getHeading()
            //     ));
            // }
        }
        for (PlannedLinearPath path : paths) {
            instructions.add(new MoveInstruction(
                    path.start,
                    path.end,
                    twoDimensionalRobot,
                    robotManager
            ));
        }
        for (Instruction instruction : instructions) {
            instructionManager.addInstruction(instruction);
        }
    }

    /**
     * Stop the robot in it's tracks.
     *
     * <p>
     * This also clears any queued paths - the robot will entirely stop, all the paths
     * it's "thinking" of will stop, everything else will stop. No pathfinding for you,
     * robot. Not today, at least.
     * </p>
     */
    public void halt() {

    }

    /**
     * "Tick" the pathfinding system to perform necessary updates.
     *
     * <p>
     * This should be called as often as possible without overdoing it - ideally,
     * every loop repetition, to ensure the highest degree of precision.
     * </p>
     * <p>
     * This should do things such as...
     * <ul>
     *     <li>Check potential paths for collisions</li>
     *     <li>Update the robot's motor powers</li>
     *     <li>Get the most up-to-date odometry readings</li>
     *     <li>And much more. Very fun, I know!</li>
     * </ul>
     * </p>
     * <p>
     * Overrides should also be handled here. If the user is pressing a button, specifically
     * a button designed to cancel a pathfinding search, the pathfinding system should be
     * halted, or dealt with according to how it's programmed. Who knows.
     * </p>
     */
    public void tickPathfinder() {
        instructionManager.runInstructions();
    }

    /**
     * You shouldn't ever call this method!
     * Or at least probably not... who knows.
     *
     * @param pfMotorPower new pf motor power.
     */
    public void setPfMotorPower(PfMotorPower pfMotorPower) {
        this.pfMotorPower = pfMotorPower;
    }

    /**
     * Get the "suggested" motor power for the robot.
     *
     * @return the power the robot should adopt.
     */
    public PfMotorPower getPfMotorPower() {
        return pfMotorPower;
    }
}
