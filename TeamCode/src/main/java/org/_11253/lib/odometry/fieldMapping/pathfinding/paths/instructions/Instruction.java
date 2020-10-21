package org._11253.lib.odometry.fieldMapping.pathfinding.paths.instructions;

/**
 * Instruction interface to keep instructions consistent.
 */
public interface Instruction {
    boolean cancellable();
    boolean isTimed();

    /**
     * Any time beyond this will make the pathfinding timeout.
     * @return max amount of allowable time. (ms)
     */
    int maxTime();
    int time();
    int minTime();
}
