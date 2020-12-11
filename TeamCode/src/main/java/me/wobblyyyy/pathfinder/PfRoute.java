package me.wobblyyyy.pathfinder;

import me.wobblyyyy.pathfinder.fieldMapping.components.Coordinate;

import java.util.ArrayList;

/**
 * Manage routes for the pathfinder.
 *
 * @author Colin Robertson
 */
public class PfRoute {
    /**
     * A list of all the coordinate targets the robot has to hit.
     *
     * <p>
     * While the pathfinder system is active, these targets will be
     * followed - or at least, will try to be followed. Of course,
     * it's pretty unlikely that everything here will end up working
     * perfectly - but oh well.
     * </p>
     */
    public ArrayList<Coordinate<Double>> targets = new ArrayList<>();

    /**
     * Add a list of points to the list of points the pathfinder needs
     * to travel to.
     *
     * @param toAdd a list of points to add.
     */
    public void add(ArrayList<Coordinate<Double>> toAdd) {
        targets.addAll(toAdd);
    }

    /**
     * Remove the point at the index 0.
     *
     * <p>
     * This will generally only really be called when the pathfinder
     * ends up reaching a target point.
     * </p>
     */
    public void remove() {
        targets.remove(0);
    }

    /**
     * Clear the entire array list of points to travel to.
     *
     * <p>
     * Note that a point is actually added to this - however, it's
     * at -1.0, -1.0 - meaning it can't ever be reached. The pathfinder
     * interface knows not to try to go here.
     * </p>
     */
    public void clear() {
        targets = new ArrayList<Coordinate<Double>>() {{
            add(new Coordinate<Double>(
                    -1.0,
                    -1.0
            ));
        }};
    }

    /**
     * Get the next target point.
     *
     * @return the next target the robot should try to go to.
     */
    public Coordinate<Double> getNextTarget() {
        return targets.get(0);
    }
}
