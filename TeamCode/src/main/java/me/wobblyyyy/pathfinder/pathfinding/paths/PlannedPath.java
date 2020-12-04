package me.wobblyyyy.pathfinder.pathfinding.paths;

import me.wobblyyyy.pathfinder.fieldMapping.components.HeadingCoordinate;

/**
 * The most primitive of the planned paths.
 *
 * <p>
 * A "path" is just a combination of several instructions.
 * Each path should accomplish a single movement-oriented goal.
 * Paths can still operate and activate other parts of the robot,
 * but the primary goal of a path, and what differentiates one
 * path from another, is the completion of a single
 * movement-based objective.
 * </p>
 */
public interface PlannedPath {
    HeadingCoordinate<Double> start();
    HeadingCoordinate<Double> end();
}
