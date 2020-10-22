package org._11253.lib.odometry.fieldMapping.pathfinding;

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
}
