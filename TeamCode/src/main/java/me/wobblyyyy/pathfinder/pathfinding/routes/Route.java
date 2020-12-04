package me.wobblyyyy.pathfinder.pathfinding.routes;

import me.wobblyyyy.pathfinder.fieldMapping.components.HeadingCoordinate;
import me.wobblyyyy.pathfinder.fieldMapping.components.countable.Line;
import me.wobblyyyy.pathfinder.pathfinding.routes.components.ComponentCore;

import java.util.ArrayList;

/**
 * An interface for different types of routes.
 */
public interface Route {
    ArrayList<Line> getLines();
    ArrayList<ComponentCore> getComponents();
    ArrayList<HeadingCoordinate<Double>> getTests();
    HeadingCoordinate<Double> getStart();
    HeadingCoordinate<Double> getTarget();
    double getOptimalLength();
    double getTotalLength();
}
