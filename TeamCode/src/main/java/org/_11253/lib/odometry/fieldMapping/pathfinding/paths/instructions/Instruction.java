package org._11253.lib.odometry.fieldMapping.pathfinding.paths.instructions;

/**
 * Instruction interface to keep instructions consistent.
 */
public interface Instruction {
    boolean cancellable();

    boolean isTimed();

    /**
     * Any time beyond this will make the pathfinding timeout.
     *
     * @return max amount of allowable time. (ms)
     */
    int maxTime();

    /**
     * The minimum amount of time the instruction will execute for.
     *
     * @return the minimum amount of time the instruction will execute for. (ms)
     */
    int minTime();

    /**
     * What condition (boolean) must be met to start.
     *
     * @return true/false for that condition.
     */
    boolean conditionStart();

    /**
     * What condition (boolean) must be met to continue.
     *
     * @return true/false for that condition.
     */
    boolean conditionContinue();

    /**
     * What condition (boolean) must be met to stop.
     *
     * <p>
     * Note that when this function returns true, this instruction will
     * suddenly halt. Some examples of things that you could put in here
     * include a color sensor picking up a certain color or the odometry system
     * registering that the robot is in a certain position.
     * </p>
     *
     * @return true/false for that condition.
     */
    boolean conditionStop();

    void execute();
}
