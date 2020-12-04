package me.wobblyyyy.pathfinder.pathfinding.routes;

import me.wobblyyyy.pathfinder.fieldMapping.Geometry;
import me.wobblyyyy.pathfinder.fieldMapping.components.HeadingCoordinate;

/**
 * A higher-level route abstraction to simplify the process of creating different
 * types of routes.
 *
 * @author Colin Robertson
 */
public abstract class HighRoute implements Route {
    /**
     * Start position of the route.
     */
    private final HeadingCoordinate<Double> start;

    /**
     * End position of the route.
     */
    private final HeadingCoordinate<Double> target;

    /**
     * Constructor - set up the start and end positions.
     *
     * @param start  start position of the route.
     * @param target end position of the route.
     */
    public HighRoute(HeadingCoordinate<Double> start,
                     HeadingCoordinate<Double> target) {
        this.start = start;
        this.target = target;
    }

    /**
     * Get the start position.
     *
     * @return the start position of the route.
     */
    @Override
    public HeadingCoordinate<Double> getStart() {
        return start;
    }

    /**
     * Get the target position.
     *
     * @return the target position of the route.
     */
    @Override
    public HeadingCoordinate<Double> getTarget() {
        return target;
    }

    /**
     * Get the optimal length of the route.
     *
     * @return the optimal length of the route.
     */
    @Override
    public double getOptimalLength() {
        return Geometry.getDistance(start.getCoordinate(), target.getCoordinate());
    }
}
