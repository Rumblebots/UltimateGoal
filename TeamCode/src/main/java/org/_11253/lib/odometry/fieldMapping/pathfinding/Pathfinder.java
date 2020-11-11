package org._11253.lib.odometry.fieldMapping.pathfinding;

import org._11253.lib.controllers.Controller;
import org._11253.lib.odometry.Odometry;
import org._11253.lib.odometry.fieldMapping.Map;
import org._11253.lib.odometry.fieldMapping.TwoDimensionalRobot;
import org._11253.lib.odometry.fieldMapping.components.HeadingCoordinate;
import org._11253.lib.odometry.fieldMapping.pathfinding.managers.InstructionManager;
import org._11253.lib.odometry.fieldMapping.pathfinding.managers.MapManager;
import org._11253.lib.odometry.fieldMapping.pathfinding.managers.PathManager;
import org._11253.lib.odometry.fieldMapping.pathfinding.managers.RobotManager;
import org._11253.lib.odometry.fieldMapping.pathfinding.paths.DoubleLinearPath;
import org._11253.lib.odometry.fieldMapping.pathfinding.paths.PlannedLinearPath;
import org._11253.lib.odometry.fieldMapping.pathfinding.paths.instructions.Instruction;
import org._11253.lib.odometry.fieldMapping.pathfinding.paths.instructions.MoveInstruction;
import org._11253.lib.odometry.fieldMapping.pathfinding.robotRegulation.DrivetrainRegulationSystem;
import org._11253.lib.odometry.fieldMapping.pathfinding.robotRegulation.OdometryRegulationSystem;
import org._11253.lib.odometry.fieldMapping.pathfinding.robotRegulation.UserInputRegulationSystem;
import org._11253.lib.robot.phys.assm.drivetrain.Drivetrain;

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
    public TwoDimensionalRobot twoDimensionalRobot;
    public Map map;

    public DrivetrainRegulationSystem drivetrain = new DrivetrainRegulationSystem();
    public OdometryRegulationSystem odometry = new OdometryRegulationSystem();
    public UserInputRegulationSystem user = new UserInputRegulationSystem();

    public InstructionManager instructionManager = new InstructionManager();
    public MapManager mapManager;
    public PathManager pathManager = new PathManager();
    public RobotManager robotManager = new RobotManager(
            drivetrain,
            odometry,
            user,
            twoDimensionalRobot
    );

    /**
     * Create a new instance of a pathfinder.
     *
     * @param drivetrain  the robot's drivetrain. As of now, pathfinding only works with
     *                    four-wheel-drive meccanum - other systems will render you
     *                    entirely out of luck. You can feel free to create your own
     *                    pathfinding system, but I'm sure as hell not planning on it.
     * @param odometry    the robot's odometry system. Odometry systems come in two main
     *                    flavors - two wheel with a gyroscope for positional heading, and
     *                    three wheel without a gyroscope. You can, of course, use a three
     *                    wheel with a gyroscope for added precision, but it's questionable
     *                    whether or not that'll work.
     * @param controller1 the first controller.
     * @param controller2 the second controller.
     */
    public Pathfinder(Drivetrain drivetrain,
                      Odometry odometry,
                      Map map,
                      Controller controller1,
                      Controller controller2) {
        this.map = map;
        mapManager = new MapManager(map);
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
}
