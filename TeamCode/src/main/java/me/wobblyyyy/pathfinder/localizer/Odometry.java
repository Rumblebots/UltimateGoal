package me.wobblyyyy.pathfinder.localizer;

import me.wobblyyyy.pathfinder.fieldMapping.components.HeadingCoordinate;

public interface Odometry {
    HeadingCoordinate<Double> getPosition();
}
